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
  
    getLogger().info(String.format("Habilitando Evolution v%s.", version));
    
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
 
    getLogger().info(String.format("Evolution v%s foi desabilitado com sucesso!", version));
  }
  
  @Override
  public void onDisable()
  {
    getLogger().info(String.format("Desabilitando Evolution v%s.", version));
    
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
    
    getLogger().info(String.format("Evolution v%s foi desabilitado com sucesso!", version));
  }
}
