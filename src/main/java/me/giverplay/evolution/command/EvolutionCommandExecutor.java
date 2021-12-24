package me.giverplay.evolution.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public abstract class EvolutionCommandExecutor implements CommandExecutor, TabCompleter {

  private final String name;
  private final String permission;

  protected boolean isConsoleAllowed = true;
  protected boolean isPlayerAllowed = true;
  protected boolean isEnabled = true;

  public EvolutionCommandExecutor(String name) {
    this.name = name;
    this.permission = "evolution.command." + name;
  }

  public final String getPermission() {
    return permission;
  }

  public final String getPermission(String child) {
    return permission + "." + child;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public boolean isConsoleAllowed() {
    return isConsoleAllowed;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    return new ArrayList<>();
  }

  public String getName() {
    return name;
  }
}
