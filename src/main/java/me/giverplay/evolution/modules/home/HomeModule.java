package me.giverplay.evolution.modules.home;

import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.command.commands.DelHomeCommand;
import me.giverplay.evolution.command.commands.HomeCommand;
import me.giverplay.evolution.command.commands.SetHomeCommand;
import me.giverplay.evolution.modules.Module;

public class HomeModule implements Module
{
  private final EvolutionAPI plugin;
  
  private SetHomeCommand setHomeCommand;
  private DelHomeCommand delHomeCommand;
  private HomeCommand homeCommand;
  
  private boolean enabled;
  
  public HomeModule(EvolutionAPI plugin)
  {
    this.plugin = plugin;
    
    setupCommands();
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
    
    toggleCommands(true);
    
    enabled = true;
    plugin.getLogger().info("Modulo Homes habilitado.");
  }
  
  @Override
  public void disable()
  {
    if(!enabled) return;
    
    toggleCommands(false);
    
    enabled = false;
    plugin.getLogger().info("Modulo Homes desabilitado.");
  }
  
  private void toggleCommands(boolean status)
  {
    homeCommand.setEnabled(status);
    setHomeCommand.setEnabled(status);
    delHomeCommand.setEnabled(status);
  }
  
  private void setupCommands()
  {
    setHomeCommand = new SetHomeCommand(plugin);
    delHomeCommand = new DelHomeCommand(plugin);
    homeCommand = new HomeCommand(plugin);
    
  
    plugin.getCommandManager().registerCommand(setHomeCommand);
    plugin.getCommandManager().registerCommand(delHomeCommand);
    plugin.getCommandManager().registerCommand(homeCommand);
  }
  
  @Override
  public boolean isEnabled()
  {
    return enabled;
  }
}
