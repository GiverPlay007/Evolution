package me.giverplay.evolution.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public final class ChatUtils {
  private ChatUtils() { }

  public static void sendAction(Player player, String message) {
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
  }
}
