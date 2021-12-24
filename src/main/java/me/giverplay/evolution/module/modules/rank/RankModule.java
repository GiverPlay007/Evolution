package me.giverplay.evolution.module.modules.rank;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.command.CommandHandler;
import me.giverplay.evolution.command.commands.RankupCommand;
import me.giverplay.evolution.listeners.RankModuleListener;
import me.giverplay.evolution.module.Module;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class RankModule extends Module {

  private YamlConfiguration config;
  private RankManager rankManager;
  private RankModuleListener listener;

  public RankModule(Evolution evolution) {
    super(evolution, "Rank");
    evolution.requireVault();
  }

  @Override
  protected void onEnable() {
    File file = new File(evolution.getDataFolder(), "Rank.yml");
    config = new YamlConfiguration();

    if(!file.exists()) {
      evolution.saveResource("Rank.yml", false);
    }

    try {
      config.load(file);
    } catch (IOException | InvalidConfigurationException e) {
      getLogger().severe("Failed to load Rank.yml");
      e.printStackTrace();
    }

    rankManager = new RankManager(this);
    rankManager.loadRanks();

    listener = new RankModuleListener(this);
    evolution.registerEventListener(listener);

    CommandHandler commandHandler = evolution.getCommandHandler();
    commandHandler.registerCommand(new RankupCommand(rankManager));
  }

  @Override
  protected void onDisable() {
    evolution.unregisterEventListener(listener);

    CommandHandler commandHandler = evolution.getCommandHandler();
    commandHandler.unregisterCommand("rankup");

    listener = null;
    config = null;
  }

  public YamlConfiguration getConfig() {
    return config;
  }

  public RankManager getRankManager() {
    return rankManager;
  }
}
