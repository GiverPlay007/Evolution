package me.giverplay.evolution;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

import me.giverplay.evolution.api.Rank;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.manager.ConfigManager;
import me.giverplay.evolution.api.manager.PlayerManager;
import net.milkbowl.vault.economy.Economy;

public class Variaveis
{
	public static Plugin plugin;
	public static ConsoleCommandSender console;
	
	public static boolean reiniciando;
	public static boolean bloqueartudo;
	public static String blockmsg;
	
	public static ConfigManager playersyaml;
	public static ConfigManager niveis;
	public static ConfigManager configs;
	public static ConfigManager homes;
	public static ConfigManager warps;
	public static ConfigManager ranks;

	public static Economy economy;
	
	public static ArrayList<String> deitar;
	
	public static HashMap<String, Rank> rankups;
	public static HashMap<String, PlayerManager> playersHashMap;
	public static HashMap<String, Comando> comandos;
	public static HashMap<String, Long> summonDragon;
}
