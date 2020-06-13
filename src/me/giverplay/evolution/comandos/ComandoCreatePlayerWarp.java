package me.giverplay.evolution.comandos;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.api.PlayerWarp;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ComandoCreatePlayerWarp extends Comando
{
	public ComandoCreatePlayerWarp()
	{
		super("createplayerwarp", ComandoType.PLAYER, "/createplayerwarp <warp>");
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
		
		int limite = 0;
		
		if(player.getPlayer().hasPermission("evolution.vip.prata"))
		{
			limite = 1;
		}
		
		if(player.getPlayer().hasPermission("evolution.vip.ouro"))
		{
			limite = 2;
		}
		
		if(player.getPlayer().hasPermission("evolution.vip.platina"))
		{
			limite = 3;
		}
		
		ArrayList<PlayerWarp> list = Evolution.getInstance().getWarps(player.getName());
		
		if(!(list == null))
		{
			if(list.size() >= limite)
			{
				player.sendMessage("§cVocê já tem todas as warps de seu limite...");
				return;
			}
		}
		
		PlayerWarp warp = new PlayerWarp(player.getPlayer().getLocation(), args[0], player.getName());
		warp.saveWarp();
		
		player.sendMessage("§aWarp criada com sucesso: §f" + warp.getWarpName());
	}
}
