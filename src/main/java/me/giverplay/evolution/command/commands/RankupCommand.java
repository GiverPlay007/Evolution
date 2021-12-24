package me.giverplay.evolution.command.commands;

import me.giverplay.evolution.command.EvolutionCommandExecutor;
import me.giverplay.evolution.module.modules.rank.Rank;
import me.giverplay.evolution.module.modules.rank.RankManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankupCommand extends EvolutionCommandExecutor {

  private final RankManager rankManager;

  public RankupCommand(RankManager rankManager) {
    super("rankup");

    this.rankManager = rankManager;
    isConsoleAllowed = false;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player = (Player) sender;
    rankManager.rankup(player);

    return true;
  }
}
