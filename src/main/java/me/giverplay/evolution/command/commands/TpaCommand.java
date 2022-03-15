package me.giverplay.evolution.command.commands;

import me.giverplay.evolution.command.EvolutionCommandExecutor;
import me.giverplay.evolution.module.modules.teleport.TeleportModule;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    if(other == player) {
      player.sendMessage(ChatColor.RED + "Este jogador é você!");
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

    TextComponent message = new TextComponent("Clique ");
    message.setColor(ChatColor.GRAY);

    BaseComponent[] acceptHover = new ComponentBuilder("Aceitar pedido de teleporte de " + sender.getName())
      .color(ChatColor.GREEN)
      .create();

    TextComponent accept = new TextComponent("AQUI");
    accept.setColor(ChatColor.GREEN);
    accept.setBold(true);
    accept.setUnderlined(true);
    accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, acceptHover));
    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + sender.getName()));

    BaseComponent[] denyHover = new ComponentBuilder("Negar pedido de teleporte de " + sender.getName())
      .color(ChatColor.RED)
      .create();

    TextComponent deny = new TextComponent("AQUI");
    deny.setColor(ChatColor.RED);
    deny.setBold(true);
    deny.setUnderlined(true);
    deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, denyHover));
    deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny " + sender.getName()));

    TextComponent acceptOr = new TextComponent(" para aceitar ou ");
    acceptOr.setColor(ChatColor.GRAY);

    TextComponent denyOr = new TextComponent(" para negar.");
    denyOr.setColor(ChatColor.GRAY);

    message.addExtra(accept);
    message.addExtra(acceptOr);
    message.addExtra(deny);
    message.addExtra(denyOr);

    final int requestCooldown = teleport.getCooldown();

    other.sendMessage(" ");
    other.sendMessage(ChatColor.GRAY + player.getName() + " pediu para teleportar até você.");
    other.spigot().sendMessage(message);
    other.sendMessage(" ");
    player.sendMessage(ChatColor.GREEN + "Pedido de teleporte enviado para " + other.getName() + "!");
    teleport.setCooldown(player, requestCooldown);
    teleport.setRequest(player, other);

    Bukkit.getScheduler().runTaskLater(teleport.getEvolution(), () -> {
      Player requested = teleport.getRequest(player);

      if(requested == other) {
        teleport.setRequest(player, null);
        teleport.setCooldown(player, requestCooldown);
        player.sendMessage(ChatColor.RED + other.getName() + " não aceitou seu pedido de teleporte.");
        other.sendMessage(ChatColor.YELLOW + "Pedido de teleporte expirado!");
      }
    }, requestCooldown * 20L);
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    return args.length != 1 ? new ArrayList<>() : null;
  }
}
