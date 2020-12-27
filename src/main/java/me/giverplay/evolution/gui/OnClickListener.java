package me.giverplay.evolution.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface OnClickListener
{
  void onClick(InventoryClickEvent event);
}
