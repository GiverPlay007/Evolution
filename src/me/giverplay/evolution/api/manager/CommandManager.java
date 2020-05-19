package me.giverplay.evolution.api.manager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.Variaveis;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class CommandManager implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args)
	{
		Comando comando = Variaveis.comandos.get(cmd.getName());
		
		if((comando.getType() == ComandoType.CONSOLE) && (sender instanceof Player))
		{
			sender.sendMessage("§cEste comando só pode ser executado no Console");
			return true;
		}
		
		if((comando.getType() == ComandoType.PLAYER) && (!(sender instanceof Player)))
		{
			sender.sendMessage("§cEste comando só pode ser executado por Jogadores");
			return true;
		}
		
		if(comando.getType() == ComandoType.GERAL)
		{
			Variaveis.console.sendMessage("Executando comando geral para " + sender.getName());
		}
		
		comando.execute(sender, args);
		
		return false;
	}
}
