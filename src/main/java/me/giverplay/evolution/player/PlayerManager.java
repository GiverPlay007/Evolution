package me.giverplay.evolution.player;

import java.util.HashMap;
import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.listeners.PlayerManagerListener;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerManager
{
  private final HashMap<String, EvolutionPlayer> players = new HashMap<>();
  private final EvolutionAPI plugin;
  
  public PlayerManager(EvolutionAPI api)
  {
    this.plugin = api;
    api.registerListener(new PlayerManagerListener(this));
  }
  
  public EvolutionPlayer getPlayer(String name)
  {
    synchronized(players)
    {
      return players.get(name);
    }
  }
  
  public EvolutionPlayer getPlayer(Player player)
  {
    Validate.notNull(player, "O jogador não pode ser nulo!");
    
    return getPlayer(player.getName());
  }
  
  public void registerPlayer(Player player)
  {
    Validate.notNull(player, "Não é possível registar um jogador nulo.");
    
    synchronized(players)
    {
      if(players.containsKey(player.getName()))
      {
        throw new IllegalStateException("Jogador " + player.getName() + " já está registrado");
      }
      
      plugin.getLogger().info("Registrando " + player.getName());
      players.put(player.getName(), new EvolutionPlayer(player));
    }
  }
  
  public void unregisterPlayer(Player player)
  {
    if(player == null)
    {
      return;
    }
    
    synchronized(players)
    {
      EvolutionPlayer ep = players.remove(player.getName());
      
      if(ep != null)
      {
        plugin.getLogger().info("Jogador " + ep + " foi desregistrado.");
      }
    }
  }
}
