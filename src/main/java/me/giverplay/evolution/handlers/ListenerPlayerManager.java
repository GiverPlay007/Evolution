package me.giverplay.evolution.handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.api.manager.ScoreboardManager;

public class ListenerPlayerManager implements Listener
{
	private Evolution plugin;
	
	public ListenerPlayerManager()
	{
		this.plugin = Evolution.getInstance();
	}
	
	@EventHandler
	public void onLogin(PlayerJoinEvent event)
	{
		String name = event.getPlayer().getName();
		
		if(!plugin.getPlayersConfig().getConfig().isSet(name))
		{
			plugin.getPlayersConfig().set(name + ".niveis.xp", 0);
			plugin.getPlayersConfig().set(name + ".niveis.nivel", 0);
			plugin.getPlayersConfig().set(name + ".niveis.rank", plugin.getRanksConfig().getString("ranks.0.nome"));
			
			event.getPlayer().sendMessage("§aBem-vindo, seu novo nével é §f0");
			
			Bukkit.getConsoleSender().sendMessage(name + " cadastrado.");
			plugin.getPlayersConfig().saveConfig();
		}
		
		plugin.addPlayer(name);
		plugin.getPlayer(name).setLoginTime(System.currentTimeMillis());
		ScoreboardManager.build(plugin.getPlayer(name));
		
		event.setJoinMessage("§a" + name + " entrou no servidor.");
		plugin.setHeaderAndFooter(event.getPlayer());
		
	}
	
	@EventHandler
	public void onLogout(PlayerQuitEvent event)
	{
		String name = event.getPlayer().getName();
		plugin.removePlayer(name);
		event.setQuitMessage("§c" + name + " saiu do servidor.");
	}
}
