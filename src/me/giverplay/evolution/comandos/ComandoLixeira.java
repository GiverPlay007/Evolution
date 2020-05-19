package me.giverplay.evolution.comandos;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.api.Menus;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class ComandoLixeira extends Comando
{
	public ComandoLixeira()
	{
		super("lixeira", ComandoType.PLAYER, "/lixeira");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		player.openInventory(Menus.lixeira());
	}
}
