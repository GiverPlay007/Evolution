package me.giverplay.evolution.listeners;

import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.modules.chat.ChatModule;
import me.giverplay.evolution.player.EvolutionPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatModuleListener implements Listener
{
  private final EvolutionAPI plugin;
  private final ChatModule module;
  
  public ChatModuleListener(ChatModule module, EvolutionAPI plugin)
  {
    this.module = module;
    this.plugin = plugin;
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onChat(AsyncPlayerChatEvent event)
  {
    if(event.isCancelled())
      return;
  
    EvolutionPlayer player = plugin.getPlayerManager().getPlayer(event.getPlayer());
    
    String format = module.getFormat();
    format = format.replace("$level", String.valueOf(player.getLevel()));
    format = format.replace("$prefix", player.getPrefix());
    format = format.replace("$tag", player.getTag());
    format = format.replace("$color", player.getNameColor());
    format = format.replace("$chatcolor", player.getChatColor());
    
    event.setFormat(ChatColor.translateAlternateColorCodes('&', format));
    
    if(player.hasPermission("evolution.chatcolor"))
    {
      event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
    }
  }
}

