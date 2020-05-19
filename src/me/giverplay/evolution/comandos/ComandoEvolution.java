package me.giverplay.evolution.comandos;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class ComandoEvolution extends Comando
{

	public ComandoEvolution()
	{
		super("evolution", ComandoType.PLAYER, "/evolution [ram/reload]");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;

		if(args.length == 0)
		{
			player.sendMessage("\n§e§l- Evolution -\n\n");
			player.sendMessage("§eServidor fundado por §f§nBocão§e, administrado por §f§nKunno_Mahou§e e §f§nPinkLady98§e, e desenvolvido por §f§l§nGiverPlay007§e =D\n\n");
			player.sendMessage("§c§lObs: §cEste servidor conta com recursos disponíveis gratuitamente em §c§lSpigotMC§c, e alguns recursos próprios, parte baseada em projetos encontrados no §c§lGitHub\n\n");
			player.sendMessage("§eBom jogo §c<3\n");
		}
		else
		{
			if(!player.hasPermission("evolution.evolution"))
			{
				return;
			}
			
		  if(args[0].equalsIgnoreCase("reload"))
			{
			  EvolutionAPI.reloadConfig();
			  player.sendMessage("§aConfigs recarregadas");
			}
			else if(args[0].equalsIgnoreCase("ram"))
			{

				Runtime rt = Runtime.getRuntime();

				long total = rt.totalMemory();
				long free = rt.freeMemory();
				long aloc = rt.maxMemory();

				long usage = (total - free);

				player.sendMessage(" ");
				player.sendMessage(ChatColor.YELLOW + "-= Uso de Memoria =-");
				player.sendMessage(" ");
				player.sendMessage(ChatColor.GREEN + "Uso: "
						+ ChatColor.WHITE + (usage / 1048576) + "MB");

				player.sendMessage(ChatColor.GREEN + "Livre: "
						+ ChatColor.WHITE + (free / 1048576) + "MB");

				player.sendMessage(ChatColor.GREEN + "Total: "
						+ ChatColor.WHITE + (aloc / 1048576) + "MB");

				player.sendMessage(ChatColor.GREEN + "Alocada: "
						+ ChatColor.WHITE + (total / 1048576) + "MB");

				player.sendMessage(" ");
			}
			else
			{
				player.sendMessage(getUsage());
			}
		}
	}
}
