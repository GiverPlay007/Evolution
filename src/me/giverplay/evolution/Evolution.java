package me.giverplay.evolution;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.Formater;
import me.giverplay.evolution.api.Home;
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
import me.giverplay.evolution.comandos.ComandoTell;
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
import net.milkbowl.vault.economy.Economy;

public class Evolution extends JavaPlugin 
{
	private static Evolution instance;
	private BukkitTask task;
	
	private void setupConfig()
	{
		Variaveis.playersyaml = new ConfigManager("players");
		Variaveis.playersyaml.saveDefaultConfig();
		
		Variaveis.niveis = new ConfigManager("niveis");
		Variaveis.niveis.saveDefaultConfig();
		
		Variaveis.configs = new ConfigManager("configs");
		Variaveis.configs.saveDefaultConfig();
		
		Variaveis.homes = new ConfigManager("homes");
		Variaveis.homes.saveDefaultConfig();
		
		Variaveis.warps = new ConfigManager("warps");
		Variaveis.warps.saveDefaultConfig();
		
		Variaveis.ranks = new ConfigManager("ranks");
		Variaveis.ranks.saveDefaultConfig();
		
		if (!(Variaveis.homes.getConfig().isSet("allNamedHomes") || Variaveis.homes.getConfig().isSet("unknownHomes")))
		{
			Variaveis.homes.getConfig().addDefault("allNamedHomes", new HashMap<String, HashMap<String, Home>>());
			Variaveis.homes.getConfig().addDefault("unknownHomes", new HashMap<String, Home>());
		}
	}
	
	private void setupVars()
	{
		instance = this;
		
		Variaveis.console = Bukkit.getConsoleSender();
		
		Variaveis.deitar = new ArrayList<String>();
		
		Variaveis.playersHashMap = new HashMap<String, PlayerManager>();
		Variaveis.summonDragon = new HashMap<String, Long>();
		Variaveis.comandos = new HashMap<String, Comando>();
		Variaveis.rankups = new HashMap<String, Rank>();
	}
	
	private boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> economyProvider = 
				getServer().
				getServicesManager().
				getRegistration(Economy.class);
		
		if(economyProvider != null)
		{
			Variaveis.economy = (Economy) economyProvider.getProvider();
		}
		
		return(Variaveis.economy != null);
	}
	
	public static Evolution getInstance(){
		return instance;
	}
	
	private void setupComandos()
	{
		EvolutionAPI.addComando("nivel", new ComandoNivel());
		EvolutionAPI.addComando("lixeira", new ComandoLixeira());
		EvolutionAPI.addComando("evolution", new ComandoEvolution());
		EvolutionAPI.addComando("tell", new ComandoTell());
		EvolutionAPI.addComando("reply", new ComandoReply());
		EvolutionAPI.addComando("reiniciar", new ComandoReiniciar());
		EvolutionAPI.addComando("reparar", new ComandoReparar());
		EvolutionAPI.addComando("tokenreparo", new ComandoTokenReparo());
		EvolutionAPI.addComando("home", new ComandoHome());
		EvolutionAPI.addComando("home-of", new ComandoHomeOf());
		EvolutionAPI.addComando("homes", new ComandoHomes());
		EvolutionAPI.addComando("delhome", new ComandoDelHome());
		EvolutionAPI.addComando("delhome-of", new ComandoDelHomeOf());
		EvolutionAPI.addComando("sethome", new ComandoSetHome());
		EvolutionAPI.addComando("plugins", new ComandoPlugins());
		EvolutionAPI.addComando("rankup", new ComandoRankup());
		EvolutionAPI.addComando("op", new ComandoOp());
		EvolutionAPI.addComando("deop", new ComandoDeop());
		EvolutionAPI.addComando("playerwarp", new ComandoPlayerWarp());
		EvolutionAPI.addComando("createplayerwarp", new ComandoCreatePlayerWarp());
		EvolutionAPI.addComando("removeplayerwarp", new ComandoRemovePlayerWarp());
		EvolutionAPI.addComando("mywarp", new ComandoMyWarp());
		//EvolutionAPI.addComando("tntrun", new ComandoTntRun());
		
		CommandExecutor exe = new CommandManager();
		
		for(String cmd: Variaveis.comandos.keySet())
		{
			getCommand(cmd).setExecutor(exe);
		}
	}
	
	private void registerEvents()
	{
		EvolutionAPI.registerEvents(new ListenerPlayerManager());
		EvolutionAPI.registerEvents(new ListenerNivel());
		EvolutionAPI.registerEvents(new ListenerChat());
		EvolutionAPI.registerEvents(new ListenerAvulso());
		EvolutionAPI.registerEvents(new ListenerEntityDeathBroadcast());
		EvolutionAPI.registerEvents(new ListenerMenuReparar());
		EvolutionAPI.registerEvents(new ListenerCommandManager());
		EvolutionAPI.registerEvents(new ListenerSigns());
		EvolutionAPI.registerEvents(new ListenerReiniciar());
		EvolutionAPI.registerEvents(new ListenerPluginsMenu());
		//EvolutionAPI.registerEvents(new ListenerAntiGrief());
		EvolutionAPI.registerEvents(new ListenerPlayerWarpMenu());
		//EvolutionAPI.registerEvents(new ListenerTntRun());
	}
	
	private void startRunnableCore(){
		this.task = new BukkitRunnable()
		{
			@Override
			public void run()
			{
				for(PlayerManager player : Variaveis.playersHashMap.values()){
					ScoreboardManager.update(player);
				}
			}
		}.runTaskTimer(this, 200, 200);
	}
	
	private void fixPlayerManager(){
		for(Player p : Bukkit.getOnlinePlayers()){
			EvolutionAPI.addPlayer(p.getName());
		}
	}
	
	private void setupRankups(){		
		for(int i = 0; i < 101; i++){
			String path = "ranks." + String.valueOf(i);
			ConfigManager cf = Variaveis.ranks;
			
			String nome = cf.getString(path + ".nome");
			String prefixo = cf.getString(path + ".prefixo").replace("&", "§");
			double custo = cf.getDouble(path + ".custo");
			boolean ultimo = cf.getBoolean(path + ".ultimo");
			String proximo = cf.getString(path + ".proximo");
			int level = cf.getInt(path + ".nivel");
			
			Variaveis.rankups.put(nome, new Rank(nome, prefixo, ultimo, custo, level, proximo));
		}
	}
	
	protected BukkitTask getTaskID(){
		return this.task;
	}
	
	public void onEnable()
	{
		new Formater();
		
		Variaveis.plugin = this;
		setupConfig();
		setupVars();
		setupEconomy();
		setupRankups();
		setupComandos();
		registerEvents();
		fixPlayerManager();
		startRunnableCore();
	}
	
	public void onDisable()
	{
		EvolutionAPI.saveAllConfigs();
		Bukkit.getScheduler().cancelTasks(this);
	}
}
