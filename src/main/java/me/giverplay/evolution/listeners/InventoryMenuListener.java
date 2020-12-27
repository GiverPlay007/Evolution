package me.giverplay.evolution.listeners;

import me.giverplay.evolution.gui.InventoryMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryMenuListener implements Listener
{
  @EventHandler
  public void onClick(InventoryClickEvent event)
  {
    if(!(event.getWhoClicked() instanceof Player))
    {
      return;
    }

    Inventory inventory = event.getClickedInventory();

    if(inventory == null)
    {
      return;
    }

    InventoryHolder holder = inventory.getHolder();

    if(holder instanceof InventoryMenu)
    {
      ((InventoryMenu) holder).onClick(event);
    }
  }
}
