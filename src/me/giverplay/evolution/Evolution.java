package me.giverplay.evolution;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.giverplay.evolution.api.Formater;
import me.giverplay.evolution.api.Home;
import me.giverplay.evolution.api.PBar;
import me.giverplay.evolution.api.PlayerWarp;
import me.giverplay.evolution.api.Rank;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.manager.CommandManager;
import me.giverplay.evolution.api.manager.ConfigManager;
import me.giverplay.evolution.api.manager.PlayerManager;
import me.giverplay.evolution.api.manager.ScoreboardManager;
import me.giverplay.evolution.comandos.ComandoCreatePlayerWarp;
import me.giverplay.evolution.comandos.ComandoDelHome;
import me.giverplay.evolution.comandos.ComandoDelHomeOf;
import me.giverplay.evolution.comandos.ComandoDeop;
import me.giverplay.evolution.comandos.ComandoEvolution;
import me.giverplay.evolution.comandos.ComandoHome;
import me.giverplay.evolution.comandos.ComandoHomeOf;
import me.giverplay.evolution.comandos.ComandoHomes;
import me.giverplay.evolution.comandos.ComandoItemRaro;
import me.giverplay.evolution.comandos.ComandoLixeira;
import me.giverplay.evolution.comandos.ComandoMyWarp;
import me.giverplay.evolution.comandos.ComandoNivel;
import me.giverplay.evolution.comandos.ComandoOp;
import me.giverplay.evolution.comandos.ComandoPlayerWarp;
import me.giverplay.evolution.comandos.ComandoPlugins;
import me.giverplay.evolution.comandos.ComandoRankup;
import me.giverplay.evolution.comandos.ComandoReiniciar;
import me.giverplay.evolution.comandos.ComandoRemovePlayerWarp;
import me.giverplay.evolution.comandos.ComandoReparar;
import me.giverplay.evolution.comandos.ComandoReply;
import me.giverplay.evolution.comandos.ComandoSetHome;
import me.giverplay.evolution.comandos.ComandoTPA;
import me.giverplay.evolution.comandos.ComandoTPAceitar;
import me.giverplay.evolution.comandos.ComandoTPNegar;
import me.giverplay.evolution.comandos.ComandoTell;
import me.giverplay.evolution.comandos.ComandoToggle;
import me.giverplay.evolution.comandos.ComandoTokenReparo;
import me.giverplay.evolution.handlers.ListenerAvulso;
import me.giverplay.evolution.handlers.ListenerChat;
import me.giverplay.evolution.handlers.ListenerCommandManager;
import me.giverplay.evolution.handlers.ListenerEntityDeathBroadcast;
import me.giverplay.evolution.handlers.ListenerMenuReparar;
import me.giverplay.evolution.handlers.ListenerNivel;
import me.giverplay.evolution.handlers.ListenerPlayerManager;
import me.giverplay.evolution.handlers.ListenerPlayerWarpMenu;
import me.giverplay.evolution.handlers.ListenerPluginsMenu;
import me.giverplay.evolution.handlers.ListenerReiniciar;
import me.giverplay.evolution.handlers.ListenerSigns;
import me.giverplay.evolution.handlers.ListenerToggle;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_16_R1.MinecraftServer;

public class Evolution extends JavaPlugin 
{
	private static Evolution instance;
	
	private HashMap<String, Rank> rankups = new HashMap<>();
	private HashMap<String, PlayerManager> playersHashMap = new HashMap<>();
	private HashMap<String, Comando> comandos = new HashMap<>();
	
	private ArrayList<String> deitar = new ArrayList<>();
	
	private ConfigManager playersyaml;
	private ConfigManager niveis;
	private ConfigManager configs;
	private ConfigManager homes;
	private ConfigManager warps;
	private ConfigManager ranks;
	
	private Economy economy;
	
	private boolean reiniciando;
	private boolean bloqueartudo;
	
	public static Evolution getInstance()
	{
		return instance;
	}
	
	// TODO Ferramentas
	
	public String getTime(int segundos)
	{
		StringBuilder tempo = new StringBuilder();
		
		if(segundos >= 60)
		{
			int m = segundos / 60;
			int s = segundos % 60;
			tempo.append(m + " minuto");
			
			if(m > 1)
			{
				tempo.append("s");
			}
			
			if(s > 0)
			{
				tempo.append(" e " + s + " segundos");
			}		
		}
		else
		{
			tempo.append(segundos + " segundos");
		}
		
		return tempo.toString();
	}
	
