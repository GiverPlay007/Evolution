package me.giverplay.evolution.comandos;

import org.bukkit.command.CommandSender;

import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class ComandoReloadEvolution extends Comando
{

	public ComandoReloadEvolution()
	{
		super("reloadevolution", ComandoType.GERAL, "/reloadevolution");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(!sender.hasPermission("evolution.reload"))
		{
			sender.sendMessage("§aSem permissão");
			return;
		}
		
		// Em desenvolvimento
	}
}
