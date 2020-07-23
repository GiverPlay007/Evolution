package me.giverplay.evolution.api.manager;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.giverplay.evolution.Evolution;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@SuppressWarnings("deprecation")
public class ScoreboardManager
{
	public static void build(PlayerManager player)
	{
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		if(!player.isScoreboardEnabled())
			return;
		
		Objective obj = scoreboard.registerNewObjective("OBJ", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);;
		obj.setDisplayName("§6§lEvolution");
		
		Team rank = scoreboard.registerNewTeam("rank");
		Team proximo = scoreboard.registerNewTeam("proximo");
		Team progresso = scoreboard.registerNewTeam("progresso");
		Team nivel = scoreboard.registerNewTeam("nivel");
		Team money = scoreboard.registerNewTeam("money");
		
		rank.addEntry("§e Rank: §f");
		proximo.addEntry("§e Proximo: §f");
		progresso.addEntry("§e Progresso: §f");
		nivel.addEntry("§e Nivel: §f");
		money.addEntry("§e Money: §f");
		
		obj.getScore("").setScore(8);
		obj.getScore("§e Rank: §f").setScore(7);
		obj.getScore("§e Proximo: §f").setScore(6);
		obj.getScore("§e Progresso: §f").setScore(5);
		obj.getScore(" ").setScore(4);
		obj.getScore("§e Nivel: §f").setScore(3);
		obj.getScore("§e Money: §f").setScore(2);
		obj.getScore("  ").setScore(1);
		obj.getScore("§cloja.exemplo.kkkkkk/").setScore(0);
		
		player.getPlayer().setScoreboard(scoreboard);
		update(player);
	}
	
	public static void update(PlayerManager player)
	{
		player.getPlayer().setPlayerListName(PermissionsEx.getUser(player.getPlayer()).getPrefix().replace("&", "§") + player.getName());
		
		if(!player.isScoreboardEnabled())
		{
			player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			return;
		}
		
		Scoreboard sb = player.getPlayer().getScoreboard();
		
		if(sb.getTeam("rank") == null && player.isScoreboardEnabled())
		{
			build(player);
			return;
		}
		
		sb.getTeam("rank").setSuffix(player.getRank().getName());
		sb.getTeam("proximo").setSuffix((player.getRank().isLastRank() ? "Nenhum =D" : player.getRank().getNextRank()));
		sb.getTeam("progresso").setSuffix(player.getRank().isLastRank() ? "100%" :(Evolution.getInstance().getProgress(player)));
		sb.getTeam("nivel").setSuffix(String.valueOf(player.getLevel()));
		sb.getTeam("money").setSuffix(Evolution.getInstance().getMoney(player.getPlayer()));
	}
}
