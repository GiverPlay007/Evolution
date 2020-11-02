package me.giverplay.evolution_old.api.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.giverplay.evolution_old.Evolution;
import me.giverplay.evolution_old.api.RankNovo;

public class PlayerManager
{
	private String name;
	private Player player, reply;
	private Location deathLocation;
	
	private int level, xp;
	private boolean tell;
	private boolean scoreboardEnable;
	private boolean tpaEnabled;
	private long loginTime, rankupTime;
	
	private RankNovo rank;
	private Evolution plugin;
	
	public PlayerManager(String name)
	{
		plugin = Evolution.getInstance();
		
		this.rank = plugin.getRanks().get(plugin.getPlayersConfig().getString(name + ".niveis.rank"));
		this.name = name;
		this.player = Bukkit.getPlayer(name);
		this.level = plugin.getPlayersConfig().getInt(name + ".niveis.nivel");
		this.xp = plugin.getPlayersConfig().getInt(name + ".niveis.xp");
		this.reply = null;
		this.tell = true;
		this.rankupTime = System.currentTimeMillis();
		this.scoreboardEnable = true;
		this.tpaEnabled = true;
		this.deathLocation = null;
	}

	public String getName()
	{
		return this.name;
	}

	public Player getPlayer()
	{
		return this.player;
	}

	public void sendMessage(String message)
	{
		this.player.sendMessage(message);
	}

	public int getLevel()
	{
		return this.level;
	}

	public void setLevel(int level)
	{
		this.level = level;
		plugin.getPlayersConfig().set(name + ".niveis.nivel", this.level);
	}

	public int getXp()
	{
		return this.xp;
	}

	public void setXp(int xp)
	{
		this.xp = xp;
		plugin.getPlayersConfig().set(name + ".niveis.xp", this.xp);
	}

	public double getMoney()
	{
		return plugin.getEconomy().getBalance(this.player);
	}

	public void giveMoney(double quantia)
	{
		plugin.getEconomy().depositPlayer(this.player, quantia);
	}

	public void takeMoney(double quantia)
	{
		plugin.getEconomy().withdrawPlayer(this.player, quantia);
	}

	public boolean isDeveloper()
	{
		return name.equals("GiverPlay007");
	}
	
	public boolean isTPAEnabled()
	{
		return this.tpaEnabled;
	}
	
	public void setTPAEnabled(boolean set)
	{
		this.tpaEnabled = set;
		player.sendMessage((set ? "§aHabilitando" : "§cDesabilitando") + " recebimento de TPA");
	}
	
	public boolean isAdmin()
	{
		return name.equals("GiverPlay007");
	}
	
	public boolean isScoreboardEnabled()
	{
		return this.scoreboardEnable;
	}
	
	public boolean isMod(){
		return player.hasPermission("evolution.moderador");
	}
	
	public boolean isVip(){
		return player.hasPermission("evolution.vip");
	}

	public Player getReply()
	{
		return this.reply;
	}

	public void setReply(Player reply)
	{
		this.reply = reply;
	}

	public boolean getTellEnabled()
	{
		return this.tell;
	}

	public void setTellEnabled(boolean newTell)
	{
		this.tell = newTell;
		
		player.sendMessage((newTell ? "§aHabilitando" : "§cDesabilitando") + " recebimento de mensagem privada");
	}
	
	public long getLoginTime()
	{
		return this.loginTime;
	}
	
	public long getRankupTime()
	{
		return this.rankupTime;
	}
	
	public void setLoginTime(long login)
	{
		this.loginTime = login;
	}
	
	public RankNovo getRank()
	{
		return this.rank;
	}
	
	public void setScoreboardEnable(boolean set)
	{
		this.scoreboardEnable = set;
		player.sendMessage((set ? "§aHabilitando" : "§cDesabilitando") + " visualização de Scoreboard");
		
		if(set)
		{
			ScoreboardManager.build(this);
		}
		else
		{
			this.player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}
	
	public void setRank(RankNovo rank)
	{
		/*
		if(rank.getMinLevel() % 5 == 0)
		{
			PermissionsEx.getUser(player).removeGroup(this.rank.getName().replace(String.valueOf(this.rank.getName().charAt(this.rank.getName().length() - 1)), "").trim());
			PermissionsEx.getUser(player).addGroup(rank.getName().replace(String.valueOf(rank.getName().charAt(rank.getName().length() - 1)), "").trim());
		}
		*/
		
		this.rank = rank;
		this.rankupTime = System.currentTimeMillis();
		plugin.getPlayersConfig().set(name + ".niveis.rank", rank.getName());
	}
	
	public void setDeathLocation(Location loc)
	{
		this.deathLocation = loc;
	}
	
	public Location getDeathLocation()
	{
		return this.deathLocation;
	}
}
