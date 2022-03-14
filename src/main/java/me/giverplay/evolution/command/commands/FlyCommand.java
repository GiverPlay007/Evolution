package me.giverplay.evolution.command.commands;

import br.net.fabiozumbi12.RedProtect.Bukkit.API.RedProtectAPI;
import br.net.fabiozumbi12.RedProtect.Bukkit.Region;
import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.command.EvolutionCommandExecutor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand extends EvolutionCommandExecutor {

  private final Evolution evolution;

  public FlyCommand(Evolution evolution) {
    super("fly");
    this.evolution = evolution;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(args.length == 0 && !(sender instanceof Player)) {
      sender.sendMessage(ChatColor.RED + "Utilize: /fly <nickname>");
      return true;
    }

    if(args.length > 0) {
      Player other = Bukkit.getPlayer(args[1]);

      if(!sender.hasPermission("evolution.fly.others")) {
        sender.sendMessage(ChatColor.RED + "Você não pode alterar o modo fly de outro jogador!");
        return true;
      }

      if(other == null) {
        sender.sendMessage(ChatColor.RED + "Jogador não encontado!");
        return true;
      }

      if(other.getAllowFlight()) {
        other.setAllowFlight(false);
        other.sendMessage(ChatColor.RED + "Modo fly desativado por " + sender.getName());
        sender.sendMessage(ChatColor.RED + "Modo fly de " + other.getName() + " desativado!");
      } else {
        other.setAllowFlight(true);
        other.sendMessage(ChatColor.GREEN + "Modo fly ativado por " + sender.getName());
        sender.sendMessage(ChatColor.GREEN + "Modo fly de " + other.getName() + " ativado!");
      }

      return true;
    }


    Player player = (Player) sender;

    if(player.getAllowFlight()) {
      player.setAllowFlight(false);
      player.sendMessage(ChatColor.RED + "Modo fly desativado!");
      return true;
    }

    if(evolution.isRedProtectHooked()) {
      RedProtectAPI rp = evolution.getRedProtect();
      Region region = rp.getRegion(player.getLocation());

      if(region == null || !region.isLeader(player)) {
        if(!player.hasPermission("evolution.fly.anywhere")) {
          player.sendMessage(ChatColor.RED + "Você não tem permissão para voar fora de suas regiões!");
          return true;
        }
      }
    }

    player.setAllowFlight(true);
    player.sendMessage(ChatColor.GREEN + "Modo fly ativado!");

    return true;
  }
}
