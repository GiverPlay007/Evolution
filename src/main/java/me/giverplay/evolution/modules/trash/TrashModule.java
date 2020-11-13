package me.giverplay.evolution.modules.trash;

import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.modules.Module;

public class TrashModule implements Module
{
  private final EvolutionAPI plugin;
  
  private boolean enabled;
  
  public TrashModule(EvolutionAPI plugin)
  {
    this.plugin = plugin;
  }
  
  @Override
  public String getName()
  {
    return "trash";
  }
  
  @Override
  public void enable()
  {
    if(enabled)
      return;
    
    enabled = true;
  }
  
  @Override
  public void disable()
  {
    if(!enabled)
      return;
    
    enabled = false;
  }
  
  @Override
  public boolean isEnabled()
  {
    return enabled;
  }
}
