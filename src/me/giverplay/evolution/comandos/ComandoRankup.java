package me.giverplay.evolution.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.giverplay.evolution.Variaveis;
import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.Rank;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ComandoRankup extends Comando
{
	public ComandoRankup()
	{
		super("rankup", ComandoType.PLAYER, "/rankup");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		PlayerManager player = EvolutionAPI.getPlayer(sender.getName());
		
		if(player.getRank().isLastRank()){
			player.sendMessage("§eVocê já está no último rank, meu consagrado");
			return;
		}
		
		final Rank proximo = Variaveis.rankups.get(player.getRank().getNextRank());
		
		if(player.getLevel() < proximo.getMinLevel()){
			player.sendMessage("\n§cVocê não possui o seguinte requisito: §fNível " + proximo.getMinLevel() + "\n");
			return;
		}
		
		if(Variaveis.economy.getBalance(player.getPlayer()) < proximo.getCost()){
			player.sendMessage("\n§cVocê não possui o seguinte requisito: §f" + Variaveis.rankups.get(player.getRank().getNextRank()).getCost() + " de money\n");
			return;
		}
		
		if(!player.isDeveloper()){
			int ni = player.getRank().getMinLevel();
			long cooldown = (ni > 80 ? 180 : (ni > 70 ? 150 : (ni > 40 ? 120 : (ni > 15 ? 60 : 40))));
			cooldown *= 1000;
			
			long tempo = System.currentTimeMillis() - player.getRankupTime();
			
			if(tempo < (cooldown)){
				player.sendMessage("§cAguarde mais alguns segundos antes de upar...");
				player.sendMessage("§cMais §f" + String.valueOf((tempo - cooldown ) / 1000) + " segundos");
				return;
			}
		}
		
		Variaveis.economy.withdrawPlayer(player.getPlayer(), proximo.getCost());
		player.setRank(proximo);
		
		for(Player p : Bukkit.getOnlinePlayers()){
			EvolutionAPI.sendAction(p, "§6" + p.getName() + " §eupou para " + proximo.getPrefix());
		}
	}
}
