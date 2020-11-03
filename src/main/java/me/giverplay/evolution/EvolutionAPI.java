package me.giverplay.evolution;

import java.util.logging.Logger;
import me.giverplay.evolution.command.CommandManager;
import me.giverplay.evolution.command.commands.EvolutionCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public final class EvolutionAPI
{
  private final Evolution plugin;
  
  private CommandManager commandManager;
  
  EvolutionAPI(Evolution plugin)
  {
    this.plugin = plugin;
  }
  
  void load()
  {
    registerManagers();
    registerCommands();
  }
  
  void unload()
  {
    Bukkit.getScheduler().cancelTasks(plugin);
    HandlerList.unregisterAll(plugin);
  }
  
  private void registerManagers()
  {
    commandManager = new CommandManager(this);
  }
  
  private void registerCommands()
  {
    commandManager.registerCommand(new EvolutionCommand());
  }
  
  public Evolution getPlugin()
  {
    return this.plugin;
  }
  
  public Logger getLogger()
  {
    return plugin.getLogger();
  }
}
