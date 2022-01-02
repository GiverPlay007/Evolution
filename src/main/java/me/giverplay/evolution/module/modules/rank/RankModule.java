package me.giverplay.evolution.module.modules.rank;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.command.CommandHandler;
import me.giverplay.evolution.command.commands.RankupCommand;
import me.giverplay.evolution.listeners.RankModuleListener;
import me.giverplay.evolution.module.Module;

public final class RankModule extends Module {

  private RankManager rankManager;
  private RankModuleListener listener;

  public RankModule(Evolution evolution) {
    super(evolution, "Rank");
    evolution.requireVault();
  }

  @Override
  protected void onEnable() {
    saveDefaultConfig();
    reloadConfig();

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
  }

  public RankManager getRankManager() {
    return rankManager;
  }
}
