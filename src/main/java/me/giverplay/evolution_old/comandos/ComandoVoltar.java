package me.giverplay.evolution_old.comandos;

import org.bukkit.command.CommandSender;

import me.giverplay.evolution_old.Evolution;
import me.giverplay.evolution_old.api.comando.Comando;
import me.giverplay.evolution_old.api.comando.ComandoType;
import me.giverplay.evolution_old.api.manager.PlayerManager;
import net.md_5.bungee.api.ChatColor;

public class ComandoVoltar extends Comando
{
	public ComandoVoltar()
	{
		super("voltar", ComandoType.PLAYER, "/voltar");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		PlayerManager player = Evolution.getInstance().getPlayer(sender.getName());
		
		if(player.getDeathLocation() != null)
		{
			player.getPlayer().teleport(player.getDeathLocation());
			player.sendMessage(ChatColor.GREEN + "Teleportando para onde você morreu...");
			player.setDeathLocation(null);
			return;
		}
		
		player.sendMessage(ChatColor.RED + "Você não morreu hoje ou já teleportou... Morra novamente :)");
	}
}
