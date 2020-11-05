package me.giverplay.evolution.modules.home;

import java.util.HashMap;
import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.data.YamlConfig;
import me.giverplay.evolution.modules.Module;

public class HomeModule implements Module
{
  private final EvolutionAPI plugin;
  private final YamlConfig config;
  private boolean enabled;
  
  public HomeModule(EvolutionAPI plugin)
  {
    this.plugin = plugin;
    this.config = new YamlConfig("homes");
  }
  
  @Override
  public String getName()
  {
    return "homes";
  }
  
  @Override
  public void enable()
  {
    if(enabled) return;
    
    if(!plugin.getConfig().getBoolean("modules.home"))
    {
      plugin.getLogger().info("Módulo Homes está desativado, ignorando registro.");
      return;
    }
    
    enabled = true;
    config.reload();
    config.saveDefault(false);
    
    plugin.getLogger().info("Módulo Homes habilitado.");
  }
  
  @Override
  public void disable()
  {
    if(!enabled) return;
    
    enabled = false;
    config.save();
    
    plugin.getLogger().info("Módulo Homes desabilitado.");
  }
  
  @Override
  public boolean isEnabled()
  {
    return enabled;
  }
  
  public Home getHome(String playerName, String homeName)
  {
    if(!hasHome(playerName, homeName))
    {
      return null;
    }
    
    return new Home(config.getLocation(playerName + "." + homeName), homeName);
  }
  
  public HashMap<String, Home> getHomes(String playerName)
  {
    HashMap<String, Home> homes = new HashMap<>();
    
    if(config.isConfigurationSection(playerName))
    {
      for(String key : config.getConfigurationSection(playerName).getKeys(false))
      {
        if(hasHome(playerName, key))
        {
          homes.put(key, getHome(playerName, key));
        }
      }
    }
    
    return homes;
  }
  
  public void setHome(String playerName, Home home)
  {
    config.set(playerName + "." + home.getName(), home.getLocation());
  }
  
  public void deleteHome(String playerName, String homeName)
  {
    config.set(playerName + "." + homeName, null);
  }
  
  public void deleteHomes(String playerName)
  {
    config.set(playerName, null);
  }
  
  public int homesCount(String playerName)
  {
    int count = 0;
    
    if(config.isConfigurationSection(playerName))
    {
      for(String key : config.getConfigurationSection(playerName).getKeys(false))
      {
        if(hasHome(playerName, key))
        {
          count++;
        }
      }
    }
    
    return count;
  }
  
  public boolean hasHome(String playerName, String homeName)
  {
    return config.isLocation(playerName + "." + homeName);
  }
  
  public void saveHomes()
  {
    config.save();
  }
  
  public void reloadHomes()
  {
    config.reload();
  }
}
