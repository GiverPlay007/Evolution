package me.giverplay.evolution.listeners;

import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.modules.chat.ChatModule;
import org.bukkit.event.EventHandler;
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
  
  @EventHandler
  public void onChat(AsyncPlayerChatEvent event)
  {
  
  }
}

