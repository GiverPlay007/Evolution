package me.giverplay.evolution.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class ComandoHomes extends Comando
{
  public ComandoHomes()
	{
		super("homes", ComandoType.PLAYER, "/homes");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{

		Player player = (Player) sender;
		
		if(args.length == 0)
		{
			Evolution.getInstance().listHomes(player);
			return;
		}
		
		if(!player.hasPermission("evolution.homeof"))
		{
			player.sendMessage("§cSem permissão para listar casas de outras pessoas");
			return;
		}
		
		Player target = Bukkit.getPlayer(args[0]);
		
		if(target == null)
		{
			player.sendMessage("§cJogador offline ou desconhecido");
			return;
		}
		
		Evolution.getInstance().listHomes(target, player);
	}
}
