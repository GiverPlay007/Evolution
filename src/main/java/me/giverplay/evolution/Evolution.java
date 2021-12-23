package me.giverplay.evolution;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public final class Evolution extends JavaPlugin {

  private static Evolution evolution;
  private static EvolutionAPI api;

  private boolean death = false;

  public static Evolution getInstance() {
    return evolution;
  }

  public static EvolutionAPI getEvolutionAPI() {
    return api;
  }

  @Override
  public void onEnable() {
    evolution = this;
    api = new EvolutionAPI(this);

    if (!api.serverHasDependencies()) {
      getLogger().severe("Plugin could not be loaded");
      death = true;
      setEnabled(false);

      return;
    }

    try {
      api.load();
    } catch (Throwable t) {
      getLogger().log(Level.SEVERE, "Unhandled exception while loading " + getDescription().getFullName(), t);
      this.setEnabled(false);
      return;
    }

    getLogger().fine("Enabled successfully :ok_hand:");
  }

  @Override
  public void onDisable() {
    if (death) {
      getLogger().info("Disabling without API unloading (death disable)");
      return;
    }

    try {
      api.unload();
    } catch (Throwable t) {
      getLogger().log(Level.SEVERE, "Unhandled exception while unloading " + getDescription().getFullName(), t);
      return;
    }

    api = null;
    getLogger().fine("Disabled perfectly :ok_hand:");
  }
}