	public String createTimerLabel(int segundos)
	{
		String timeLabel = "";
		int min, sec;
		
		min = segundos / 60;
		sec = segundos % 60;
		
		timeLabel += min + ":";
		timeLabel += (sec < 10 ? "0" + sec : sec);
		return timeLabel;
	}
	
	public void sendAction(Player player, String message)
	{
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	}
	
	public double calcularSalario(PlayerManager player)
	{
		long tempoOnline = (System.currentTimeMillis() - player.getLoginTime()) / 1000;
		
		double salario = (tempoOnline * 80) / 1440;
		
		return salario;
	}
	
	public void setHeaderAndFooter(Player player) 
	{
		player.setPlayerListHeaderFooter("§a§lEvolution§f§lCity\n\n§7----------",
				"§7----------\n\n§eBom jogo!");
	}
	
	public String getProgress(PlayerManager player)
	{
		double custo = rankups.get(player.getRank().getNextRank()).getCost();
		double money = economy.getBalance(player.getPlayer());
		
		if(money >= custo)
		{
			return "§8[§a" + StringUtils.repeat("|", 15) + "§8]";
		}
		
		return PBar.getProgressBarScore(money, custo, 15, "|", "§a", "§7", "§8[", "§8]");
	}
	
	// TODO Funções relacionadas ao plugin/servidor
	
	public Economy getEconomy()
	{
		return this.economy;
	}
	
	public void addPlayer(String name)
	{
		playersHashMap.put(name, new PlayerManager(name));
	}
	
	public void removePlayer(String name)
	{
		playersHashMap.remove(name);
	}
	
	public PlayerManager getPlayer(String name)
	{
		return playersHashMap.get(name);
	}
	
	public void registerEvents(Listener listener)
	{
		Bukkit.getPluginManager().registerEvents(listener, getInstance());
	}
	
	public void addComando(String nome, Comando comando)
	{
		comandos.put(nome, comando);
	}
	
	public boolean isRestarting()
	{
		return this.reiniciando;
	}
	
	public void setRestarting(boolean toSet)
	{
		this.reiniciando = toSet;
	}
	
	public boolean blockAllRestart()
	{
		return this.bloqueartudo;
	}
	
	public void setBlockAllRestart(boolean toSet)
	{
		this.bloqueartudo = toSet;
	}
	
	@SuppressWarnings({ "deprecation", "resource" })
	public String getTPS()
	{
		double tps = MinecraftServer.getServer().recentTps[0]; 
		return String.valueOf(((tps > 18.0D) ? ChatColor.GREEN : (
				(tps > 16.0D) ? ChatColor.YELLOW : ChatColor.RED)).toString()) + (
						(tps > 20.0D) ? "*" : "") + Math.min(Math.round(tps * 100.0D) / 100.0D, 20.0D);
	}
	
	public String getMoney(Player p)
	{
		return Formater.format((long) economy.getBalance(p));
	}
	
	// TODO Getters - Coleções
	
	public HashMap<String, Comando> getRegisteredCommands()
	{
		return this.comandos;
	}
	
	public HashMap<String, Rank> getRanks()
	{
		return this.rankups;
	}
	
	public ArrayList<String> getBedCooldownList()
	{
		return this.deitar;
	}
	
	// TODO Warps
	
	public ArrayList<PlayerWarp> getWarps(String nickname)
	{
		YamlConfiguration conf = warps.getConfig();
		
		if(!conf.isSet(nickname))
		{
			return null;
		}
		
		ArrayList<PlayerWarp> list = new ArrayList<>();
		
		for(String key : conf.getConfigurationSection(nickname).getKeys(false))
		{
			try
			{
				if(!conf.isSet(nickname + "." + key + ".loc")){
					continue;
				}
				
				list.add(new PlayerWarp(conf.getLocation(nickname + "." + key + ".loc"), key, nickname));
			}
			catch(NullPointerException e)
			{
				Bukkit.getConsoleSender().sendMessage("§c[Evolution] Erro em uma warp............");
			}
		}
		
		if(list.isEmpty()){
			return null;
		}
		
		return list;
	}
	
	public PlayerWarp getPlayerWarp(String nickname, String warp)
	{
		try
		{
			YamlConfiguration conf = warps.getConfig();
			
			if(!conf.isSet(nickname) || !conf.isSet(nickname + "." + warp))
			{
				return null;
			}
			
			return new PlayerWarp(conf.getLocation(nickname + "." + warp + ".loc"), warp, nickname);
		}
		catch(NullPointerException e)
		{
			return null;
		}
	}
	
	// TODO Homes - Baseado no plugin SetHomes
	
