package me.giverplay.evolution;

import java.util.ArrayList;
import java.util.logging.Logger;
import me.giverplay.evolution.command.CommandManager;
import me.giverplay.evolution.command.commands.EvolutionCommand;
import me.giverplay.evolution.data.YamlConfig;
import me.giverplay.evolution.modules.Module;
import me.giverplay.evolution.modules.home.HomeModule;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public final class EvolutionAPI
{
  private final ArrayList<Module> modules = new ArrayList<>();
  
  private final Evolution plugin;
  private final YamlConfig config;
  
  private CommandManager commandManager;
  private HomeModule homeModule;
  
  EvolutionAPI(Evolution plugin)
  {
    this.plugin = plugin;
    this.config = new YamlConfig("settings");
  }
  
  void load()
  {
    config.saveDefault(false);
    config.reload();
    
    registerModules();
    registerCommands();
  }
  
  void unload()
  {
    modules.forEach(Module::disable);
    config.save();
    
    Bukkit.getScheduler().cancelTasks(plugin);
    HandlerList.unregisterAll(plugin);
  }
  
  private void registerModules()
  {
    commandManager = new CommandManager(this);
    
    modules.add(homeModule = new HomeModule(this));
    modules.forEach(Module::enable);
  }
  
  private void registerCommands()
  {
    commandManager.registerCommand(new EvolutionCommand());
  }
  
  public CommandManager getCommandManager()
  {
    return commandManager;
  }
  
  public HomeModule getHomeModule()
  {
    return homeModule;
  }
  
  public YamlConfig getConfig()
  {
    return config;
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
