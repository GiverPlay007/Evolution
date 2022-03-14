package me.giverplay.evolution.module.modules.teleport;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.command.commands.TpaCommand;
import me.giverplay.evolution.module.EvolutionModule;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TeleportModule extends EvolutionModule {

  private final Map<Player, Player> requests = new HashMap<>();
  private final Map<Player, Long> cooldowns = new HashMap<>();

  public TeleportModule(Evolution evolution) {
    super(evolution, "Teleport");
  }

  @Override
  protected void onEnable() {
    evolution.getCommandHandler().registerCommand(new TpaCommand(this));
  }

  @Override
  protected void onDisable() {
    evolution.getCommandHandler().unregisterCommand("tpa");
    requests.clear();
  }

  public Player getRequest(Player player) {
    return requests.get(player);
  }

  public void setInvitation(Player player, Player other) {
    if(other == null) {
      requests.remove(player);
      return;
    }

    requests.put(player, other);
  }

  public int getCooldown(Player player) {
    if(!cooldowns.containsKey(player)) return 0;

    long cooldown = cooldowns.get(player) - System.currentTimeMillis();

    if(cooldown <= 0) {
      cooldowns.remove(player);
    }

    return (int) cooldown / 1000;
  }

  public void setCooldown(Player player, int delay) {
    cooldowns.put(player, System.currentTimeMillis() + delay * 1000L);
  }
}
