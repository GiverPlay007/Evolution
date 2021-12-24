package me.giverplay.evolution.module.modules.rank;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class RankManager {

  private final Map<String, Rank> ranks = new HashMap<>();
  private final RankModule rank;

  private Rank lastRank;
  private Rank firstRank;

  public RankManager(RankModule rank) {
    this.rank = rank;
  }

  public void loadRanks() {
    ConfigurationSection ranks = rank.getConfig().getConfigurationSection("Ranks");

    for (String key : ranks.getKeys(false)) {
      ConfigurationSection rankData = ranks.getConfigurationSection(key);

      String next = rankData.getString("Next");
      boolean isLast = rankData.getBoolean("isLast");
      boolean isFist = rankData.getBoolean("isFirst");
      double cost = rankData.getDouble("Cost");

      Rank rank = new Rank(key, cost, isFist, isLast, next);
      this.ranks.put(key, rank);

      if(isLast) {
        lastRank = rank;
      }

      if(isFist) {
        firstRank = rank;
      }
    }

    rank.getLogger().info("Loaded " + this.ranks.size() + " ranks!");
  }

  public Rank getLastRank() {
    return lastRank;
  }

  public Rank getFirstRank() {
    return firstRank;
  }
}
