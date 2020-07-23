package me.giverplay.evolution.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class ComandoTPAceitar extends Comando
{
	public ComandoTPAceitar()
	{
		super("tpaceitar", ComandoType.PLAYER, "/tpaceitar [jogador]");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (args.length == 0)
		{
			sender.sendMessage(getUsage());
			return;
		} 
		else if (args.length > 1)
		{
			sender.sendMessage(getUsage());
			return;
		} 
		else if (args.length == 1)
		{
			Player requester = Bukkit.getPlayer(args[0]);
			if (ComandoTPA.checkPlayers(sender, requester))
			{
				return;
			} 
			else
			{
				if (ComandoTPA.tparequest.get(requester) == sender)
				{
					ComandoTPA.tparequest.remove(requester);
					requester.teleport((Player) sender);
					requester.sendMessage("§eSeu pedido de teleporte foi aceito");
					sender.sendMessage("§eO pedido de teleporte foi aceito");
				} 
				else
				{
					sender.sendMessage("§cNenhum pedido de teleporte para aceitar!");
					return;
				}
			}
		}
	}
}