	public HashMap<String, Home> getPlayersNamedHomes(String uuid)
	{
		HashMap<String, Home> playersNamedHomes = new HashMap<String, Home>();
		String homesPath = "allNamedHomes." + uuid;
		YamlConfiguration homesCfg = homes.getConfig();
		
		for (String id : homesCfg.getConfigurationSection(homesPath).getKeys(false))
		{
			String path = homesPath + "." + id + ".";
			World world = Bukkit.getServer().getWorld(homesCfg.getString(path + ".world"));
			
			double x = homesCfg.getDouble(path + ".x");
			double y = homesCfg.getDouble(path + ".y");
			double z = homesCfg.getDouble(path + ".z");
			
			float pitch = Float.parseFloat(homesCfg.getString(path + ".pitch"));
			float yaw = Float.parseFloat(homesCfg.getString(path + ".yaw"));
			
			Location home = new Location(world, x, y, z, yaw, pitch);
			Home h = new Home(home);
			
			playersNamedHomes.put(id, h);
		}
		
		return playersNamedHomes;
	}
	
	public void saveNamedHome(String uuid, Home home)
	{
		String path = "allNamedHomes." + uuid + "." + home.getHomeName();
		ConfigManager homesCfg = homes;
		
		homesCfg.set(path + ".world", home.getWorld());
		homesCfg.set(path + ".x", home.getX());
		homesCfg.set(path + ".y", home.getY());
		homesCfg.set(path + ".z", home.getZ());
		homesCfg.set(path + ".pitch", home.getPitch());
		homesCfg.set(path + ".yaw", home.getYaw());
		homesCfg.saveConfig();
	}
	
	public boolean hasNamedHomes(String uuid)
	{
		return homes.contains("allNamedHomes." + uuid) && homes.getConfig().isSet("allNamedHomes." + uuid);
	}
	
	public void saveUnknownHome(String uuid, Home home)
	{
		String path = "unknownHomes." + uuid;
		ConfigManager homesCfg = homes;
		
		homesCfg.set(path + ".world", home.getWorld());
		homesCfg.set(path + ".x", home.getX());
		homesCfg.set(path + ".y", home.getY());
		homesCfg.set(path + ".z", home.getZ());
		homesCfg.set(path + ".pitch", home.getPitch());
		homesCfg.set(path + ".yaw", home.getYaw());
		homesCfg.saveConfig();
	}
	
	public boolean hasUnknownHomes(String uuid)
	{
		return homes.getConfig().contains("unknownHomes." + uuid);
	}
	
	public void deleteUnknownHome(String uuid)
	{
		String path = "unknownHomes." + uuid;
		
		homes.getConfig().set(path, null);
		homes.saveConfig();
	}
	
	public Location getNamedHomeLocal(String uuid, String homeName)
	{
		Home h = getPlayersNamedHomes(uuid).get(homeName);
		World world = Bukkit.getWorld(h.getWorld());
		Location home = new Location(world, h.getX(), h.getY(), h.getZ(), h.getYaw(), h.getPitch());
		
		return home;
	}
	
	public Location getPlayersUnnamedHome(String uuid)
	{
		String path = "unknownHomes." + uuid;
		ConfigManager homesCfg = homes;
		
		World world = Bukkit.getWorld(homesCfg.getString(path + ".world"));
		double x = homesCfg.getDouble(path + ".x");
		double y = homesCfg.getDouble(path + ".y");
		double z = homesCfg.getDouble(path + ".z");
		float pitch = Float.parseFloat(homesCfg.getString(path + ".pitch"));
		float yaw = Float.parseFloat(homesCfg.getString(path + ".yaw"));
		
		return new Location(world, x, y, z, yaw, pitch);
	}
	
	public void deleteNamedHome(String uuid, String homeName)
	{
		String path = "allNamedHomes." + uuid + "." + homeName;
		
		homes.set(path, null);
		homes.saveConfig();
	}
	
	public void teleportHome(Player player, String uuid, String[] args)
	{
		if (args.length < 1)
		{
			if (!(hasUnknownHomes(uuid)))
			{
				player.sendMessage("§cNenhuma casa padrão definida");
				return;
			}
			
			player.teleport(getPlayersUnnamedHome(uuid));
			player.spawnParticle(Particle.PORTAL, player.getLocation(), 100);
			player.playNote(player.getLocation(), Instrument.BELL, Note.sharp(2, Note.Tone.F));
			player.sendMessage("§aTeleportado com sucesso");
			
		} 
		else
		{
			if ( !(hasNamedHomes(uuid)) || !(getPlayersNamedHomes(uuid).containsKey(args[0])) )
			{
				player.sendMessage("§cNenhuma casa com esse nome: §f" + args[0]);
				return;
			}
			
			player.teleport(getNamedHomeLocal(uuid, args[0]));
			player.spawnParticle(Particle.PORTAL, player.getLocation(), 100);
			player.playNote(player.getLocation(), Instrument.BELL, Note.sharp(2, Note.Tone.F));
			player.sendMessage("§aTeleportado com sucesso");
		}
	}
	
