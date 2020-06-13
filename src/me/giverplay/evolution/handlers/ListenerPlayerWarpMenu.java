package me.giverplay.evolution.handlers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.giverplay.evolution.Evolution;

@SuppressWarnings("deprecation")
public class ListenerPlayerWarpMenu implements Listener
{
	@EventHandler
	public void onClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		InventoryView inv = player.getOpenInventory();
		ItemStack item = event.getCurrentItem();
		
		if(inv.getTitle().startsWith("Warps de"))
		{
			event.setCancelled(true);
			
			OfflinePlayer vip = Bukkit.getOfflinePlayer(inv.getTitle().replace("Warps de ", "").trim());
			
			if(item == null || !item.hasItemMeta()) return;
			
			String warpName = item.getItemMeta().getDisplayName().replace("§b", "").trim();
			
			player.closeInventory();
			Evolution.getInstance().getPlayerWarp(vip.getName(), warpName).teleportWarp(player);;
		}
	}
}
