package me.giverplay.evolution.command;

import java.util.HashMap;
import me.giverplay.evolution.EvolutionAPI;
import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class CommandManager implements CommandExecutor
{
  private final HashMap<String, EvolutionCommand> commands = new HashMap<>();
  private final EvolutionAPI evo;
  
  public CommandManager(EvolutionAPI api)
  {
    Validate.notNull(api, "EvolutionAPI não pode ser nulo!");
    
    this.evo = api;
  }
  
  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
  {
    EvolutionCommand cmd = commands.get(command.getName());
    CommandSource sender = new CommandSource(commandSender);
    
    if(!sender.hasPermission(cmd.getBasePermission()))
    {
      sender.sendMessage("&cVocê não tem permissão para executar esse comando!");
      return true;
    }
    
    if(!cmd.isAllowConsole() && !sender.isPlayer())
    {
      sender.sendMessage("&cEste comando só pode ser executado por jogadores.");
      return true;
    }
    
    try
    {
      cmd.execute(sender, args);
    }
    catch(Throwable t)
    {
      evo.getLogger().warning("O comando " + cmd.getName() + " teve uma exceção não tratada.");
      t.printStackTrace();
    }
    
    return true;
  }
  
  public void registerCommand(EvolutionCommand cmd)
  {
    Validate.notNull(cmd, "O comando não pode ser nulo!");
    Validate.isTrue(!commands.containsKey(cmd.getName()), "O comando " + cmd + " ja foi registrado!");
    
    PluginCommand command = evo.getPlugin().getCommand(cmd.getName());
    Validate.notNull(command, "O comando " + cmd + " não está registrado no plugin.yml.");
    
    command.setExecutor(this);
    command.setTabCompleter(cmd);
    commands.put(cmd.getName(), cmd);
  }
}
