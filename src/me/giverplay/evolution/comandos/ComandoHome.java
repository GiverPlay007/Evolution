package me.giverplay.evolution.comandos;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class ComandoHome extends Comando
{
	public ComandoHome()
	{
		super("home", ComandoType.PLAYER, "/home [casa]");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String uuid = player.getUniqueId().toString();

		EvolutionAPI.teleportHome(player, uuid, args);
	}
}
