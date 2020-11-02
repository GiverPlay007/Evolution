package me.giverplay.evolution_old.handlers;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.giverplay.evolution_old.Evolution;
import me.giverplay.evolution_old.api.manager.PlayerManager;

public class ListenerCommandManager implements Listener
{
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event)
	{
		String[] input = event.getMessage().split(" ");
		PlayerManager player = Evolution.getInstance().getPlayer(event.getPlayer().getName());
		
		if(input[0].equalsIgnoreCase("/pex"))
		{
			if(!player.isDeveloper())
			{
				event.setCancelled(true);
				player.sendMessage("§cSem permissão para executar este comando (O comando foi desabilitado para usuários in-game, exceto o desenvolvedor deste servidor)");
			}
			
			return;
		}
		
		if(input[0].equalsIgnoreCase("/plugins") || input[0].equalsIgnoreCase("/pl"))
		{
			event.setCancelled(true);
			Evolution.getInstance().getRegisteredCommands().get("plugins").execute(player.getPlayer(), input);
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 1; i < input.length; i++)
		{
			sb.append(input[i]);
			sb.append(" ");
		}
		
		String[] args = sb.toString().split(" ");
		
		
		if((input[0].equalsIgnoreCase("/r")) || (input[0].equalsIgnoreCase("/reply")) )
		{
			event.setCancelled(true);
			
			Evolution.getInstance().getRegisteredCommands().get("reply").execute((CommandSender) event.getPlayer(), args);
		}
	}
}
