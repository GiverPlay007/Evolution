package me.giverplay.evolution.player;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.EvolutionAPI;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

public class EvolutionPlayer
{
  private final PlayerSettings settings;
  private final EvolutionAPI plugin;
  private final Essentials ess;
  private final Player player;
  private final String name;
  
  public EvolutionPlayer(Player player)
  {
    Validate.notNull(player, "Não é possível registrar um jogador nulo");
    
    this.player = player;
    this.name = player.getName();
    this.settings = new PlayerSettings();
    this.plugin = Evolution.getEvolutionAPI();
    this.ess = plugin.getEssentials();
  }
  
  public String getName()
  {
    return name;
  }
  
  public PlayerSettings getPlayerSettings()
  {
    return settings;
  }
  
  public Player getPlayer()
  {
    return player;
  }
  
  public User getEssentialsUser()
  {
    return ess.getUser(player);
  }
}
