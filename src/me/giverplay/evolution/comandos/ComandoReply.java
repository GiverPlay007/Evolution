package me.giverplay.evolution.comandos;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ComandoReply extends Comando
{
	public ComandoReply()
	{
		super("reply", ComandoType.PLAYER, "/reply <mensagem>");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		PlayerManager player = Evolution.getInstance().getPlayer(sender.getName());
		
		Player alvo = player.getReply();
		
		if(alvo == null)
		{
			player.sendMessage("§cNinguém para responder");
			return;
		}
		
		PlayerManager target = Evolution.getInstance().getPlayer(player.getReply().getName());
		
		if(!target.getTellEnabled())
		{
			player.sendMessage("§cJogador com tell desativado!");
			return;
		}
		
		if(!player.getTellEnabled())
		{
			player.setTellEnabled(true);;
		}
		
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < args.length; i++)
		{
			sb.append(" ");
			sb.append(args[i]);
		}

		String msg = sb.toString();

		sender.sendMessage("§6[§eMensagem para " + target.getName() + "§6]§f" + msg);
		target.sendMessage("§6[§eMensagem de " + sender.getName() + "§6]§f" + msg);

		target.setReply(player.getPlayer());
		player.setReply(target.getPlayer());
	}
}
