package me.giverplay.evolution;

import me.giverplay.evolution.listeners.PlayerManagerListener;
import me.giverplay.evolution.module.ModuleManager;
import me.giverplay.evolution.player.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Evolution extends JavaPlugin {

  private PlayerManager playerManager;
  private ModuleManager moduleManager;

  @Override
  public void onEnable() {
    playerManager = new PlayerManager(this);
    moduleManager = new ModuleManager(this);
    moduleManager.enableAll();

    getServer().getPluginManager().registerEvents(new PlayerManagerListener(this), this);
  }

  @Override
  public void onDisable() {
    moduleManager.disableAll();
    moduleManager = null;
    playerManager = null;
  }

  public PlayerManager getPlayerManager() {
    return playerManager;
  }

  public ModuleManager getModuleManager() {
    return moduleManager;
  }
}
