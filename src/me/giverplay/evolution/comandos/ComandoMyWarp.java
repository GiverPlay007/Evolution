package me.giverplay.evolution.comandos;

import org.bukkit.command.CommandSender;

import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.PlayerWarp;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ComandoMyWarp extends Comando
{
	
	public ComandoMyWarp()
	{
		super("mywarp", ComandoType.PLAYER, "/mywarp <warpID>");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		PlayerManager player = EvolutionAPI.getPlayer(sender.getName());
		
		if(!player.isVip()){
			player.sendMessage("§cVocê não possui nenhum grupo VIP");
			return;
		}
		
		if(args.length == 0){
		  player.sendMessage(getUsage());
			return;
		}
		
		PlayerWarp warp = EvolutionAPI.getPlayerWarp(player.getName(), args[0]);
		
		if(warp == null){
			player.sendMessage("§cNenhuma warp com este nome...");
			return;
		}
		
		warp.teleportWarp(player.getPlayer());
	}	
}
