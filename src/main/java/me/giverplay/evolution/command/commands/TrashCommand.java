package me.giverplay.evolution.command.commands;

import me.giverplay.evolution.command.EvolutionCommandExecutor;
import me.giverplay.evolution.module.modules.trash.TrashModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrashCommand extends EvolutionCommandExecutor {

  private final TrashModule trashModule;

  public TrashCommand(TrashModule trashModule) {
    super("trash");
    this.trashModule = trashModule;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player;

    if(!(sender instanceof Player)) {
      if(args.length == 0) {
        sender.sendMessage(ChatColor.RED + "Especifique o jogador.");
        return true;
      }
    }

    if(args.length > 0) {
      player = Bukkit.getPlayer(args[0]);

      if(player == null) {
        sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
        return true;
      }
    } else {
      player = (Player) sender;
    }

    player.openInventory(trashModule.getTrash());
    player.sendMessage(ChatColor.YELLOW + "Abrindo lixeira!");

    if(player != sender) {
      sender.sendMessage(ChatColor.YELLOW + "Abrindo lixeira para " + player.getName());
    }

    return true;
  }
}
