package me.giverplay.evolution;

import java.util.logging.Logger;
import me.giverplay.evolution.command.CommandManager;
import me.giverplay.evolution.command.commands.EvolutionCommand;
import me.giverplay.evolution.data.YamlConfig;
import me.giverplay.evolution.modules.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public final class EvolutionAPI
{
  private final Evolution plugin;
  private final YamlConfig config;
  
  private ModuleManager moduleManager;
  private CommandManager commandManager;
  
  EvolutionAPI(Evolution plugin)
  {
    this.plugin = plugin;
    this.config = new YamlConfig("settings");
  }
  
  void load()
  {
    config.saveDefault(false);
    config.reload();
    
    registerManagers();
    registerCommands();
    
    moduleManager.enable();
  }
  
  void unload()
  {
    moduleManager.disable();
    config.save();
    
    Bukkit.getScheduler().cancelTasks(plugin);
    HandlerList.unregisterAll(plugin); // Para precavir, caso venha do setEnabled() sem o disablePlugin()
  }
  
  boolean shouldLoad()
  {
    return checkDependency("Vault") && checkDependency("Essentials") && checkDependency("PermissionsEx");
  }
  
  private boolean checkDependency(String name)
  {
    boolean has = true;
    
    if(!Bukkit.getPluginManager().isPluginEnabled(name))
    {
      getLogger().warning("Plugin " + name + " não foi encontrado no servidor ou está desabilitado.");
      has = false;
    }
    
    return has;
  }
  
  private void registerManagers()
  {
    commandManager = new CommandManager(this);
    moduleManager = new ModuleManager(this);
  }
  
  private void registerCommands()
  {
    commandManager.registerCommand(new EvolutionCommand());
  }
  
  public CommandManager getCommandManager()
  {
    return commandManager;
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
  
  public void registerListener(Listener listener)
  {
    Bukkit.getPluginManager().registerEvents(listener, plugin);
  }
}
