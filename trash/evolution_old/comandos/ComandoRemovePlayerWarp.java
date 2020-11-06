package me.giverplay.evolution_old.comandos;

import org.bukkit.command.CommandSender;

import me.giverplay.evolution_old.Evolution;
import me.giverplay.evolution_old.api.PlayerWarp;
import me.giverplay.evolution_old.api.comando.Comando;
import me.giverplay.evolution_old.api.comando.ComandoType;
import me.giverplay.evolution_old.api.manager.PlayerManager;

public class ComandoRemovePlayerWarp extends Comando
{
	
	public ComandoRemovePlayerWarp()
	{
		super("removeplayerwarp", ComandoType.PLAYER, "/removeplayerwarp <warp>");		
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		PlayerManager player = Evolution.getInstance().getPlayer(sender.getName());
		
		if(!player.isVip())
		{
			player.sendMessage("§eEste é um recurso VIP! Compre qualquer grupo VIP para ter acesso a esse comando =D");
			return;
		}
		
		if(args.length == 0)
		{
			player.sendMessage(getUsage());
			return;
		}
		
		PlayerWarp warp = Evolution.getInstance().getPlayerWarp(player.getName(), args[0]);
		
		if(warp == null)
		{
			player.sendMessage("§cVocê não possui esta warp");
			return;
		}
		
		warp.deleteWarp();
		player.sendMessage("§cWarp deletada: §f" + warp.getWarpName());
	}
}
