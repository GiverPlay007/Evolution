package me.giverplay.evolution.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor
{
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    return false;
  }
}
