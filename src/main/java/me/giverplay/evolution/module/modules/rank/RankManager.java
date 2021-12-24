package me.giverplay.evolution.module.modules.rank;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.player.PlayerData;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.swing.plaf.SplitPaneUI;
import java.util.HashMap;
import java.util.Map;

public class RankManager {

  private final Map<String, Rank> ranks = new HashMap<>();
  private final RankModule rankModule;

  private Rank lastRank;
  private Rank firstRank;

  public RankManager(RankModule rank) {
    this.rankModule = rank;
  }

  public void loadRanks() {
    ConfigurationSection ranks = rankModule.getConfig().getConfigurationSection("Ranks");

    for (String key : ranks.getKeys(false)) {
      ConfigurationSection rankData = ranks.getConfigurationSection(key);

      String next = rankData.getString("Next");
      boolean isFirst = rankData.getBoolean("FirstRank");
      boolean isLast = rankData.getBoolean("LastRank");
      double cost = rankData.getDouble("Cost");

      Rank rank = new Rank(key, cost, isFirst, isLast, next);
      this.ranks.put(key, rank);

      if(isLast) {
        lastRank = rank;
      }

      if(isFirst) {
        firstRank = rank;
      }
    }

    rankModule.getLogger().info("Loaded " + this.ranks.size() + " ranks!");
  }

  public Rank getPlayerRank(Player player) {
    PlayerData data = rankModule.getEvolution().getPlayerManager().getPlayerData(player);
    return getRank(data.getString("Rank.Rank"));
  }

  public Rank getRank(String rank) {
    return ranks.get(rank);
  }

  public Rank getLastRank() {
    return lastRank;
  }

  public Rank getFirstRank() {
    return firstRank;
  }

  public void rankup(Player player) {
    Rank rank = getPlayerRank(player);

    if(rank.isLast()) {
      player.sendMessage(ChatColor.RED + "Você já está no último rank!");
      return;
    }

    Evolution evolution = rankModule.getEvolution();
    Economy economy = evolution.getVaultEconomy();
    Rank nextRank = getRank(rank.getNextRank());
    double cost = nextRank.getCost();

    if(!economy.has(player, cost)) {
      player.sendMessage("%sVocê precisa de mais %s%s%s para upar para o rank %s%s!"
        .formatted(ChatColor.RED, ChatColor.WHITE, cost - economy.getBalance(player), ChatColor.RED, ChatColor.WHITE, nextRank.getName()));
      return;
    }

    PlayerData playerData = evolution.getPlayerManager().getPlayerData(player);
    playerData.set("Rank.Rank", nextRank.getName());
    playerData.set("Rank.LastRankup", System.currentTimeMillis());
    playerData.save();
    economy.withdrawPlayer(player, nextRank.getCost());

    ConsoleCommandSender sender = Bukkit.getConsoleSender();
    Bukkit.dispatchCommand(sender, "lp user " + player.getName() + " parent remove " + rank.getName());
    Bukkit.dispatchCommand(sender, "lp user " + player.getName() + " parent add " + nextRank.getName());

    evolution.getLogger().info("Player " + player.getName() + " rank upped to " + nextRank.getName());
    player.sendMessage(ChatColor.GREEN + "Parabéns! Você upou para o rank " + nextRank.getName() + "!");
  }
}
