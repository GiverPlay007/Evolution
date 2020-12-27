package me.giverplay.evolution.gui;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

public class InventoryMenu implements InventoryHolder
{
  private final HashMap<Integer, ItemButton> BUTTONS = new HashMap<>();

  private final boolean DEFAULT_CANCEL;

  private final Inventory inventory;

  public InventoryMenu(String title, int slots, boolean defaultCancel)
  {
    Validate.notNull(title, "O titulo do inventário não pode ser nulo!");
    Validate.isTrue(slots <= 54 && slots > 0 && slots % 9 != 0, "Número de slots inválido");

    this.inventory = Bukkit.createInventory(this, slots, title);
    this.DEFAULT_CANCEL = defaultCancel;
  }

  @Override
  public final Inventory getInventory()
  {
    return inventory;
  }

  public final void addButton(int slot, ItemButton button)
  {
    BUTTONS.put(slot, button);
  }

  public final void onClick(InventoryClickEvent event)
  {
    if(DEFAULT_CANCEL)
    {
      event.setCancelled(true);
    }

    ItemButton button = BUTTONS.get(event.getRawSlot());

    if(button != null)
    {
      button.onClick(event);
    }
  }
}
