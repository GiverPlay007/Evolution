package me.giverplay.evolution_old.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution_old.Evolution;
import me.giverplay.evolution_old.api.comando.Comando;
import me.giverplay.evolution_old.api.comando.ComandoType;

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
