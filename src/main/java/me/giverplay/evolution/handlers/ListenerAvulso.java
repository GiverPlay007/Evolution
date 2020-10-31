package me.giverplay.evolution.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import me.giverplay.evolution.Evolution;

public class ListenerAvulso implements Listener
{
	private Evolution plugin;
	
	public ListenerAvulso()
	{
		plugin = Evolution.getInstance();
	}
	
	@EventHandler
	public void onSleep(PlayerBedEnterEvent e)
	{ 
		Player player = e.getPlayer();

		if(plugin.getBedCooldownList().contains(player.getName()))
		{
			player.sendMessage("§cVocê não pode mudar o tempo agora...");
			return;
		}

		player.getWorld().setTime(0L);

		Bukkit.broadcastMessage("§a" + player.getName() + " §adormiu, agora é dia no mundo §f"
				+ e.getPlayer().getWorld().getName());
		plugin.getBedCooldownList().add(player.getName());

		Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable()
		{
			@Override
			public void run() 
			{
				plugin.getBedCooldownList().remove(player.getName());
			}
		}, 30 * 20);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		
		plugin.getPlayer(player.getName()).setDeathLocation(player.getLocation().clone());
	}
}
