package me.giverplay.evolution.command;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Based on Essentials and EssentialsX
public class CommandSource
{
  private final CommandSender sender;
  
  public CommandSource(CommandSender sender)
  {
    Validate.notNull(sender, "Sender cannot be null!");
    
    this.sender = sender;
  }
  
  public boolean isPlayer()
  {
    return sender instanceof Player;
  }
  
  public Player getPlayer()
  {
    if(sender instanceof Player)
    {
      return (Player) sender;
    }
    
    return null;
  }
  
  public CommandSender getSender()
  {
    return sender;
  }
  
  public void sendMessage(String message)
  {
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
  }
  
  public boolean hasPermission(String permission)
  {
    return sender.hasPermission(permission);
  }
  
  public boolean hasAnyPermissions(String... permissions)
  {
    for(String permission : permissions)
    {
      if(sender.hasPermission(permission))
      {
        return true;
      }
    }
    
    return false;
  }
}
