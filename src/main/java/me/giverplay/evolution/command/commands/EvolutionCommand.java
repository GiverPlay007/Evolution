package me.giverplay.evolution.command.commands;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.command.CommandSource;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EvolutionCommand extends me.giverplay.evolution.command.EvolutionCommand
{
  private static final List<String> OPTIONS = Arrays.asList("save-db", "lockdown", "reload");
  private static final List<String> OPTIONS_RELOAD = Arrays.asList("save-db", "lockdown", "reload");
  
  private final EvolutionAPI evo;
  
  public EvolutionCommand(EvolutionAPI evo)
  {
    super("evolution", true);
    
    this.evo = evo;
  }
  
  @Override
  public void execute(CommandSource sender, String[] args)
  {
    if(args.length == 0)
    {
      sender.sendMessage("&aServidor lindo, né?.");
      return;
    }
    
    if(!sender.hasPermission("evolution.manager"))
    {
      return;
    }
    
    if(args[0].equalsIgnoreCase("save-db"))
    {
      evo.saveDatabase(sender.getSender());
    }
    else if(args[0].equalsIgnoreCase("lockdown"))
    {
      evo.toggleLockdown();
    }
    else if(args[0].equalsIgnoreCase("reload"))
    {
      boolean plugin;
      
      if(args.length >= 2)
      {
        if(args[1].equals("plugin"))
        {
          plugin = true;
        }
        else if(args[1].equals("config"))
        {
          plugin = false;
        }
        else
        {
          sendUsage(sender, "/evolution reload <config|plugin>");
          return;
        }
        
        sender.sendMessage("&cIniciando reload.");
        evo.reload(plugin);
      }
      
      sendUsage(sender, "/evolution reload <config|plugin>", "/evolution reload plugin");
      return;
    }
    
    sendUsage(sender, "/evolution <reload|lockdown|save-db>", "/evolution save-db");
  }
  
  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
  {
    List<String> completions = Lists.newArrayList();
    
    if(sender.hasPermission("evolution.manager"))
    {
      if(args.length == 0)
      {
        completions.addAll(OPTIONS);
      }
      else if(args.length == 1)
      {
        completions.addAll(OPTIONS.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList()));
      }
      else if(args.length == 2)
      {
        if(args[0].equals("reload"))
        {
          completions.addAll(OPTIONS_RELOAD.stream().filter(s -> s.startsWith(args[1])).collect(Collectors.toList()));
        }
      }
    }
    
    return completions;
  }
}