	public void teleportHomeOf(Player player, String uuid, String[] args)
	{
		if(args.length == 1)
		{
			if(!hasUnknownHomes(uuid))
			{
				player.sendMessage("§cEste jogador não possui uma casa padrão");
				return;
			}
			
			player.teleport(getPlayersUnnamedHome(uuid));
			player.spawnParticle(Particle.PORTAL, player.getLocation(), 100);
			player.playNote(player.getLocation(), Instrument.BELL, Note.sharp(2, Note.Tone.F));
			player.sendMessage("§aTeleportado com sucesso");
			
			return;
		}
		
		String homeName = args[1];
		
		if (!(hasNamedHomes(uuid)) || !(getPlayersNamedHomes(uuid).containsKey(homeName)))
		{
			player.sendMessage("§cEste jogador não possui a casa §f" + homeName);
			return;
		}
		
		player.teleport(getNamedHomeLocal(uuid, homeName));
		player.spawnParticle(Particle.PORTAL, player.getLocation(), 100);
		player.playNote(player.getLocation(), Instrument.BELL, Note.sharp(2, Note.Tone.F));
		player.sendMessage("§cTeleportado com sucesso");
	}
	
	public void listHomes(Player player)
	{
		String uuid = player.getUniqueId().toString();
		String filler = StringUtils.repeat("§f-", 40);
		
		player.sendMessage(" ");
		player.sendMessage("§b§lSuas Casas");
		player.sendMessage(filler);
		player.sendMessage(" ");
		
		if(hasUnknownHomes(uuid))
		{
			String world = getPlayersUnnamedHome(uuid).getWorld().getName();
			player.sendMessage("§bCasa Padrão: §fNo mundo " + world);
		}
		
		player.sendMessage(" ");
		
		if(hasNamedHomes(uuid))
		{
			for(String id : getPlayersNamedHomes(uuid).keySet())
			{
				String world = getPlayersNamedHomes(uuid).get(id).getWorld();
				
				player.sendMessage("§bCasa: §f" + id);
				player.sendMessage("§bMundo: §f" + world);
				
				player.sendMessage(" ");
			}
		}
		player.sendMessage(filler);
	}
	
	public void listHomes(Player alvo, Player player)
	{
		String uuid = alvo.getUniqueId().toString();
		String filler = StringUtils.repeat("§f-", 40);
		
		player.sendMessage(" ");
		player.sendMessage("§b§lCasas de " + alvo.getName());
		player.sendMessage(filler);
		player.sendMessage(" ");
		
		if(hasUnknownHomes(uuid))
		{
			String world = getPlayersUnnamedHome(uuid).getWorld().getName();
			player.sendMessage("§bCasa Padrão: §fNo mundo " + world);
		}
		
		player.sendMessage(" ");
		
		if(hasNamedHomes(uuid))
		{
			for(String id : getPlayersNamedHomes(uuid).keySet())
			{
				String world = getPlayersNamedHomes(uuid).get(id).getWorld();
				
				player.sendMessage("§bCasa: §f" + id);
				player.sendMessage("§bMundo: §f" + world);
				
				player.sendMessage(" ");
			}
		}
		player.sendMessage(filler);
	}
	
	// TODO ConfigManagers - Getters
	
	public void saveAllConfigs()
	{
		playersyaml.saveConfig();
		niveis.saveConfig();
		configs.saveConfig();
		homes.saveConfig();
		warps.saveConfig();
		ranks.saveConfig();
	}
	
	public void reloadConfig()
	{
		configs.reloadConfig();
		niveis.reloadConfig();
		homes.reloadConfig();
		warps.reloadConfig();
		playersyaml.reloadConfig();
		ranks.reloadConfig();
	}
	
	public ConfigManager getWarpsConfig()
	{
		return this.warps;
	}
	
	public ConfigManager getRanksConfig()
	{
		return this.ranks;
	}
	
	public ConfigManager getLevelConfig()
	{
		return this.niveis;
	}
	
	public ConfigManager getPlayersConfig()
	{
		return this.playersyaml;
	}
	
	// TODO Metodos Privados - Setups e Registerers
	
