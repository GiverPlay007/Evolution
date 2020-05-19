package me.giverplay.evolution.comandos;

import org.bukkit.command.CommandSender;

import me.giverplay.evolution.Variaveis;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;
import me.giverplay.evolution.api.manager.PlayerManager;
import me.giverplay.evolution.eventos.TNTRun;

public class ComandoTntRun extends Comando
{
	public ComandoTntRun()
	{
		super("tntrun", ComandoType.PLAYER, "/tntrun <action>");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		PlayerManager player = Variaveis.playersHashMap.get(sender.getName());
		
		if(args.length == 0)
		{
			player.sendMessage(getUsage());
			return;
		}
		
		String action = args[0];
		
		if(action.equalsIgnoreCase("inciar"))
		{
			// TODO
		}
		
		else if(action.equalsIgnoreCase("resetbaseplate")){
			if(!player.isDeveloper()){
				player.sendMessage("�cVoc� n�o tem permiss�o, meu caro");
				return;
			}
			
			player.sendMessage("�aResetando");
			TNTRun.reset();
		}
		
		else if(action.equalsIgnoreCase("entrar"))
		{
			if(!TNTRun.acontecendo)
			{
				player.sendMessage("�cEvento TNT Run n�o est� acontecendo no momento.");
				return;
			}
			
			if(TNTRun.participantes.contains(player))
			{
				player.sendMessage("�Voce ja est� no evento");
				return;
			}
			
			if(TNTRun.started)
			{
				player.sendMessage("�cO evento j� come�ou :(");
				return;
			}
			
			TNTRun.add(player.getPlayer());
		}
		
		else if(action.equalsIgnoreCase("camarote"))
		{
			if(!TNTRun.acontecendo)
			{
				player.sendMessage("�cO Evento n�o est� acontecendo.");
				return;
			}
			
			player.getPlayer().teleport(TNTRun.getCamarote());
		}
		
		else if(action.equalsIgnoreCase("staff"))
		{
			if(!player.getPlayer().hasPermission("evolution.tntrun.staff"))
			{
				player.sendMessage("�cSem permiss�o.");
				return;
			}
			
			player.getPlayer().teleport(TNTRun.getStaff());
		}
		
		else if(action.equalsIgnoreCase("setcamarote"))
		{
			if(!player.isDeveloper())
			{
				player.sendMessage("�cSem permiss�o");
			}
			
			TNTRun.setCamarote(player.getPlayer().getLocation());
			player.sendMessage("�aCamarote definido");
		}
		
		else if(action.equalsIgnoreCase("setstaff"))
		{
			if(!player.isDeveloper())
			{
				player.sendMessage("�cSem permiss�o");
			}
			
			TNTRun.setStaff(player.getPlayer().getLocation());
			player.sendMessage("�aCamarote STAFF definido");
		}
		
		else if(action.equalsIgnoreCase("setarea"))
		{
			if(!player.isDeveloper())
			{
				player.sendMessage("�cSem permiss�o");
				return;
			}
			
			TNTRun.setBase();
			player.sendMessage("�aArea salva nas configura��es com sucesso");
		}
		
		else if(action.equalsIgnoreCase("setinicio"))
		{
			if(!player.isDeveloper())
			{
				player.sendMessage("�cSem permiss�o");
			}
			
			TNTRun.setStaff(player.getPlayer().getLocation());
			player.sendMessage("�aInicio definido");
		}
		
		else{
			player.sendMessage("�cSub-comando inv�lido");
			return;
		}
	}
}
