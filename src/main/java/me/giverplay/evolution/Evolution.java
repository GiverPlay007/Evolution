package me.giverplay.evolution;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public final class Evolution extends JavaPlugin
{
  private static Evolution evolution;
  private static EvolutionAPI api;
  
  private String version;
  
  private boolean death = false;
  
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
  
    getLogger().info("Habilitando.");
    
    if(!api.shouldLoad())
    {
      getLogger().severe("O plugin não pôde ser iniciado com sucesso.");
      death = true;
      setEnabled(false);
      
      return;
    }
    
    try
    {
      api.load();
    }
    catch(Throwable t)
    {
      getLogger().log(Level.SEVERE, String.format("Exceção não tratada enquanto habilitava Evolution v%s.", version), t);
      this.setEnabled(false);
      return;
    }
 
    getLogger().info("Plugin habilitado com sucesso");
  }
  
  @Override
  public void onDisable()
  {
    getLogger().info("Desabilitando.");
    
    if(death)
    {
      getLogger().info("Desabilitado sem descarregar a API (death disable).");
      return;
    }
    
    try
    {
      api.unload();
    }
    catch(Throwable t)
    {
      getLogger().log(Level.SEVERE, String.format("Exceção não trata enquanto desabilitava Evolution v%s.", version), t);
      return;
    }
    
    getLogger().info("Plugin desabilitado com sucesso.");
  }
}
