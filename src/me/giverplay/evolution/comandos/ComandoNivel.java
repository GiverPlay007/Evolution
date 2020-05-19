package me.giverplay.evolution.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.Variaveis;
import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ComandoNivel extends Comando
{
	public ComandoNivel()
	{
		super("nivel", ComandoType.PLAYER, "/nivel [jogador]");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		
		if(args.length == 0)
		{
			PlayerManager player = EvolutionAPI.getPlayer(sender.getName());
			
			int proximo = (Variaveis.niveis.getInt("niveis." + (player.getLevel() + 1) + ".xp")) - player.getXp();
			
			player.sendMessage(" ");
			player.sendMessage("§aNivel: §f" + player.getLevel());
			player.sendMessage("§aXP: §f" + player.getXp());
			player.sendMessage("§aProximo nivel: +§f" + proximo + " EXP");
			player.sendMessage(" ");
			
			return;
		}
		
		if(args.length > 2)
		{
			if(!sender.hasPermission("evolution.niveis"))
			{
				sender.sendMessage(getUsage());
				return;
			}
			
			// TODO Futuro gerenciamento de niveis
			
			return;
		}
		
		Player target = Bukkit.getPlayer(args[0]);
		
		if(target == null)
		{
			sender.sendMessage("§cJogador desconhecido ou offline");
			return;
		}
		
		PlayerManager targetManager = EvolutionAPI.getPlayer(target.getName());
		int proximo = (Variaveis.niveis.getInt("niveis." + (targetManager.getLevel() + 1) + ".xp")) - targetManager.getXp();
		
		sender.sendMessage(" ");
		sender.sendMessage("§aNivel de " + target.getName() +": §f" + targetManager.getLevel());
		sender.sendMessage("§aXP de " + target.getName() +": §f" + targetManager.getXp());
		sender.sendMessage("§aProximo nivel: +§f" + proximo + " EXP");
		sender.sendMessage(" ");
	}
}
