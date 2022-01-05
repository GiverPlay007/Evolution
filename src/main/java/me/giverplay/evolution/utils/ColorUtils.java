package me.giverplay.evolution.utils;

import org.bukkit.ChatColor;

public final class ColorUtils {
  private ColorUtils() { }

  public static String translate(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }
}
