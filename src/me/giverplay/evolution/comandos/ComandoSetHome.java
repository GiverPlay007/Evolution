package me.giverplay.evolution.comandos;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.Home;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ComandoSetHome extends Comando
{
	public ComandoSetHome()
	{
		super("sethome", ComandoType.PLAYER, "/sethome [casa]");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		String uuid = player.getUniqueId().toString();
		Location loc = player.getLocation();
		Home home = new Home(loc);
		PlayerManager pm = EvolutionAPI.getPlayer(player.getName());
		int maxHomes = 1;
		
		if(pm.getLevel() >= 50)
		{
			maxHomes = 2;
		}
		if(pm.getLevel() >= 70)
		{
			maxHomes = 3;
		}
		if(pm.getLevel() >= 85)
		{
			maxHomes = 4;
		}
		if(pm.getLevel() >= 100)
		{
			maxHomes = 5;
		}
		
		if(args.length == 0)
		{
			EvolutionAPI.saveUnknownHome(uuid, home);
			player.sendMessage("§aHome padrão definida!");
			
			return;
		}
		
		if(EvolutionAPI.hasNamedHomes(uuid)){
			if((EvolutionAPI.getPlayersNamedHomes(uuid).size() >= maxHomes) && !player.hasPermission("evolution.admin"))
			{
				player.sendMessage("§cNumero maximo de casas atingido");
				return;
			}
			
			if(EvolutionAPI.getPlayersNamedHomes(uuid).containsKey(args[0]))
			{
				player.sendMessage("§cVocê ja tem uma casa com esse nome... Tente outro ou apague a sua casa com esse nome");
				return;
			}
		}
		
		home.setHomeName(args[0]);
		
		EvolutionAPI.saveNamedHome(uuid, home);
		
		player.sendMessage("§aSua casa '" + home.getHomeName() + "' foi definida!");
	}
}
