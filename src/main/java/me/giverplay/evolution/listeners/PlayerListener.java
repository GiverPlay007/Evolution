package me.giverplay.evolution.listeners;

import me.giverplay.evolution.Evolution;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

  private final Evolution evolution;

  public PlayerListener(Evolution evolution) {
    this.evolution = evolution;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerJoin(PlayerJoinEvent event) {
    evolution.playerJoin(event.getPlayer());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuit(PlayerQuitEvent event) {
    evolution.playerQuit(event.getPlayer());
  }
}
