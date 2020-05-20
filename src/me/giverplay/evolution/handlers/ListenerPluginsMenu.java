package me.giverplay.evolution.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ListenerPluginsMenu implements Listener
{
	@EventHandler
	public void onClick(InventoryClickEvent event)
	{
		if(event.getWhoClicked().getOpenInventory().getTitle().startsWith("§0§lPlugins")) event.setCancelled(true);
	}
}
