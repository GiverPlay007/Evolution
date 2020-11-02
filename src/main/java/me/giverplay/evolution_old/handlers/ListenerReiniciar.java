package me.giverplay.evolution_old.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.giverplay.evolution_old.Evolution;

public class ListenerReiniciar implements Listener
{
	// Codigo original por GuiHSilva
	
	private String blockmsg = "§c * Você não pode interagir agora, para evitar perda de itens ou progresso, aguarde a reinicialização";
	private Evolution plugin;
	
	public ListenerReiniciar()
	{
		plugin = Evolution.getInstance();
	}
	
	@EventHandler
	private void event1(BlockPlaceEvent e) 
	{
		if (plugin.blockAllRestart()) 
		{
			if (!e.getPlayer().hasPermission("evolution.reiniciar.bypass")) 
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage(" ");
				e.getPlayer().sendMessage(blockmsg);	
				e.getPlayer().sendMessage(" ");
			}
		}
	}
	
	@EventHandler
	private void event2(PlayerCommandPreprocessEvent e) 
	{
		if (plugin.blockAllRestart())
		{
			if (!e.getPlayer().hasPermission("evolution.reiniciar.bypass")) 
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage(" ");
				e.getPlayer().sendMessage(blockmsg);	
				e.getPlayer().sendMessage(" ");}
		}
	}
	
	@EventHandler
	private void event3(PlayerInteractEvent e)
	{
		if (plugin.blockAllRestart()) 
		{
			if (!e.getPlayer().hasPermission("evolution.reiniciar.bypass")) 
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage(" ");
				e.getPlayer().sendMessage(blockmsg);	
				e.getPlayer().sendMessage(" ");
			}
		}
	}
	
	@EventHandler
	private void event4(EntityDamageEvent e)
	{
		if (plugin.blockAllRestart()) 
		{
			if (!e.getEntity().hasPermission("evolution.reiniciar.bypass"))
			{
				e.setCancelled(true);
				e.getEntity().sendMessage(" ");
				e.getEntity().sendMessage(blockmsg);	
				e.getEntity().sendMessage(" ");
			}
		}
	}
	
	
	@EventHandler
	private void event5(EntityDamageByEntityEvent e) 
	{
		if (plugin.blockAllRestart())
		{
			if (!e.getDamager().hasPermission("evolution.reiniciar.bypass"))
			{
				e.setCancelled(true);
				e.getDamager().sendMessage(" ");
				e.getDamager().sendMessage(blockmsg);	
				e.getDamager().sendMessage(" ");
				
			}
		}
	}
	
	@EventHandler
	private void event6(BlockBreakEvent e) 
	{
		if (plugin.blockAllRestart()) 
		{
			if (!e.getPlayer().hasPermission("evolution.reiniciar.bypass")) 
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage(" ");
				e.getPlayer().sendMessage(blockmsg);	
				e.getPlayer().sendMessage(" ");
			}
		}
	}
	
	@EventHandler
	private void event7(PlayerDropItemEvent e) 
	{
		if (plugin.blockAllRestart()) 
		{
			if (!e.getPlayer().hasPermission("evolution.reiniciar.bypass")) 
			{
				e.setCancelled(true);
				e.getPlayer().sendMessage(" ");
				e.getPlayer().sendMessage(blockmsg);	
				e.getPlayer().sendMessage(" ");
			}
		}
	}
	
	@EventHandler
	private void event8(InventoryInteractEvent e) 
	{
		if (plugin.blockAllRestart()) 
		{
			if (!e.getWhoClicked().hasPermission("evolution.reiniciar.bypass")) 
			{
				e.setCancelled(true);
				e.getWhoClicked().sendMessage(" ");
				e.getWhoClicked().sendMessage(blockmsg);	
				e.getWhoClicked().sendMessage(" ");
			}
		}
	}
}
