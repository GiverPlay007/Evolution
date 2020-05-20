package me.giverplay.evolution.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ListenerNivel implements Listener
{
	public static void xpCheck(String playerName)
	{
		PlayerManager player = Evolution.getInstance().getPlayer(playerName);
		
		if(player.getLevel() >= Evolution.getInstance().getLevelConfig().getInt("nivel_maximo"))
		{
			return;
		}
		
		int nivel = player.getLevel(); 	
		int exp = player.getXp();
		int xpNeeded =  Evolution.getInstance().getLevelConfig().getInt("niveis." + (nivel + 1) + ".xp");
		
		if(exp >= xpNeeded)
		{
			player.setLevel(nivel + 1);
			player.sendMessage("§bSubiu de nivel!");
			
			Bukkit.getServer().broadcastMessage
			(ChatColor.GREEN + playerName + ChatColor.AQUA + " passou para o nível " +
					ChatColor.WHITE + (nivel + 1));
			
			Evolution.getInstance().saveAllConfigs();
			
			if(player.getLevel() % 5 == 0)
			{
				rewardChecker(player);
			}
		}
	}
	
	public static void rewardChecker(PlayerManager pm)
	{
		int level = pm.getLevel();
		Location loc = pm.getPlayer().getLocation();
		
		loc.getWorld().getBlockAt(loc).setType(Material.CHEST);
		
		Chest chest = (Chest) loc.getWorld().getBlockAt(loc).getState();
		
		if(level == 5)
		{
			chest.getBlockInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
		}
		
		if(level == 10)
		{
			chest.getBlockInventory().addItem(new ItemStack(Material.IRON_ORE, 32));
			chest.getBlockInventory().addItem(new ItemStack(Material.COAL, 32));
			chest.getBlockInventory().addItem(new ItemStack(Material.GOLD_ORE, 32));
			chest.getBlockInventory().addItem(new ItemStack(Material.LAPIS_ORE, 32));
		}
		
		if(level == 15)
		{
			chest.getBlockInventory().addItem(new ItemStack(Material.LEATHER, 32));
		}
		
		if(level == 20)
		{
			chest.getBlockInventory().addItem(new ItemStack(Material.IRON_ORE, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.DIAMOND, 1));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.WHITE_WOOL, 64));
		}
		
		if(level == 25)
		{
			chest.getBlockInventory().addItem(new ItemStack(Material.DIAMOND, 1));
			chest.getBlockInventory().addItem(new ItemStack(Material.OBSIDIAN, 20));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
		}
		
		if(level == 30)
		{
			chest.getBlockInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
			chest.getBlockInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		}
		
		if(level == 35)
		{
			chest.getBlockInventory().addItem(new ItemStack(Material.WHITE_WOOL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.WHITE_WOOL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 30));
		}
		
		if(level == 40)
		{
			chest.getBlockInventory().addItem(new ItemStack(Material.VILLAGER_SPAWN_EGG, 2));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.RAIL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.POWERED_RAIL, 32));
		}
		
		if(level == 45)
		{
			chest.getBlockInventory().addItem(new ItemStack(Material.ZOMBIE_SPAWN_EGG, 4));
			chest.getBlockInventory().addItem(new ItemStack(Material.IRON_ORE, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.GRAVEL, 64));//
			chest.getBlockInventory().addItem(new ItemStack(Material.COAL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.COAL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.COAL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.COAL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.COAL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.COAL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.COAL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.COAL, 64));
			chest.getBlockInventory().addItem(new ItemStack(Material.COAL, 64));
		}
		
		if(level == 50)
		{
			chest.getBlockInventory().addItem(new ItemStack(Material.SPAWNER, 2));
		}
		
		if(level == 55)
		{
			//chest.getBlockInventory()
		}
		
		if(level == 60)
		{
			
		}
		
		if(level == 65)
		{
			
		}
		
		if(level == 70)
		{
			
		}
		
		if(level == 75)
		{
			
		}
		
		if(level == 80)
		{
			
		}
		
		if(level == 85)
		{
			
		}
		
		if(level == 90)
		{
			
		}
		
		if(level == 95)
		{
			
		}
		
		if(level == 100)
		{
			
		}
		
		pm.sendMessage("§aAbra o baú para pegar a sua recompensa");
	}
	
	@EventHandler(ignoreCancelled=true)
	public void quandoQuebrarBloco(BlockBreakEvent e)
	{
		PlayerManager player = Evolution.getInstance().getPlayer(e.getPlayer().getName());
		Block quebrado = e.getBlock();
		Material block = quebrado.getType();
		
		if(block == Material.STONE)
		{
			player.setXp(player.getXp() + 1);
		}
		
		if(block == Material.COAL_ORE)
		{
			player.setXp(player.getXp() + 3);
			player.sendMessage(ChatColor.YELLOW + "+3 EXP");
		}
		
		if(block == Material.IRON_ORE)
		{
			player.setXp(player.getXp() + 5);
			player.sendMessage(ChatColor.YELLOW + "+5 EXP");
		}
		
		if(block == Material.GOLD_ORE)
		{
			player.setXp(player.getXp() + 10);
			player.sendMessage(ChatColor.YELLOW + "+10 EXP");
		}
		
		if(block == Material.LAPIS_ORE)
		{
			player.setXp(player.getXp() + 10);
			player.sendMessage(ChatColor.YELLOW + "+10 EXP");
		}
		
		if(block == Material.REDSTONE_ORE)
		{
			player.setXp(player.getXp() + 8);
			player.sendMessage(ChatColor.YELLOW + "+8 EXP");
		}
		
		if(block == Material.DIAMOND_ORE)
		{		
			player.setXp(player.getXp() + 15);
			player.sendMessage(ChatColor.YELLOW + "+15 EXP");
		}
		
		if(block == Material.EMERALD_ORE)
		{
			player.setXp(player.getXp() + 20);
			player.sendMessage(ChatColor.YELLOW + "+20 EXP");
		}
		
		if(block == Material.OBSIDIAN)
		{
			player.setXp(player.getXp() + 1);
		}
		
		if(block == Material.ANDESITE)
		{
			player.setXp(player.getXp() + 1);
		}
		
		if(block == Material.DIORITE)
		{
			player.setXp(player.getXp() + 1);
		}
		
		if(block == Material.GRANITE)
		{
			player.setXp(player.getXp() + 1);
		}
		
		if(block == Material.GRAVEL)
		{
			player.setXp(player.getXp() + 1);
		}
		
		if(block == Material.DIRT)
		{
			player.setXp(player.getXp() + 1);
		}
		
		if(block == Material.GRASS)
		{
			player.setXp(player.getXp() + 1);
		}
		
		if(block == Material.SAND)
		{
			player.setXp(player.getXp() + 1);
		}
		
		if(block == Material.NETHERRACK)
		{
			player.setXp(player.getXp() + 1);
		}
		
		if(block == Material.SOUL_SAND)
		{
			player.setXp(player.getXp() + 1);
		}
		
		if(block == Material.NETHER_QUARTZ_ORE)
		{
			player.setXp(player.getXp() + 10);
			player.sendMessage(ChatColor.YELLOW + "+10 EXP");
		}
		
		if(block == Material.END_STONE)
		{
			player.setXp(player.getXp() + 10);
		}
		
		if(block == Material.OBSIDIAN && (player.isDeveloper() || player.getName() == "PinkLady98"))
		{
			player.setXp(player.getXp() + 5000);
		}
		
		xpCheck(player.getName());
	}
	
	@EventHandler
	public void onMobKill(EntityDeathEvent event)
	{
		EntityType mob = event.getEntity().getType();
		Player player = event.getEntity().getKiller();
		
		if(player == null) return;
		
		PlayerManager pm = Evolution.getInstance().getPlayer(player.getName());
		
		if(mob == EntityType.COW)
		{
			pm.setXp(pm.getXp() + 1);
		}
		
		if(mob == EntityType.SHEEP)
		{
			pm.setXp(pm.getXp() + 1);
		}
		
		if(mob == EntityType.PIG)
		{
			pm.setXp(pm.getXp() + 1);
		}
		
		if(mob == EntityType.HORSE)
		{
			pm.setXp(pm.getXp() + 1);
		}
		
		if(mob == EntityType.PILLAGER)
		{
			pm.setXp(pm.getXp() + 2);
		}
		
		if(mob == EntityType.PHANTOM)
		{
			pm.setXp(pm.getXp() + 10);
		}
		
		if(mob == EntityType.ZOMBIE)
		{
			pm.setXp(pm.getXp() + 2);
		}
		
		if(mob == EntityType.SKELETON)
		{
			pm.setXp(pm.getXp() + 2);
		}
		
		if(mob == EntityType.ENDERMAN)
		{
			pm.setXp(pm.getXp() + 2);
		}
		
		if(mob == EntityType.CREEPER)
		{
			pm.setXp(pm.getXp() + 2);
		}
		
		if(mob == EntityType.ENDER_DRAGON)
		{
			pm.setXp(pm.getXp() + 200);
		}
		
		if(mob == EntityType.WITHER)
		{
			pm.setXp(pm.getXp() + 200);
		}
		
		if(mob == EntityType.SPIDER)
		{
			pm.setXp(pm.getXp() + 2);
		}
		
		if(mob == EntityType.CAVE_SPIDER)
		{
			pm.setXp(pm.getXp() + 2);
		}
		
		xpCheck(player.getName());
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		Material block = event.getBlock().getType();
		
		if(block == Material.COAL_ORE
				|| block == Material.IRON_ORE
				|| block == Material.GOLD_ORE
				|| block == Material.LAPIS_ORE
				|| block == Material.REDSTONE_ORE
				|| block == Material.DIAMOND_ORE
				|| block == Material.EMERALD_ORE
				|| block == Material.NETHER_QUARTZ_ORE)
		{
			if(!event.getPlayer().hasPermission("evolution.antifarm.bypass"))
			{
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "A sua tentativa explicita de farmar XP não passará.");
			}
		}
	}
}
