package me.giverplay.evolution.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.giverplay.evolution.Variaveis;
import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.manager.ScoreboardManager;

public class ListenerPlayerManager implements Listener
{
	@EventHandler
	public void onLogin(PlayerJoinEvent event)
	{
		String name = event.getPlayer().getName();
		
		if(!Variaveis.playersyaml.getConfig().isSet(name))
		{
			Variaveis.playersyaml.set(name + ".niveis.xp", 0);
			Variaveis.playersyaml.set(name + ".niveis.nivel", 0);
			Variaveis.playersyaml.set(name + ".niveis.rank", Variaveis.ranks.getString("ranks.0.nome"));
			
			event.getPlayer().sendMessage("§aBem-vindo, seu novo nível é §f0");
			
			Variaveis.console.sendMessage(name + " cadastrado.");
			Variaveis.playersyaml.saveConfig();
		}
		
		EvolutionAPI.addPlayer(name);
		EvolutionAPI.getPlayer(name).setLoginTime(System.currentTimeMillis());
		ScoreboardManager.build(EvolutionAPI.getPlayer(name));
		
		event.setJoinMessage("§a" + name + " entrou no servidor.");
		EvolutionAPI.setHeaderAndFooter(event.getPlayer());
		
	}
	
	@EventHandler
	public void onLogout(PlayerQuitEvent event)
	{
		String name = event.getPlayer().getName();
		EvolutionAPI.removePlayer(name);
		event.setQuitMessage("§c" + name + " saiu do servidor.");
	}
}
