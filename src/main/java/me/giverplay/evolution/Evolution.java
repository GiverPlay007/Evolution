package me.giverplay.evolution;

import me.giverplay.evolution.listeners.PlayerManagerListener;
import me.giverplay.evolution.module.ModuleManager;
import me.giverplay.evolution.player.PlayerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Evolution extends JavaPlugin {

  private PlayerManager playerManager;
  private ModuleManager moduleManager;

  private Economy vaultEconomy;

  private boolean isVaultRequired;

  @Override
  public void onEnable() {
    playerManager = new PlayerManager(this);
    moduleManager = new ModuleManager(this);

    if(isVaultRequired && !hookVault()) {
      getLogger().severe("Plugin Vault found, disabling...");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    moduleManager.enableAll();

    getServer().getPluginManager().registerEvents(new PlayerManagerListener(this), this);
  }

  @Override
  public void onDisable() {
    if(moduleManager != null) {
      moduleManager.disableAll();
      moduleManager = null;
    }

    playerManager = null;
  }

  private boolean hookVault() {
    Plugin plugin = getServer().getPluginManager().getPlugin("Vault");

    if(plugin == null || !getServer().getPluginManager().isPluginEnabled(plugin)) return false;

    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);

    if(economyProvider != null) {
      vaultEconomy = economyProvider.getProvider();
    }

    return vaultEconomy != null;
  }

  public void requireVault() {
    isVaultRequired = true;
  }

  public PlayerManager getPlayerManager() {
    return playerManager;
  }

  public ModuleManager getModuleManager() {
    return moduleManager;
  }
}
