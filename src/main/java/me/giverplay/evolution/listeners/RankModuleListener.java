package me.giverplay.evolution.listeners;

import me.giverplay.evolution.module.modules.rank.RankManager;
import me.giverplay.evolution.module.modules.rank.RankModule;
import me.giverplay.evolution.player.PlayerData;
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

    RankManager manager = rank.getRankManager();

    if(!data.isConfigurationSection("Rank")) {
      data.set("Rank.Rank", manager.getFirstRank().getName());
      data.set("Rank.LastRankup", System.currentTimeMillis());
    }
  }
}
