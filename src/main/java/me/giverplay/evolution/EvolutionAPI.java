package me.giverplay.evolution;

import com.earth2me.essentials.Essentials;
import me.giverplay.evolution.command.CommandManager;
import me.giverplay.evolution.command.commands.EvolutionCommand;
import me.giverplay.evolution.modules.ModuleManager;
import me.giverplay.evolution.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class EvolutionAPI
{
  private final Evolution plugin;
  private final Essentials essentials;
  private final YamlConfiguration config;
  
  private CommandManager commandManager;
  private ModuleManager moduleManager;
  private PlayerManager playerManager;
  
  EvolutionAPI(Evolution plugin)
  {
    this.plugin = plugin;
    this.essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
    this.config = new YamlConfiguration();
  }
  
  void load()
  {
    plugin.saveResource("settings.yml", false);

    try {
      config.load(new File(plugin.getDataFolder(), "settings.yml"));
    } catch (IOException | InvalidConfigurationException e) {
      e.printStackTrace();
    }

    registerManagers();
    registerCommands();
    
    moduleManager.enable();
  }
  
  void unload()
  {
    moduleManager.disable();

    try {
      config.save(new File(plugin.getDataFolder(), "settings.yml"));
    } catch (IOException e) {
      e.printStackTrace();
    }

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
    commandManager.registerCommand(new EvolutionCommand(this));
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
  
  public YamlConfiguration getConfig()
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
  
  public void saveDatabase(CommandSender sender)
  {
    // TODO
  }
  
  public void toggleLockdown()
  {
    // TODO
  }
  
  public void reload(boolean plugin)
  {
    if(plugin)
    {
      PluginManager mng = Bukkit.getPluginManager();
      mng.disablePlugin(this.plugin);
      mng.enablePlugin(this.plugin);
      return;
    }
    
    // TODO
  }
}