	private void setupConfig()
	{
		playersyaml = new ConfigManager("players");
		playersyaml.saveDefaultConfig();
		
		niveis = new ConfigManager("niveis");
		niveis.saveDefaultConfig();
		
		configs = new ConfigManager("configs");
		configs.saveDefaultConfig();
		
		homes = new ConfigManager("homes");
		homes.saveDefaultConfig();
		
		warps = new ConfigManager("warps");
		warps.saveDefaultConfig();
		
		ranks = new ConfigManager("ranks");
		ranks.saveDefaultConfig();
		
		if (!(homes.getConfig().isSet("allNamedHomes") || homes.getConfig().isSet("unknownHomes")))
		{
			homes.getConfig().addDefault("allNamedHomes", new HashMap<String, HashMap<String, Home>>());
			homes.getConfig().addDefault("unknownHomes", new HashMap<String, Home>());
		}
	}
	
	private boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> economyProvider = 
				getServer().
				getServicesManager().
				getRegistration(Economy.class);
		
		if(economyProvider != null)
		{
			economy = (Economy) economyProvider.getProvider();
		}
		
		return(economy != null);
	}
	
	private void setupComandos()
	{
		addComando("nivel", new ComandoNivel());
		addComando("lixeira", new ComandoLixeira());
		addComando("evolution", new ComandoEvolution());
		addComando("tell", new ComandoTell());
		addComando("reply", new ComandoReply());
		addComando("reiniciar", new ComandoReiniciar());
		addComando("reparar", new ComandoReparar());
		addComando("tokenreparo", new ComandoTokenReparo());
		addComando("home", new ComandoHome());
		addComando("home-of", new ComandoHomeOf());
		addComando("homes", new ComandoHomes());
		addComando("delhome", new ComandoDelHome());
		addComando("delhome-of", new ComandoDelHomeOf());
		addComando("sethome", new ComandoSetHome());
		addComando("plugins", new ComandoPlugins());
		addComando("rankup", new ComandoRankup());
		addComando("op", new ComandoOp());
		addComando("deop", new ComandoDeop());
		addComando("playerwarp", new ComandoPlayerWarp());
		addComando("createplayerwarp", new ComandoCreatePlayerWarp());
		addComando("removeplayerwarp", new ComandoRemovePlayerWarp());
		addComando("mywarp", new ComandoMyWarp());
		addComando("toggle", new ComandoToggle());
		addComando("tpa", new ComandoTPA());
		addComando("tpaceitar", new ComandoTPAceitar());
		addComando("tpnegar", new ComandoTPNegar());
		addComando("itemraro", new ComandoItemRaro());
		
		CommandExecutor exe = new CommandManager();
		
		for(String cmd: comandos.keySet())
		{
			getCommand(cmd).setExecutor(exe);
		}
	}
	
	private void registerEvents()
	{
		registerEvents(new ListenerPlayerManager());
		registerEvents(new ListenerNivel());
		registerEvents(new ListenerChat());
		registerEvents(new ListenerAvulso());
		registerEvents(new ListenerEntityDeathBroadcast());
		registerEvents(new ListenerMenuReparar());
		registerEvents(new ListenerCommandManager());
		registerEvents(new ListenerSigns());
		registerEvents(new ListenerReiniciar());
		registerEvents(new ListenerPluginsMenu());
		registerEvents(new ListenerToggle());	
		//registerEvents(new ListenerAntiGrief());
		registerEvents(new ListenerPlayerWarpMenu());
	}
	
	private void startRunnableCore()
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				for(PlayerManager player : playersHashMap.values())
				{
					ScoreboardManager.update(player);
				}
			}
		}.runTaskTimer(this, 200, 200);
	}
	
	private void fixPlayerManager()
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			addPlayer(p.getName());
		}
	}
	
	private void setupRankups()
	{		
		for(int i = 0; i < 101; i++)
		{
			String path = "ranks." + String.valueOf(i);
			ConfigManager cf = ranks;
			
			String nome = cf.getString(path + ".nome");
			String prefixo = cf.getString(path + ".prefixo").replace("&", "§");
			double custo = cf.getDouble(path + ".custo");
			boolean ultimo = cf.getBoolean(path + ".ultimo");
			String proximo = cf.getString(path + ".proximo");
			int level = cf.getInt(path + ".nivel");
			
			rankups.put(nome, new Rank(nome, prefixo, ultimo, custo, level, proximo));
		}
	}
	
	// TODO Metodos JavaPlugin (Enable e Disable)
	public void onEnable()
	{
		instance = this;
		
		setupConfig();
		setupEconomy();
		setupRankups();
		setupComandos();
		registerEvents();
		fixPlayerManager();
		startRunnableCore();
	}
	
	public void onDisable()
	{
		saveAllConfigs();
		Bukkit.getScheduler().cancelTasks(this);
	}
}
