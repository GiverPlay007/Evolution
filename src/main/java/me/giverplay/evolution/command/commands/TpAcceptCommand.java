package me.giverplay.evolution.command.commands;

import me.giverplay.evolution.command.EvolutionCommandExecutor;
import me.giverplay.evolution.module.modules.teleport.TeleportModule;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpAcceptCommand extends EvolutionCommandExecutor {

  private final TeleportModule teleport;

  public TpAcceptCommand(TeleportModule teleport) {
    super("tpaccept");
    this.teleport = teleport;
    this.isConsoleAllowed = false;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player = (Player) sender;

    if(args.length == 0) {
      player.sendMessage(ChatColor.RED + "Utilize /tpaccept <nickname>!");
      return true;
    }

    Player whoRequested = Bukkit.getPlayer(args[0]);

    if(whoRequested == null) {
      player.sendMessage(ChatColor.RED + "Este jogador não existe ou está offline.");
      return true;
    }

    if(player != teleport.getRequest(whoRequested)) {
      player.sendMessage(ChatColor.RED + "Este jogador não pediu para se teleportar até você!");
      return true;
    }

    whoRequested.sendMessage(ChatColor.YELLOW + "Teleportando até " + player.getName() + "...");
    player.sendMessage(ChatColor.YELLOW + "Teleportando " + whoRequested.getName() + " até você...");
    whoRequested.teleport(player);
    teleport.setRequest(whoRequested, null);
    teleport.setCooldown(whoRequested, teleport.getCooldown());
    return true;
  }
}
