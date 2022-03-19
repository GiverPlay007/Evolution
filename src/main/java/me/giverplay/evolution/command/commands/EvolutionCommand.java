package me.giverplay.evolution.command.commands;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.command.EvolutionCommandExecutor;
import org.bukkit.ChatColor;
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

    if(!checkPermission(sender, args[0])) {
      sender.sendMessage(ChatColor.RED + "Você não tem permissão para prosseguir com este comando!");
      return true;
    }

    if(args[0].equalsIgnoreCase("reload")) {
      evolution.reload();
      sender.sendMessage(ChatColor.GREEN + "Evolution recarregado com sucesso!");
      return true;
    }

    return true;
  }
}
