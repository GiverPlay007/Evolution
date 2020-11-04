package me.giverplay.evolution.teleport;

import java.util.HashMap;
import me.giverplay.evolution.data.YamlConfig;

public class HomeManager
{
  private final YamlConfig config;
  
  public HomeManager()
  {
    this.config = new YamlConfig("homes");
    
    config.saveDefault(false);
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
