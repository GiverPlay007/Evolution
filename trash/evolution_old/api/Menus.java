package me.giverplay.evolution_old.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.giverplay.evolution_old.Evolution;

public class Menus
{
	public static Inventory lixeira()
	{
		return Bukkit.createInventory(null, 54, "§4Lixeira");
	}
	
	public static Inventory repararMenu(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 27, "§0§lReparar");
		
		ItemStack item = player.getInventory().getItemInMainHand();
		
		List<String> lore = Arrays.asList("§f -",
				"§aCusto: §f1 Ticket de reparo", "§f -");
		
		ItemStack anvil = Stacks.add(Material.ANVIL, "§a§lReparar Item", lore);
		
		inv.setItem(10, item);
		inv.setItem(14, anvil);
		inv.setItem(16, Stacks.add(Material.BARRIER, "§c§lCancelar"));
		
		return inv;
	}
	
	public static Inventory playerWarps(String player)
	{
		Inventory inv = Bukkit.createInventory(null, 36, "Warps de " + player);
		
		ArrayList<PlayerWarp> warps = Evolution.getInstance().getWarps(player);
		
		int count = 0;
		
		if(warps != null)
		{
			for(PlayerWarp warp : warps)
			{
				inv.setItem(count, Stacks.add(Material.WRITABLE_BOOK, "§b" + warp.getWarpName(), Arrays.asList("§aClick para teleportar")));
				count++;
			}
		}
		
		return inv;
	}
}
