package me.giverplay.evolution.handlers;

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

import me.giverplay.evolution.Variaveis;

public class ListenerReiniciar implements Listener
{
	// Codigo original por GuiHSilva
	
	@EventHandler
	private void event1(BlockPlaceEvent e) {
		if (Variaveis.bloqueartudo) {
			if (!e.getPlayer().isOp()) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(" ");
				e.getPlayer().sendMessage(Variaveis.blockmsg);	
				e.getPlayer().sendMessage(" ");
			}
		}
	}
	
	@EventHandler
	private void event2(PlayerCommandPreprocessEvent e) {
		if (Variaveis.bloqueartudo) {
			if (!e.getPlayer().isOp()) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(" ");
				e.getPlayer().sendMessage(Variaveis.blockmsg);	
				e.getPlayer().sendMessage(" ");}
		}
	}

	@EventHandler
	private void event3(PlayerInteractEvent e) {
		if (Variaveis.bloqueartudo) {
			if (!e.getPlayer().isOp()) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(" ");
				e.getPlayer().sendMessage(Variaveis.blockmsg);	
				e.getPlayer().sendMessage(" ");
			}
		}
	}

	@EventHandler
	private void event4(EntityDamageEvent e) {
		if (Variaveis.bloqueartudo) {
			if (!e.getEntity().isOp()) {
				e.setCancelled(true);
				e.getEntity().sendMessage(" ");
				e.getEntity().sendMessage(Variaveis.blockmsg);	
				e.getEntity().sendMessage(" ");
				}
		}
	}
	

	@EventHandler
	private void event5(EntityDamageByEntityEvent e) {
		if (Variaveis.bloqueartudo) {
			if (!e.getDamager().isOp()) {
				e.setCancelled(true);
				e.getDamager().sendMessage(" ");
				e.getDamager().sendMessage(Variaveis.blockmsg);	
				e.getDamager().sendMessage(" ");
				
			}
		}
	}
	
	@EventHandler
	private void event6(BlockBreakEvent e) {
		if (Variaveis.bloqueartudo) {
			if (!e.getPlayer().isOp()) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(" ");
				e.getPlayer().sendMessage(Variaveis.blockmsg);	
				e.getPlayer().sendMessage(" ");
			}
		}
	}
	
	@EventHandler
	private void event7(PlayerDropItemEvent e) {
		if (Variaveis.bloqueartudo) {
			if (!e.getPlayer().isOp()) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(" ");
				e.getPlayer().sendMessage(Variaveis.blockmsg);	
				e.getPlayer().sendMessage(" ");
			}
		}
	}

	@EventHandler
	private void event8(InventoryInteractEvent e) {
		if (Variaveis.bloqueartudo) {
			if (!e.getWhoClicked().isOp()) {
				e.setCancelled(true);
				e.getWhoClicked().sendMessage(" ");
				e.getWhoClicked().sendMessage(Variaveis.blockmsg);	
				e.getWhoClicked().sendMessage(" ");
			}
		}
	}
}
