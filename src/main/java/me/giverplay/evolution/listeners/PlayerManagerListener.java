package me.giverplay.evolution.listeners;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.player.PlayerManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerManagerListener implements Listener {

  private final Evolution plugin;

  public PlayerManagerListener(Evolution evolution) {
    this.plugin = evolution;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    PlayerManager manager = plugin.getPlayerManager();
    Player player = event.getPlayer();

    if(!manager.isPlayerRegistered(player)) {
      manager.registerPlayer(player);

      YamlConfiguration playerData = manager.getPlayerData(player);
      playerData.set("Info.RegisteredAt", System.currentTimeMillis());

      plugin.getLogger().info("Registered player " + player.getName());
    }
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    PlayerManager manager = plugin.getPlayerManager();
    Player player = event.getPlayer();

    manager.savePlayerData(player);
    manager.removeFromCache(player);
  }
}