package me.giverplay.evolution.command.commands;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.command.EvolutionCommandExecutor;
import me.giverplay.evolution.utils.ColorUtils;
import me.giverplay.evolution.utils.ProgressBar;
import me.giverplay.evolution.utils.TimeUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RestartCommand extends EvolutionCommandExecutor {

  private final Evolution evolution;

  private String reason = "Sem motivo.";

  private boolean isRestarting;
  private int taskId;
  private int time;
  private int remainingTime;

  public RestartCommand(Evolution evolution) {
    super("restart");
    this.evolution = evolution;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(args.length == 0) {
      if(isRestarting) {
        reason = "Sem motivo.";
        isRestarting = false;
        remainingTime = 0;
        time = 0;
        Bukkit.getScheduler().cancelTask(taskId);
        taskId = -1;
        sender.sendMessage(ChatColor.RED + "O servidor não vai mais reiniciar.");
        return true;
      }

      sender.sendMessage(ChatColor.RED + "Utilize /restart <segundos | now>");
      return true;
    }

    if(isRestarting) {
      sender.sendMessage(ChatColor.RED + "O servidor já está reiniciando!");
      return true;
    }

    if(args[0].equalsIgnoreCase("now")) {
      sender.sendMessage("Reiniciando servidor!");
      Bukkit.getScheduler().scheduleSyncDelayedTask(evolution, Bukkit::shutdown, 20L);
      return true;
    }

    int time;

    try {
      time = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      sender.sendMessage(ChatColor.RED + "O tempo deve ser um número!");
      return true;
    }

    if(time < 30) {
      sender.sendMessage(ChatColor.RED + "O tempo não pode ser menor do que 30 segundos!");
      return true;
    }

    if(time > 900) {
      sender.sendMessage(ChatColor.RED + "O tempo não pode ser maior do que 15 minutos!");
      return true;
    }

    if(args.length > 1) {
      StringBuilder reason = new StringBuilder();

      for(int i = 1; i < args.length; i++) {
        reason.append(args[i]);
      }

      this.reason = reason.toString();
    }

    for(Player player : Bukkit.getOnlinePlayers()) {
      player.sendMessage(" ");
      player.sendMessage("%sReinciando servidor em %s%s%s minutos!".formatted(ChatColor.GREEN, ChatColor.WHITE, TimeUtils.format(time), ChatColor.GREEN));

      if(reason != null) {
        player.sendMessage("%sMotivo: %s%s".formatted(ChatColor.GREEN, ChatColor.WHITE, reason));
      }

      player.sendMessage(" ");
    }

    this.time = time;
    remainingTime = time;
    isRestarting = true;
    taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(evolution, this::restartTimer, 20L, 20L);
    return true;
  }

  private void restartTimer() {
    remainingTime--;

    if(remainingTime == 0) {
      Bukkit.broadcastMessage(ChatColor.GREEN + "Reiniciando...");
      Bukkit.shutdown();
      return;
    }

    String message = "&eReiniciando em %s &7[%s&7]"
      .formatted(
        TimeUtils.format(remainingTime),
        ProgressBar.bar(time - remainingTime, time, 10, "|", "&a", "&c")
      );
    TextComponent component = new TextComponent(ColorUtils.translate(message));

    for(Player player : Bukkit.getOnlinePlayers()) {
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
    }
  }
}
