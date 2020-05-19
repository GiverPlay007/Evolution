package me.giverplay.evolution.comandos;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class ComandoDelHome extends Comando
{
	public ComandoDelHome()
	{
		super("delhome", ComandoType.PLAYER, "/delhome [casa]");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String uuid = player.getUniqueId().toString();

		if(args.length == 0){
			if(!EvolutionAPI.hasUnknownHomes(uuid))
			{
				player.sendMessage(getUsage());
				return;
			}

			EvolutionAPI.deleteUnknownHome(uuid);
			player.sendMessage("§aCasa padrão deletada");
			return;
		}
		
		if(!(EvolutionAPI.hasNamedHomes(uuid)) || !(EvolutionAPI.getPlayersNamedHomes(uuid).containsKey(args[0])))
		{
			player.sendMessage("§cVocê não possui uma casa com esse nome");
			return;
		}
		
		EvolutionAPI.deleteNamedHome(uuid, args[0]);
		player.sendMessage("§aCasa §f" + args[0] + " §adeletada com sucesso");
	}
}
