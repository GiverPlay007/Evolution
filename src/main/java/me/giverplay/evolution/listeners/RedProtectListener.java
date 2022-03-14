package me.giverplay.evolution.listeners;

import br.net.fabiozumbi12.RedProtect.Bukkit.API.events.EnterExitRegionEvent;
import br.net.fabiozumbi12.RedProtect.Bukkit.Region;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RedProtectListener implements Listener {

  @EventHandler
  public void onRegionEnterExit(EnterExitRegionEvent event) {
    Player player = event.getPlayer();
    Region entered = event.getEnteredRegion();

    if(entered == null || !entered.isLeader(player)) {
      if(player.getAllowFlight() && !player.hasPermission("evolution.fly.anywhere")) {
        player.setAllowFlight(false);
        player.sendMessage(ChatColor.RED + "Fly desativado!");
      }
    }
  }
}
