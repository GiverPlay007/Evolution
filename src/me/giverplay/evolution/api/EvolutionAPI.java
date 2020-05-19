package me.giverplay.evolution.api;

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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.giverplay.evolution.Variaveis;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.manager.ConfigManager;
import me.giverplay.evolution.api.manager.PlayerManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_15_R1.MinecraftServer;

@SuppressWarnings("deprecation")
public class EvolutionAPI
{
	public static void saveAllConfigs()
	{
		Variaveis.playersyaml.saveConfig();
		Variaveis.niveis.saveConfig();
		Variaveis.configs.saveConfig();
		Variaveis.homes.saveConfig();
		Variaveis.warps.saveConfig();
		Variaveis.ranks.saveConfig();
	}
	
	public static void reloadConfig()
	{
		Variaveis.configs.reloadConfig();
		Variaveis.niveis.reloadConfig();
		Variaveis.homes.reloadConfig();
		Variaveis.warps.reloadConfig();
		Variaveis.playersyaml.reloadConfig();
		Variaveis.ranks.reloadConfig();
	}
	
	public static void addPlayer(String name)
	{
		Variaveis.playersHashMap.put(name, new PlayerManager(name));
	}
	
	public static void removePlayer(String name)
	{
		Variaveis.playersHashMap.remove(name);
	}
	
	public static PlayerManager getPlayer(String name)
	{
		return Variaveis.playersHashMap.get(name);
	}
	
	public static void registerEvents(Listener listener)
	{
		Bukkit.getPluginManager().registerEvents(listener, Variaveis.plugin);
	}
	
	public static void addComando(String nome, Comando comando)
	{
		Variaveis.comandos.put(nome, comando);
	}
	
	public static String getTime(int segundos)
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
		
		String concatenacaoDosMinutosComOsSegundosParaRetornarOResultadoDeFormaLiteralStringBuilder = tempo.toString();
		
