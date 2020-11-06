package me.giverplay.evolution.listeners;

import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerManagerListener implements Listener
{
  private final PlayerManager manager;
  
  public PlayerManagerListener(PlayerManager manager)
  {
    this.manager = manager;
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent event)
  {
    manager.registerPlayer(event.getPlayer());
  }
  
  @EventHandler
  public void onLeave(PlayerQuitEvent event)
  {
    manager.unregisterPlayer(event.getPlayer());
  }
}
