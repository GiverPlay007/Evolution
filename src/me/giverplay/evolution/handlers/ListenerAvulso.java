package me.giverplay.evolution.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import br.net.fabiozumbi12.RedProtect.Bukkit.Region;
import br.net.fabiozumbi12.RedProtect.Bukkit.API.events.EnterExitRegionEvent;
import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ListenerAvulso implements Listener
{
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event)
	{
		EntityType nasceu = event.getEntityType();

		if((event.getEntityType() == EntityType.PHANTOM) 
				|| (nasceu == EntityType.PILLAGER)
				|| (nasceu == EntityType.VINDICATOR)
				|| (nasceu == EntityType.EVOKER)
				)
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onSleep(PlayerBedEnterEvent e)
	{ 
		Player player = e.getPlayer();

		if(Evolution.getInstance().getBedCooldownList().contains(player.getName()))
		{
			player.sendMessage("§cVocê não pode mudar o tempo agora...");
			return;
		}

		player.getWorld().setTime(0L);

		Bukkit.broadcastMessage("§a" + player.getName() + " §adormiu, agora é dia no mundo §f" 
				+ e.getPlayer().getWorld().getName());
		Evolution.getInstance().getBedCooldownList().add(player.getName());

		Bukkit.getServer().getScheduler().runTaskLater(Evolution.getInstance(), new Runnable()
		{
			@Override
			public void run() 
			{
				Evolution.getInstance().getBedCooldownList().remove(player.getName());
			}
		}, 30 * 20);
	}
	
	@EventHandler
	public void onRPEnter(EnterExitRegionEvent event)
	{ // Quando entrar ou sair de um RP
		PlayerManager player = Evolution.getInstance().getPlayer(event.getPlayer().getName());
		
		if(!player.isVip()) return; // Se o jogador for vip...
		
		Region enter = event.getEnteredRegion();
		Region exit = event.getExitedRegion();
		
		if(enter != null)
		{
			if(enter.isLeader(player.getPlayer())) 
			{
				player.getPlayer().setFlying(true);
				player.sendMessage("§aModo voar ativado!");
			}
		}
		
		if(exit != null)
		{
			if(exit.isLeader(player.getPlayer())) 
			{
				player.getPlayer().setFlying(false);
				player.sendMessage("§cModo voar desativado!");
			}
		}
	}
}
