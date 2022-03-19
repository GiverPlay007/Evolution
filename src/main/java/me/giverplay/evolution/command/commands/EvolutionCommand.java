package me.giverplay.evolution.command.commands;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.command.EvolutionCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EvolutionCommand extends EvolutionCommandExecutor {

  private final Evolution evolution;

  public EvolutionCommand(Evolution evolution) {
    super("evolution");
    this.evolution = evolution;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(args.length == 0) {
      evolution.sendInformation(sender);
      return true;
    }

    return true;
  }
}
