package me.giverplay.evolution.handlers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.giverplay.evolution.api.Stacks;

@SuppressWarnings("deprecation")
public class ListenerMenuReparar implements Listener
{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		InventoryView inv = player.getOpenInventory();
		ItemStack item = event.getCurrentItem();
		
		if((inv.getTitle() == "§0§lReparar") && (item != null))
		{
			event.setCancelled(true);
			
			if(item.getType() == Material.BARRIER)
			{
				player.closeInventory();
				return;
			}
			
			if(item.getType() == Material.ANVIL)
			{
				if(!player.getInventory().containsAtLeast(Stacks.getTokenReparo(1), 1))
				{
					player.sendMessage("§cVocê não tem Tokens de Reparo. Compre com um(a) §f§nFerreiro(a)");
					return;
				}
				
				player.getInventory().removeItem(Stacks.getTokenReparo(1));
				
				player.closeInventory();
				player.getInventory().getItemInMainHand().setDurability((short) 0);
				player.sendMessage("§aItem reparado");
			}
		}
	}
}
