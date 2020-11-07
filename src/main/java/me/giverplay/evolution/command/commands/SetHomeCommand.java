package me.giverplay.evolution.command.commands;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.commands.Commandsethome;
import com.earth2me.essentials.commands.NoChargeException;
import java.util.Collections;
import java.util.List;
import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.command.CommandSource;
import me.giverplay.evolution.command.EvolutionCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SetHomeCommand extends EvolutionCommand
{
  private final Commandsethome commandsethome;
  private final EvolutionAPI evo;
  private final Essentials ess;
  
  public SetHomeCommand(EvolutionAPI evo)
  {
    super("sethome", false);
    
    this.evo = evo;
    this.ess = evo.getEssentials();
    commandsethome = new Commandsethome();
  }
  
  @Override
  public void execute(CommandSource sender, String[] args)
  {
    try
    {
      commandsethome.run(ess.getServer(), sender.getEvolutionPlayer().getEssentialsUser(), "sethome", args);
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
  
    return commandsethome.tabComplete(ess.getServer(), evo.getPlayerManager().getPlayer(sender.getName()).getEssentialsUser(), alias, command, args);
  }
}
