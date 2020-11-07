package me.giverplay.evolution.command.commands;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.commands.Commanddelhome;
import com.earth2me.essentials.commands.NoChargeException;
import java.util.Collections;
import java.util.List;
import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.command.CommandSource;
import me.giverplay.evolution.command.EvolutionCommand;
import me.giverplay.evolution.player.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DelHomeCommand extends EvolutionCommand
{
  private final PlayerManager pm;
  private final Essentials ess;
  private final Commanddelhome commanddelhome;
  
  public DelHomeCommand(EvolutionAPI evo)
  {
    super("delhome", false);
    
    this.pm = evo.getPlayerManager();
    this.ess = evo.getEssentials();
    this.commanddelhome = new Commanddelhome();
  }
  
  @Override
  public void execute(CommandSource sender, String[] args)
  {
    try
    {
      commanddelhome.run(ess.getServer(), sender.asEssentialsCommandSource(), "delhome", args);
    }
    catch(NoChargeException ignored)
    {
    
    }
    catch(Exception e)
    {
      sender.sendMessage(e.getMessage());
    }
  }
  
  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
  {
    if(!isEnabled())
    {
      return Collections.emptyList();
    }
  
    return commanddelhome.tabComplete(ess.getServer(), pm.getPlayer(sender.getName()).getEssentialsUser(), alias, command, args);
  }
}
