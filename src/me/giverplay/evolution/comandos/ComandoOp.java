package me.giverplay.evolution.comandos;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

public class ComandoOp extends Comando
{
	public ComandoOp()
	{
		super("op", ComandoType.GERAL, "/op <nick>");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		String msg = "�cEste comando s� pode ser executado pelo Desenvolvedor";
		
		if(!(sender instanceof Player))
		{
			sender.sendMessage(msg);
			return;
		}
		
		if(!Evolution.getInstance().getPlayer(sender.getName()).isDeveloper())
		{
			sender.sendMessage(msg);
			return;
		}
		
		if(args.length == 0)
		{
			sender.sendMessage(getUsage());
			return;
		}
		
		OfflinePlayer pl = Bukkit.getOfflinePlayer(args[0]);
		
		if(pl == null)
		{
			sender.sendMessage("�cJogador n�o encontrado");
			return;
		}
		
		pl.setOp(true);
	}
}
