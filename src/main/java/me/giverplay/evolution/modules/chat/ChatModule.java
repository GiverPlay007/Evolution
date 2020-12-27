package me.giverplay.evolution.modules.chat;

import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.listeners.ChatModuleListener;
import me.giverplay.evolution.modules.Module;
import org.bukkit.event.HandlerList;

public class ChatModule implements Module
{
  private final EvolutionAPI plugin;
  
  private ChatModuleListener listener;
  
  private boolean enabled;
  
  public ChatModule(EvolutionAPI plugin)
  {
    this.plugin = plugin;
  }
  
  @Override
  public String getName()
  {
    return "chat";
  }
  
  @Override
  public void enable()
  {
    if(enabled) return;

    registerEvents();
    
    enabled = true;
    plugin.getLogger().info("Modulo Chat habilitado.");
  }
  
  @Override
  public void disable()
  {
    if(!enabled) return;
    
    unregisterEvents();
    
    enabled = false;
    plugin.getLogger().info("Modulo Chat desabilitado.");
  }
  
  @Override
  public boolean isEnabled()
  {
    return enabled;
  }
  
  public String getFormat()
  {
    return "&e[$level]&r $prefix&r $tag $color%1$s$chatcolor: %2$s";
  }

  @Override
  public void registerEvents()
  {
    if(listener == null)
    {
      listener = new ChatModuleListener(this, plugin);
    }

    plugin.registerListener(listener);
  }

  @Override
  public void unregisterEvents()
  {
    if(listener != null)
    {
      HandlerList.unregisterAll(listener);
    }
  }
}