		return concatenacaoDosMinutosComOsSegundosParaRetornarOResultadoDeFormaLiteralStringBuilder;
	}
	
	public static String createTimerLabel(int segundos)
	{
		String timeLabel = "";
		int min, sec;
		
		min = segundos / 60;
		sec = segundos % 60;
		
		timeLabel += min + ":";
		timeLabel += (sec < 10 ? "0" + sec : sec);
		return timeLabel;
	}
	
	public static void sendAction(Player player, String message)
	{
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	}
	
	public static String getTPS()
	{
		double tps = MinecraftServer.getServer().recentTps[0]; 
		return String.valueOf(((tps > 18.0D) ? ChatColor.GREEN : (
				(tps > 16.0D) ? ChatColor.YELLOW : ChatColor.RED)).toString()) + (
						(tps > 20.0D) ? "*" : "") + Math.min(Math.round(tps * 100.0D) / 100.0D, 20.0D);
	}
	
	public static double calcularSalario(PlayerManager player)
	{
		long tempoOnline = (System.currentTimeMillis() - player.getLoginTime()) / 1000;
		
		double salario = (tempoOnline * 80) / 1440;
		
		return salario;
	}
	
	// Baseado no plugin SetHomes
	public static HashMap<String, Home> getPlayersNamedHomes(String uuid)
	{
		HashMap<String, Home> playersNamedHomes = new HashMap<String, Home>();
		String homesPath = "allNamedHomes." + uuid;
		YamlConfiguration homesCfg = Variaveis.homes.getConfig();
		
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
	
	public static void saveNamedHome(String uuid, Home home)
	{
		String path = "allNamedHomes." + uuid + "." + home.getHomeName();
		ConfigManager homesCfg = Variaveis.homes;
		
		homesCfg.set(path + ".world", home.getWorld());
		homesCfg.set(path + ".x", home.getX());
		homesCfg.set(path + ".y", home.getY());
		homesCfg.set(path + ".z", home.getZ());
		homesCfg.set(path + ".pitch", home.getPitch());
		homesCfg.set(path + ".yaw", home.getYaw());
		homesCfg.saveConfig();
	}
	
	public static boolean hasNamedHomes(String uuid)
	{
		return Variaveis.homes.contains("allNamedHomes." + uuid) && Variaveis.homes.getConfig().isSet("allNamedHomes." + uuid);
	}
	
	public static void saveUnknownHome(String uuid, Home home)
	{
		String path = "unknownHomes." + uuid;
		ConfigManager homesCfg = Variaveis.homes;
		
		homesCfg.set(path + ".world", home.getWorld());
		homesCfg.set(path + ".x", home.getX());
		homesCfg.set(path + ".y", home.getY());
		homesCfg.set(path + ".z", home.getZ());
		homesCfg.set(path + ".pitch", home.getPitch());
		homesCfg.set(path + ".yaw", home.getYaw());
		homesCfg.saveConfig();
	}
	
	public static boolean hasUnknownHomes(String uuid)
	{
		return Variaveis.homes.getConfig().contains("unknownHomes." + uuid);
	}
	
	public static void deleteUnknownHome(String uuid)
	{
		String path = "unknownHomes." + uuid;
		
		Variaveis.homes.getConfig().set(path, null);
		Variaveis.homes.saveConfig();
	}
	
	public static Location getNamedHomeLocal(String uuid, String homeName)
	{
		Home h = getPlayersNamedHomes(uuid).get(homeName);
		World world = Bukkit.getWorld(h.getWorld());
		Location home = new Location(world, h.getX(), h.getY(), h.getZ(), h.getYaw(), h.getPitch());
		
		return home;
	}
	
	public static Location getPlayersUnnamedHome(String uuid)
	{
		String path = "unknownHomes." + uuid;
		ConfigManager homesCfg = Variaveis.homes;
		
		World world = Bukkit.getWorld(homesCfg.getString(path + ".world"));
		double x = homesCfg.getDouble(path + ".x");
		double y = homesCfg.getDouble(path + ".y");
		double z = homesCfg.getDouble(path + ".z");
		float pitch = Float.parseFloat(homesCfg.getString(path + ".pitch"));
		float yaw = Float.parseFloat(homesCfg.getString(path + ".yaw"));
		
		return new Location(world, x, y, z, yaw, pitch);
	}
	
	public static void deleteNamedHome(String uuid, String homeName)
	{
		String path = "allNamedHomes." + uuid + "." + homeName;
		
		Variaveis.homes.set(path, null);
		Variaveis.homes.saveConfig();
	}
	
	public static void teleportHome(Player player, String uuid, String[] args)
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
	
	public static void teleportHomeOf(Player player, String uuid, String[] args)
	{
		if(args.length == 1)
		{
			if(!hasUnknownHomes(uuid)){
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
	
	public static void listHomes(Player player)
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
	
	public static void listHomes(Player alvo, Player player)
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
	
	public static Class<?> getNMSClass(String name){
		try {
			return Class.forName("net.minecraft.server.v1_14_R1." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void setHeaderAndFooter(Player player) {
		player.setPlayerListHeaderFooter("§a§lEvolution§f§lCity\n\n§7----------",
				"§7----------\n\n§eBom jogo!");
	}
	
	public static String getProgress(PlayerManager player)
	{
		double custo = Variaveis.rankups.get(player.getRank().getNextRank()).getCost();
		double money = Variaveis.economy.getBalance(player.getPlayer());
		
		if(money >= custo){
			return "§8[§a" + StringUtils.repeat("|", 15) + "§8]";
		}
		
		return PBar.getProgressBarScore(money, custo, 15, "|", "§a", "§7", "§8[", "§8]");
	}
	
	public static String getMoney(Player p){
		return Formater.format((long) Variaveis.economy.getBalance(p));
	}
	
	public static ArrayList<PlayerWarp> getWarps(String nickname){
		YamlConfiguration conf = Variaveis.warps.getConfig();
		
		if(!conf.isSet(nickname)){
			return null;
		}
		
		ArrayList<PlayerWarp> list = new ArrayList<>();
		
		for(String key : conf.getConfigurationSection(nickname).getKeys(false)){
			try{
				if(!conf.isSet(nickname + "." + key + ".loc")){
					continue;
				}
				
				list.add(new PlayerWarp(conf.getLocation(nickname + "." + key + ".loc"), key, nickname));
			}
			catch(NullPointerException e) {
				Bukkit.getConsoleSender().sendMessage("§c[Evolution] Erro em uma warp............");
			}
		}
		
		if(list.isEmpty()){
			return null;
		}
		
		return list;
	}
	
	public static PlayerWarp getPlayerWarp(String nickname, String warp){
		try{
			YamlConfiguration conf = Variaveis.warps.getConfig();
			
			if(!conf.isSet(nickname) || !conf.isSet(nickname + "." + warp)){
				return null;
			}
			
			return new PlayerWarp(conf.getLocation(nickname + "." + warp + ".loc"), warp, nickname);
		}
		catch(NullPointerException e){
			return null;
		}
	}
}
