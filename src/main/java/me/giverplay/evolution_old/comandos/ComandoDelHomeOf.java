package me.giverplay.evolution_old.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution_old.Evolution;
import me.giverplay.evolution_old.api.comando.Comando;
import me.giverplay.evolution_old.api.comando.ComandoType;

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

		if(!player.hasPermission("evolution.homeof"))
		{
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
			if(!Evolution.getInstance().hasUnknownHomes(uuid))
			{
				player.sendMessage("§cO jogador especificado não possui uma casa padrão");
				return;
			}

			Evolution.getInstance().deleteUnknownHome(uuid);
			player.sendMessage("§cCasa padrão de §f" + target.getName() + " §afoi apagada com sucesso");
			target.sendMessage("§cSua casa padrão foi deletada por um Admin");

			return;
		}
		
		String homeName = args[1];
		
		if(!(Evolution.getInstance().hasNamedHomes(uuid)) || !(Evolution.getInstance().getPlayersNamedHomes(uuid).containsKey(homeName)))
		{
			player.sendMessage("§cO jogador §f" + target.getName() + " §cnão possui a casa §f" + homeName);
			return;
		}
		
		Evolution.getInstance().deleteNamedHome(uuid, homeName);
		player.sendMessage("§cCasa §f" + homeName + " §c de §f" + target.getName() + " §cfoi deletada");
		target.sendMessage("§cSua casa §f" + homeName + " §cfoi deletada por um Admin");
		
	}
}
