package me.giverplay.evolution;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public final class Evolution extends JavaPlugin
{
  private static Evolution evolution;
  private static EvolutionAPI api;
  
  private String version;
  
  public static Evolution getInstance()
  {
    return evolution;
  }
  
  public static EvolutionAPI getEvolutionAPI()
  {
    return api;
  }
  
  @Override
  public void onEnable()
  {
    version = getDescription().getVersion();
    evolution = this;
    api = new EvolutionAPI(this);
  
    getLogger().info(String.format("Enabling Evolution v%s.", version));
    
    try
    {
      api.load();
    }
    catch(Throwable t)
    {
      getLogger().log(Level.SEVERE, String.format("Unhandled exception while enabling Evolution v%s.", version), t);
      this.setEnabled(false);
      return;
    }
 
    getLogger().info(String.format("Evolution v%s was successfully enabled!", version));
  }
  
  @Override
  public void onDisable()
  {
    getLogger().info(String.format("Disabling Evolution v%s.", version));
    
    try
    {
      api.unload();
    }
    catch(Throwable t)
    {
      getLogger().log(Level.SEVERE, String.format("Unhandled exception while disabling Evolution v%s.", version), t);
      return;
    }
    
    getLogger().info(String.format("Evolution v%s was successfully disabled!", version));
  }
}
