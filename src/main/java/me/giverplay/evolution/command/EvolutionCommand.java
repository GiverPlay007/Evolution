package me.giverplay.evolution.command;

import org.bukkit.command.CommandSender;

public abstract class EvolutionCommand
{
  private final String name;
  
  public EvolutionCommand(String name, boolean allowConsole)
  {
    this.name = name;
    
  }
  
  void process(CommandSender sender, String[] args)
  {
  
  }
}
