package me.giverplay.evolution.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class ComandoDelHomeOf extends Comando
{
	public ComandoDelHomeOf()
	{
		super("delhome-of", ComandoType.PLAYER, "/delhome-of <jogador> [casa]");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;

		if(!player.hasPermission("evolution.homeof")){
			player.sendMessage("§cSem permissão");
			return;
		}

		if(args.length == 0)
		{
			player.sendMessage(getUsage());
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);

		if(target == null)
		{
			player.sendMessage("§cJogador desconhecido ou offline");
			return;
		}

		String uuid = target.getUniqueId().toString();

		if(args.length == 1)
		{
			if(!EvolutionAPI.hasUnknownHomes(uuid))
			{
				player.sendMessage("§cO jogador especificado não possui uma casa padrão");
				return;
			}

			EvolutionAPI.deleteUnknownHome(uuid);
			player.sendMessage("§cCasa padrão de §f" + target.getName() + " §afoi apagada com sucesso");
			target.sendMessage("§cSua casa padrão foi deletada por um Admin");

			return;
		}
		
		String homeName = args[1];
		
		if(!(EvolutionAPI.hasNamedHomes(uuid)) || !(EvolutionAPI.getPlayersNamedHomes(uuid).containsKey(homeName)))
		{
			player.sendMessage("§cO jogador §f" + target.getName() + " §cnão possui a casa §f" + homeName);
			return;
		}
		
		EvolutionAPI.deleteNamedHome(uuid, homeName);
		player.sendMessage("§cCasa §f" + homeName + " §c de §f" + target.getName() + " §cfoi deletada");
		target.sendMessage("§cSua casa §f" + homeName + " §cfoi deletada por um Admin");
		
	}
}
