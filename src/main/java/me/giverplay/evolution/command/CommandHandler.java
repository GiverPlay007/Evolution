package me.giverplay.evolution.command;

import me.giverplay.evolution.Evolution;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler implements CommandExecutor, TabCompleter {

  private final Map<String, EvolutionCommandExecutor> commands = new HashMap<>();
  private final Evolution evolution;

  public CommandHandler(Evolution evolution) {
    this.evolution = evolution;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    EvolutionCommandExecutor executor = commands.get(command.getName());

    if(!executor.isEnabled) {
      sender.sendMessage(ChatColor.RED + "Esse comando está desabilitado!");
      return true;
    }

    if(!sender.hasPermission(executor.getPermission())) {
      sender.sendMessage(ChatColor.RED + "Você não tem permissão para executar esse comando!");
      return true;
    }

    if(!executor.isPlayerAllowed && (sender instanceof Player)) {
      sender.sendMessage(ChatColor.RED + "Esse comando só pode ser executado no console!");
      return true;
    }

    if(!executor.isConsoleAllowed && !(sender instanceof Player)) {
      sender.sendMessage(ChatColor.RED + "Esse comando só pode ser executado por jogadores!");
      return true;
    }

    return executor.onCommand(sender, command, label, args);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    EvolutionCommandExecutor executor = commands.get(command.getName());

    if(!executor.isEnabled
      || !sender.hasPermission(executor.getPermission())
      || !executor.isPlayerAllowed && sender instanceof Player
      || !executor.isConsoleAllowed && !(sender instanceof Player)) {
      return new ArrayList<>();
    }
 
    return executor.onTabComplete(sender, command, alias, args);
  }

  public void registerCommand(EvolutionCommandExecutor executor) {
    if(commands.containsKey(executor.getName())) return;

    PluginCommand command = evolution.getCommand(executor.getName());

    if(command == null) {
      evolution.getLogger().warning("Command " + executor.getName() + " is not set in the plugin.yml");
      return;
    }

    command.setExecutor(this);
    command.setTabCompleter(this);

    commands.put(executor.getName(), executor);
  }

  public void unregisterCommand(String commandName) {
    EvolutionCommandExecutor executor = commands.get(commandName);

    if(executor != null) {
      unregisterCommand(executor);
    }
  }

  public void unregisterCommand(EvolutionCommandExecutor executor) {
    unregisterCommand(executor, true);
  }

  public void unregisterCommand(EvolutionCommandExecutor executor, boolean remove) {
    PluginCommand command = evolution.getCommand(executor.getName());

    if(command != null) {
      command.setExecutor(evolution);
      command.setTabCompleter(evolution);
    }

    if(remove) {
      commands.remove(executor);
    }
  }

  public void unregisterAll() {
    commands.values().forEach(command -> unregisterCommand(command, false));
    commands.clear();
  }
}
