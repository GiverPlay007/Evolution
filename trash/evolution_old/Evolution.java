package me.giverplay.evolution_old;

import java.util.ArrayList;
import java.util.List;
import me.giverplay.evolution_old.api.Formater;
import me.giverplay.evolution_old.api.PBar;
import me.giverplay.evolution_old.api.RankNovo;
import me.giverplay.evolution_old.api.manager.ConfigManager;
import me.giverplay.evolution_old.api.manager.PlayerManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_16_R2.MinecraftServer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Evolution extends JavaPlugin 
{
	public String getTime(int segundos)
	{
		StringBuilder tempo = new StringBuilder();
		
		if(segundos >= 60)
		{
			int m = segundos / 60;
			int s = segundos % 60;
			tempo.append(m + " minuto");
			
			if(m > 1)
			{
				tempo.append("s");
			}
			
			if(s > 0)
			{
				tempo.append(" e " + s + " segundos");
			}		
		}
		else
		{
			tempo.append(segundos + " segundos");
		}
		
		return tempo.toString();
	}
	
	public String createTimerLabel(int segundos)
	{
		String timeLabel = "";
		int min, sec;
		
		min = segundos / 60;
		sec = segundos % 60;
		
		timeLabel += min + ":";
		timeLabel += (sec < 10 ? "0" + sec : sec);
		return timeLabel;
	}
	
	public void sendAction(Player player, String message)
	{
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	}
	
	public void setHeaderAndFooter(Player player) 
	{
		player.setPlayerListHeaderFooter("§a§lEvolution§f§lCity\n\n§7----------",
				"§7----------\n\n§eBom jogo!");
	}
	
	public String getProgress(PlayerManager player)
	{
		double atual = player.getXp();
		int xpNeeded =  Evolution.getInstance().getLevelConfig().getInt("niveis." + (player.getLevel() + 1) + ".xp");
		
		if(atual >= xpNeeded)
		{
			return "§8[§a" + StringUtils.repeat("|", 15) + "§8]";
		}
		
		return PBar.getProgressBarScore(atual, xpNeeded, 15, "|", "§a", "§7", "§8[", "§8]");
	}
	
	@SuppressWarnings({"resource" })
	public String getTPS()
	{
		double tps = MinecraftServer.getServer().recentTps[0];
		
		return ((tps > 18.0D) ? ChatColor.GREEN : ((tps > 16.0D) ? ChatColor.YELLOW : ChatColor.RED)).toString()
				+ ((tps > 20.0D) ? "*" : "")
				+ Math.min(Math.round(tps * 100.0D) / 100.0D, 20.0D);
	}
	
	public String getMoney(Player p)
	{
		return Formater.format((long) economy.getBalance(p));
	}
	
	private boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> economyProvider = 
				getServer().
				getServicesManager().
				getRegistration(Economy.class);
		
		if(economyProvider != null)
		{
			economy = (Economy) economyProvider.getProvider();
		}
		
		return(economy != null);
	}
	
	private void setupRankupsNovo()
	{		
		ConfigManager cf = ranks;
		
		for(int i = 0; i < 101; i++)
		{
			String path = "ranks." + String.valueOf(i);
			String nome = cf.getString(path + ".nome");
			String prefixo = cf.getString(path + ".prefixo").replace("&", "§");
			String proximo = cf.getString(path + ".proximo");
			List<String> needs = cf.getStringList(path + ".custo");
			List<ItemStack> finalNeed = new ArrayList<>();
			boolean ultimo = cf.getBoolean(path + ".ultimo");
			int level = cf.getInt(path + ".nivel");
			
			for(String item : needs)
			{
				String[] split = item.split(";");
				Material mat = Material.matchMaterial(split[0], false);
				
				if(!(split.length > 1) || mat == null)
					continue;
				
				int amn;
				
				try
				{
					amn = Integer.parseInt(split[1]);
				}
				catch(NumberFormatException e){ continue; }
				
				finalNeed.add(new ItemStack(mat, amn));
			}
			
			rankups.put(nome, new RankNovo(nome, prefixo, ultimo, finalNeed, level, proximo));
		}
	}
}
