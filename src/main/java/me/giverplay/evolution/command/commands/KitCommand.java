package me.giverplay.evolution.command.commands;

import me.giverplay.evolution.command.EvolutionCommandExecutor;
import me.giverplay.evolution.module.modules.kit.KitModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitCommand extends EvolutionCommandExecutor {

  private final KitModule kitModule;

  public KitCommand(KitModule module) {
    super("kit");

    kitModule = module;
    isConsoleAllowed = false;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    kitModule.openKitCategoriesMenu((Player) sender);
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    return new ArrayList<>();
  }
}
