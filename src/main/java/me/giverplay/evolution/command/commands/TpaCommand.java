package me.giverplay.evolution.command.commands;

import me.giverplay.evolution.command.EvolutionCommandExecutor;
import me.giverplay.evolution.module.modules.teleport.TeleportModule;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand extends EvolutionCommandExecutor {

  private final TeleportModule teleport;

  public TpaCommand(TeleportModule teleport) {
    super("tpa");

    this.teleport = teleport;
    isConsoleAllowed = false;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player = (Player) sender;

    if(args.length == 0) {
      player.sendMessage(ChatColor.RED + "Utilize /tpa <jogador>");
      return true;
    }

    Player other = Bukkit.getPlayer(args[0]);

    if(other == null) {
      player.sendMessage(ChatColor.RED + "Este jogador não existe ou está offline!");
      return true;
    }

    Player request = teleport.getRequest(player);

    if(request != null) {
      if(request == other) {
        player.sendMessage(ChatColor.RED + "Você já solicitou teleporte para este jogador!");
        return true;
      }

      player.sendMessage(ChatColor.RED + "Você já solicitou teleporte para outro jogador... Aguarde!");
      return true;
    }

    int cooldown = teleport.getCooldown(player);

    if(cooldown > 0) {
      player.sendMessage(ChatColor.RED + "Aguarde " + cooldown + " segundos para solicitar outro teleporte!");
      return true;
    }

    BaseComponent[] message = new ComponentBuilder("Clique ")
      .color(ChatColor.GOLD)
      .append(new ComponentBuilder("AQUI")
        .color(ChatColor.RED)
        .bold(true)
        .underlined(true)
        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Aceitar pedido de teleporte de " + player.getName())
          .color(ChatColor.GREEN)
          .create()))
        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaceitar " + player.getName()))
        .create())
      .append(new ComponentBuilder(" para aceitar ou ")
        .color(ChatColor.GOLD)
        .create())
      .append(new ComponentBuilder("AQUI")
        .color(ChatColor.RED)
        .bold(true)
        .underlined(true)
        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Negar pedido de teleporte de " + player.getName())
          .color(ChatColor.RED)
          .create()))
        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpanegar " + player.getName()))
        .create())
      .append(new ComponentBuilder(" para negar ")
        .color(ChatColor.GOLD)
        .create())
      .create();

    other.sendMessage(ChatColor.YELLOW + player.getName() + "Pediu para teleportar até você.");
    other.spigot().sendMessage(message);
    player.sendMessage(ChatColor.GREEN + "Pedido de teleporte enviado para " + other.getName() + "!");
    teleport.setCooldown(player, 20);

    Bukkit.getScheduler().runTaskLater(teleport.getEvolution(), () -> {
      Player requested = teleport.getRequest(player);

      if(requested == other) {
        teleport.setInvitation(player, null);
        player.sendMessage(ChatColor.RED + other.getName() + "Não aceitou seu pedido de teleporte.");
        other.sendMessage(ChatColor.YELLOW + "Pedido de teleporte expirado!");
      }
    }, 20 * 20L);
    return true;
  }
}