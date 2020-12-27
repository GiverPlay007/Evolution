package me.giverplay.evolution.modules.evolution;

import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.listeners.InventoryMenuListener;
import me.giverplay.evolution.modules.Module;
import org.bukkit.event.HandlerList;

public class EvolutionModule implements Module
{
  private final EvolutionAPI evolution;

  private InventoryMenuListener menuListener;

  private boolean enabled;

  public EvolutionModule(EvolutionAPI evolution)
  {
    this.evolution = evolution;

    setupCommands();
  }

  @Override
  public String getName()
  {
    return "Evolution";
  }

  @Override
  public void enable()
  {
    if(enabled) return;

    toggleCommands(true);
    registerEvents();

    enabled = true;
    evolution.getLogger().info("Modulo Evolution (Principal) habilitado.");
  }

  @Override
  public void disable()
  {
    if(!enabled) return;

    toggleCommands(false);
    unregisterEvents();

    enabled = false;
    evolution.getLogger().info("Modulo Evolution (Principal) desabilitado.");
  }

  @Override
  public boolean isEnabled()
  {
    return enabled;
  }

  @Override
  public void toggleCommands(boolean status)
  {

  }

  @Override
  public void setupCommands()
  {

  }

  @Override
  public void registerEvents()
  {
    menuListener = new InventoryMenuListener();
    evolution.registerListener(menuListener);
  }

  @Override
  public void unregisterEvents()
  {
    HandlerList.unregisterAll(menuListener);
  }
}
