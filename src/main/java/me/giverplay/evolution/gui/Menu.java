package me.giverplay.evolution.gui;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Menu implements InventoryHolder
{
  protected String title;
  
  protected final boolean personal;
  protected final int size;
  
  public Menu(String title, boolean personal, int size)
  {
    Validate.notNull(title, "O nome do menu não deve ser nulo!");
    Validate.isTrue(size > 0 && size < 55 && size % 9 == 0, "Número de slots é inválido!");
    
    this.title = title;
    this.personal = personal;
    this.size = size;
    
    createInventory();
  }
  
  protected Inventory createInventory()
  {
    return Bukkit.createInventory(this, size, title);
  }
  
  @Override
  public Inventory getInventory()
  {
    return createInventory();
  }
  
  public String getTitle()
  {
    return title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public boolean isPersonal()
  {
    return personal;
  }
  
  public int getSize()
  {
    return size;
  }
}
