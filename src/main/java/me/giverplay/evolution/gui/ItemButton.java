package me.giverplay.evolution.gui;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class ItemButton
{
  private final HashMap<ClickType, OnClickListener> CLICKS = new HashMap<>();

  public void onClick(InventoryClickEvent event)
  {
    OnClickListener listener = CLICKS.get(event.getClick());

    if(listener != null)
    {
      listener.onClick(event);
    }
  }

  public final void setOnClickListener(ClickType type, OnClickListener listener)
  {
    CLICKS.put(type, listener);
  }

  public final void removeOnClickListener(ClickType type)
  {
    CLICKS.remove(type);
  }
}
