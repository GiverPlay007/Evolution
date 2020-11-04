package me.giverplay.evolution.teleport;

import java.util.HashMap;
import me.giverplay.evolution.EvolutionAPI;

public class HomeManager
{
  private final EvolutionAPI plugin;
  
  public HomeManager(EvolutionAPI api)
  {
    this.plugin = api;
  }
  
  public Home getHome(String playerName, String homeName)
  {
    // TODO
    return null;
  }
  
  public HashMap<String, Home> getHomes(String playerName)
  {
    // TODO
    return null;
  }
  
  public void setHome(String playerName, Home home)
  {
    // TODO
  }
  
  public void deleteHome(String playerName, String homeName)
  {
    // TODO
  }
  
  public void deleteHomes(String playerName)
  {
    // TODO
  }
  
  public int homesCount(String playerName)
  {
    // TODO
    return 0;
  }
  
  public boolean hasHome(String playerName, String homeName)
  {
    // TODO
    return false;
  }
  
  public void saveHomes()
  {
    // TODO
  }
  
  public void reloadHomes()
  {
    // TODO
  }
}
