package me.giverplay.evolution_old.comandos;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution_old.Evolution;
import me.giverplay.evolution_old.api.Home;
import me.giverplay.evolution_old.api.comando.Comando;
import me.giverplay.evolution_old.api.comando.ComandoType;
import me.giverplay.evolution_old.api.manager.PlayerManager;

public class ComandoSetHome extends Comando
{
	private Evolution plugin;
	
	public ComandoSetHome()
	{
		super("sethome", ComandoType.PLAYER, "/sethome [casa]");
		plugin = Evolution.getInstance();
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String uuid = player.getUniqueId().toString();
		Home home = new Home(player.getLocation());
		PlayerManager pm = plugin.getPlayer(player.getName());
		int maxHomes = 1 + ((pm.getLevel() - (pm.getLevel() % 5)) / 20);
		
		if(args.length == 0)
		{
			plugin.saveUnknownHome(uuid, home);
			player.sendMessage("§aHome padrão definida!");
			
			return;
		}
		
		if(plugin.hasNamedHomes(uuid))
		{
			if((plugin.getPlayersNamedHomes(uuid).size() >= maxHomes) && !player.hasPermission("evolution.admin"))
			{
				player.sendMessage("§cNúmero maximo de casas atingido");
				return;
			}
			
			if(plugin.getPlayersNamedHomes(uuid).containsKey(args[0]))
			{
				player.sendMessage("§cVoc§ ja tem uma casa com esse nome... Tente outro ou apague a sua casa com esse nome");
				return;
			}
		}
		
		home.setHomeName(args[0]);
		
		plugin.saveNamedHome(uuid, home);
		
		player.sendMessage("§aSua casa '" + home.getHomeName() + "' foi definida!");
	}
}
