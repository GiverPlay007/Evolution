package me.giverplay.evolution;

import me.giverplay.evolution.command.CommandHandler;
import me.giverplay.evolution.listeners.PlayerListener;
import me.giverplay.evolution.module.ModuleManager;
import me.giverplay.evolution.module.modules.rank.RankModule;
import me.giverplay.evolution.player.PlayerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Evolution extends JavaPlugin {

  private CommandHandler commandHandler;
  private PlayerManager playerManager;
  private ModuleManager moduleManager;

  private Economy vaultEconomy;

  private boolean isVaultRequired;
  private boolean isVaultHooked;

  @Override
  public void onEnable() {
    saveDefaultConfig();
    reloadConfig();

    playerManager = new PlayerManager(this);
    moduleManager = new ModuleManager(this);
    moduleManager.registerModule(new RankModule(this));

    if(isVaultRequired && !hookVault()) {
      getLogger().severe("Plugin Vault found, disabling...");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    commandHandler = new CommandHandler(this);
    moduleManager.enableAll();

    getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    getServer().getOnlinePlayers().forEach(this::playerJoin);
  }

  @Override
  public void onDisable() {
    getServer().getOnlinePlayers().forEach(this::playerQuit);

    if(moduleManager != null) {
      moduleManager.disableAll();
      moduleManager = null;
    }

    if(commandHandler != null) {
      commandHandler.unregisterAll();
      commandHandler = null;
    }

    playerManager = null;
  }

  public void registerEventListener(Listener listener) {
    getServer().getPluginManager().registerEvents(listener, this);
  }

  public void unregisterEventListener(Listener listener) {
    HandlerList.unregisterAll(listener);
  }

  private boolean hookVault() {
    Plugin plugin = getServer().getPluginManager().getPlugin("Vault");

    if(plugin == null || !getServer().getPluginManager().isPluginEnabled(plugin)) return false;

    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);

    if(economyProvider != null) {
      vaultEconomy = economyProvider.getProvider();
    }

    isVaultHooked = vaultEconomy != null;
    return isVaultHooked;
  }

  public void requireVault() {
    if(isVaultHooked) {
      throw new IllegalStateException("Vault is already hooked... Call this on module constructor!");
    }

    isVaultRequired = true;
  }

  public void playerJoin(Player player) {
    if(!playerManager.isPlayerRegistered(player)) {
      playerManager.registerPlayer(player);
      getLogger().info("Registered player " + player.getName());
      return;
    }

    playerManager.getPlayerData(player);
  }

  public void playerQuit(Player player) {
    playerManager.savePlayerData(player);
    playerManager.removeFromCache(player);
  }

  public boolean isVaultHooked() {
    return isVaultHooked;
  }

  public PlayerManager getPlayerManager() {
    return playerManager;
  }

  public ModuleManager getModuleManager() {
    return moduleManager;
  }

  public CommandHandler getCommandHandler() {
    return commandHandler;
  }

  public Economy getVaultEconomy() {
    return vaultEconomy;
  }
}
