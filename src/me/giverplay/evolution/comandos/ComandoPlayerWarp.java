package me.giverplay.evolution.comandos;

import org.bukkit.command.CommandSender;

import me.giverplay.evolution.Variaveis;
import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.Menus;
import me.giverplay.evolution.api.PlayerWarp;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ComandoPlayerWarp extends Comando
{
	
	public ComandoPlayerWarp()
	{
		super("playerwarp", ComandoType.PLAYER, "/playerwarp <nick> [warp]");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		PlayerManager player = EvolutionAPI.getPlayer(sender.getName());
		
		if(args.length == 0){
			player.sendMessage(getUsage());
			return;
		}
		
		if(!Variaveis.warps.getConfig().isSet(args[0])){
			player.sendMessage("§cEste jogador não possui warps");
			return;
		}
		
		if(args.length == 1){
			player.getPlayer().openInventory(Menus.playerWarps(args[0]));
			return;
		}

		PlayerWarp warp = EvolutionAPI.getPlayerWarp(player.getName(), args[1]);
		
		if(warp == null){
			player.sendMessage("§cO jogador §f" + args[0] + " §cnão possui a warp §f" + args[1]);
			return;
		}
		
		warp.teleportWarp(player.getPlayer());
	}
}
