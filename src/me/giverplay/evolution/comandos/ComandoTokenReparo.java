package me.giverplay.evolution.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.api.Stacks;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class ComandoTokenReparo extends Comando
{
  public ComandoTokenReparo()
  {
	  super("tokenreparo", ComandoType.GERAL, "/tokenreparo <jogador> <quantidade>");
  }
  
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(!sender.hasPermission("evolution.tokens"))
		{
			sender.sendMessage("§cSem permissão");
			return;
		}
		
		if(args.length < 2)
		{
			sender.sendMessage(getUsage());
			return;
		}
		
		Player target = Bukkit.getPlayer(args[0]);
		
		if(target == null)
		{
			sender.sendMessage("§cJogador não encontrado");
			return;
		}
		
		int quantidade;
		
		try
		{
			quantidade = Integer.valueOf(args[1]);
		}
		catch (NumberFormatException e)
		{
			sender.sendMessage("§cInsira um numero inteiro");
			return;
		}
		
		if(quantidade <= 0)
		{
			sender.sendMessage("§cInsira um numero maior que zero");
			return;
		}
		
		sender.sendMessage("§aDando Token de Reparo para " + target.getName());
		target.sendMessage("§aRecebendo Token de Reparo >> por " + sender.getName());
		
		target.getInventory().addItem(Stacks.getTokenReparo(quantidade));

	}
}
