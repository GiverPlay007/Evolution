package me.giverplay.evolution.command.commands;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.commands.Commandhome;
import com.earth2me.essentials.commands.NoChargeException;
import com.google.common.collect.Lists;
import java.util.List;
import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.command.CommandSource;
import me.giverplay.evolution.command.EvolutionCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HomeCommand extends EvolutionCommand
{
  private final EvolutionAPI evo;
  private final Essentials ess;
  private final Commandhome commandhome;
  
  public HomeCommand(EvolutionAPI evo)
  {
    super("home", false);
    
    this.evo = evo;
    this.ess = evo.getEssentials();
    this.commandhome = new Commandhome();
  }
  
  @Override
  public void execute(CommandSource sender, String[] args)
  {
    try
    {
      commandhome.run(ess.getServer(), sender.getEvolutionPlayer().getEssentialsUser(), "home", args);
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
      return Lists.newArrayList();
    }
    
    return commandhome.tabComplete(ess.getServer(), evo.getPlayerManager().getPlayer(sender.getName()).getEssentialsUser(), alias, command, args);
  }
}
