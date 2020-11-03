package me.giverplay.evolution.command.commands;

import java.util.List;
import me.giverplay.evolution.command.CommandSource;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EvolutionCommand extends me.giverplay.evolution.command.EvolutionCommand
{
  public EvolutionCommand()
  {
    super("evolution", true);
  }
  
  @Override
  public void execute(CommandSource sender, String[] args)
  {
    sender.sendMessage("&aVou colocar uma frase melhor aqui depois.");
  }
  
  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
  {
    return null;
  }
}
