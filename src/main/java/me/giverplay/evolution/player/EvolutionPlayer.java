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
  
  public boolean hasPermission(String permission)
  {
    return player.hasPermission(permission);
  }
  
  public String getNameColor()
  {
    return ""; // todo
  }
  
  public String getChatColor()
  {
    return ""; // TODO
  }
  
  public String getTag()
  {
    return ""; // TODO
  }
  
  public String getPrefix()
  {
    return ""; // TODO
  }
  
  public int getLevel()
  {
    return 0; // TODO
  }
  
  public void setLevel(int level)
  {
    // TODO
  }
  
  public int getXp()
  {
    return 0; // TODO
  }
  
  public void setXp(int xp)
  {
    // TODO
  }
}
