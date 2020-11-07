package me.giverplay.evolution.command;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;

public abstract class EvolutionCommand implements TabCompleter
{
  private final String name;
  private final boolean allowConsole;
  
  private boolean enabled = true;
  
  public EvolutionCommand(String name, boolean allowConsole)
  {
    Validate.notNull(name, "O nome do comando não pode ser nulo!");
    
    this.name = name;
    this.allowConsole = allowConsole;
  }
  
  public abstract void execute(CommandSource sender, String[] args);
  
  public boolean isAllowConsole()
  {
    return this.allowConsole;
  }
  
  public final String getName()
  {
    return name;
  }
  
  public final String getBasePermission()
  {
    return "evolution.command." + name;
  }
  
  public boolean isEnabled()
  {
    return enabled;
  }
  
  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }
  
  public void sendUsage(CommandSource source, String usage, String example)
  {
    Validate.notNull(source, "O CommandSource não pode ser nulo...");
    Validate.notNull(usage, "A instrução não pode ser nulo...");
    
    source.sendMessage("&cUso correto do comando: " + usage + ".");
    
    if(example != null)
    {
      source.sendMessage("&cExemplo: " + example + ".");
    }
  }
  
  public void sendUsage(CommandSource source, String usage)
  {
    sendUsage(source, usage, null);
  }
  
  @Override
  public final String toString()
  {
    return "EvolutionCommand: " + name;
  }
}
