package me.giverplay.evolution;

import com.earth2me.essentials.Essentials;
import java.util.logging.Logger;
import me.giverplay.evolution.command.CommandManager;
import me.giverplay.evolution.command.commands.EvolutionCommand;
import me.giverplay.evolution.data.YamlConfig;
import me.giverplay.evolution.modules.ModuleManager;
import me.giverplay.evolution.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public final class EvolutionAPI
{
  private final Evolution plugin;
  private final Essentials essentials;
  private final YamlConfig config;
  
  private CommandManager commandManager;
  private ModuleManager moduleManager;
  private PlayerManager playerManager;
  
  EvolutionAPI(Evolution plugin)
  {
    this.plugin = plugin;
    this.essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
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
    playerManager = new PlayerManager(this);
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
  
  public PlayerManager getPlayerManager()
  {
    return playerManager;
  }
  
  public ModuleManager getModuleManager()
  {
    return moduleManager;
  }
  
  public YamlConfig getConfig()
  {
    return config;
  }
  
  public Evolution getPlugin()
  {
    return this.plugin;
  }
  
  public Essentials getEssentials()
  {
    return this.essentials;
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
