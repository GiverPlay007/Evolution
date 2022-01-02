package me.giverplay.evolution.listeners;

import me.giverplay.evolution.module.modules.rank.RankManager;
import me.giverplay.evolution.module.modules.rank.RankModule;
import me.giverplay.evolution.player.PlayerData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RankModuleListener implements Listener {

  private final RankModule rank;

  public RankModuleListener(RankModule rank) {
    this.rank = rank;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    PlayerData data = rank.getEvolution().getPlayerManager().getPlayerData(player);
    ConfigurationSection node = data.getNode(rank);

    RankManager manager = rank.getRankManager();

    if(node == null) {
      node = data.createNode(rank);
      node.set("Rank", manager.getFirstRank().getName());
      node.set("LastRankup", System.currentTimeMillis());
      data.save();
    }
  }
}
