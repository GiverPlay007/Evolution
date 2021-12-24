package me.giverplay.evolution;

import me.giverplay.evolution.player.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Evolution extends JavaPlugin {

  private PlayerManager playerManager;

  @Override
  public void onEnable() {
    playerManager = new PlayerManager(this);
  }

  @Override
  public void onDisable() {
    playerManager = null;
  }

  public PlayerManager getPlayerManager() {
    return playerManager;
  }
}
