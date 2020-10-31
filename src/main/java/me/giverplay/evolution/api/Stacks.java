package me.giverplay.evolution.api;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("deprecation")
public class Stacks
{
	public static ItemStack add(Material m)
	{
		return new ItemStack(m);
	}
	
	public static ItemStack add(Material m, int quantidade)
	{
		return new ItemStack(m, quantidade);
	}
	
	public static ItemStack addIcon(Material m, int quantia, String name, String... lore)
	{
		for(int i = 0; i < lore.length; i++)
		{
			lore[i] = lore[i].replace("&", "§");
		}
		
		ItemStack item = new ItemStack(m, quantia);
		item = addGlow(item);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack addIcon(Material m, String name, String... lore)
	{
		for(int i = 0; i < lore.length; i++)
		{
			lore[i] = lore[i].replace("&", "§");
		}
		
		ItemStack item = new ItemStack(m);
		item = addGlow(item);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack addGlow(ItemStack i)
	{
		i.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		i.getItemMeta().addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		i.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		return i;
	}
	
	public static ItemStack add(Material m, int quantidade, int durabilidade)
	{
		ItemStack item = new ItemStack(m, quantidade);
		item.setDurability((short)durabilidade);
		ItemMeta meta = item.getItemMeta();
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome)
	{
		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add1(Material m, String nome, int quantidade)
	{
		ItemStack item = new ItemStack(m, quantidade);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add1(Material m, String nome, int quantidade, int durabilidade)
	{
		ItemStack item = new ItemStack(m, quantidade);
		item.setDurability((short)durabilidade);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, Enchantment ench, int level)
	{
		ItemStack item = new ItemStack(m);
		item.addUnsafeEnchantment(ench, level);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, Enchantment ench, int level, int quantidade)
	{
		ItemStack item = new ItemStack(m, quantidade);
		item.addUnsafeEnchantment(ench, level);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, int durability)
	{
		ItemStack item = new ItemStack(m);
		item.setDurability((short)durability);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, int durability, String[] lore)
	{
		ItemStack item = new ItemStack(m);
		item.setDurability((short)durability);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, int quantidade, String nada)
	{
		ItemStack item = new ItemStack(m);
		item.setAmount(quantidade);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, String[] lore, int durability)
	{
		ItemStack item = new ItemStack(m);
		item.setDurability((short)durability);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, String[] lore)
	{
		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, List<String> lore)
	{
		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, List<String> lore, int quantidade)
	{
		ItemStack item = new ItemStack(m);
		item.setAmount(quantidade);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, String[] lore, Enchantment enchant, int lvl)
	{
		ItemStack item = new ItemStack(m);
		item.addUnsafeEnchantment(enchant, lvl);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, String[] lore, Enchantment enchant1, int lvl1, Enchantment enchant2, int lvl2)
	{
		ItemStack item = new ItemStack(m);
		item.addUnsafeEnchantment(enchant1, lvl1);
		item.addUnsafeEnchantment(enchant2, lvl2);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, Enchantment enchant1, int lvl1, Enchantment enchant2, int lvl2)
	{
		ItemStack item = new ItemStack(m);
		item.addUnsafeEnchantment(enchant1, lvl1);
		item.addUnsafeEnchantment(enchant2, lvl2);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack add(Material m, String nome, String[] lore, Enchantment enchant1, int lvl1, Enchantment enchant2, int lvl2, Enchantment enchant3, int lvl3)
	{
		ItemStack item = new ItemStack(m);
		item.addUnsafeEnchantment(enchant1, lvl1);
		item.addUnsafeEnchantment(enchant2, lvl2);
		item.addUnsafeEnchantment(enchant3, lvl3);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack potion(PotionEffectType efeito, String nome, String[] lore)
	{
		ItemStack item = new ItemStack(Material.POTION);
		PotionMeta eta = (PotionMeta)item.getItemMeta();
		eta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 3600, 9), true);
		eta.setLore(Arrays.asList(lore));
		eta.setDisplayName(nome);
		item.setItemMeta(eta);
		return item;
	}
	
	public static ItemStack couro()
	{
		ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
		meta.setColor(Color.BLUE);
		meta.setDisplayName("§bArmadura de Couro");
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack couro(Color cor, String nome)
	{
		ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
		meta.setColor(cor);
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack outro(Material m, String nome, Enchantment enchant, int lvl)
	{
		ItemStack item = new ItemStack(m);
		item.addUnsafeEnchantment(enchant, lvl);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack outro(Material m, String nome, Enchantment enchant1, int lvl1, Enchantment enchant2, int lvl2)
	{
		ItemStack item = new ItemStack(m);
		item.addUnsafeEnchantment(enchant1, lvl1);
		item.addUnsafeEnchantment(enchant2, lvl2);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack outro(Material m, String nome, Enchantment enchant1, int lvl1, Enchantment enchant2, int lvl2, Enchantment enchant3, int lvl3)
	{
		ItemStack item = new ItemStack(m);
		item.addUnsafeEnchantment(enchant1, lvl1);
		item.addUnsafeEnchantment(enchant2, lvl2);
		item.addUnsafeEnchantment(enchant3, lvl3);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack cap(Material m, int durabilidade)
	{
		ItemStack item = new ItemStack(m);
		item.setDurability((short)durabilidade);
		return item;
	}
	
	public static ItemStack skull(String name)
	{
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(name);
		meta.setDisplayName(name);
		skull.setItemMeta(meta);
		
		return skull;
	}
	
	public static ItemStack getTokenReparo(int quantidade)
	{
		return add(Material.NAME_TAG, "§bToken de Reparo",
				Arrays.asList("§f ", "§aUtilize em /reparar", "§a "), quantidade);
	}
	
	public static Material getMaterial(Material item)
	{
		Material mat = Material.STICK;
		
		if((item == Material.DIAMOND_AXE)
				|| (item == Material.DIAMOND_BOOTS)
				|| (item == Material.DIAMOND_CHESTPLATE)
				|| (item == Material.DIAMOND_HELMET)
				|| (item == Material.DIAMOND_HOE)
				|| (item == Material.DIAMOND_HORSE_ARMOR)
				|| (item == Material.DIAMOND_LEGGINGS)
				|| (item == Material.DIAMOND_PICKAXE)
				|| (item == Material.DIAMOND_SHOVEL)
				|| (item == Material.DIAMOND_SWORD)
				)
		{
			return Material.DIAMOND;
		}
		
		if((item == Material.GOLDEN_AXE)
				|| (item == Material.GOLDEN_BOOTS)
				|| (item == Material.GOLDEN_CHESTPLATE)
				|| (item == Material.GOLDEN_HELMET)
				|| (item == Material.GOLDEN_HOE)
				|| (item == Material.GOLDEN_HORSE_ARMOR)
				|| (item == Material.GOLDEN_LEGGINGS)
				|| (item == Material.GOLDEN_PICKAXE)
				|| (item == Material.GOLDEN_SHOVEL)
				|| (item == Material.GOLDEN_SWORD)
				)
		{
			return Material.GOLD_INGOT;
		}
		
		if((item == Material.IRON_AXE)
				|| (item == Material.IRON_BOOTS)
				|| (item == Material.IRON_CHESTPLATE)
				|| (item == Material.IRON_HELMET)
				|| (item == Material.IRON_HOE)
				|| (item == Material.IRON_HORSE_ARMOR)
				|| (item == Material.IRON_LEGGINGS)
				|| (item == Material.IRON_PICKAXE)
				|| (item == Material.IRON_SHOVEL)
				|| (item == Material.IRON_SWORD)
				)
		{
			return Material.IRON_INGOT;
		}
		
		if((item == Material.LEATHER_BOOTS)
				|| (item == Material.LEATHER_CHESTPLATE)
				|| (item == Material.LEATHER_HELMET)
				|| (item == Material.LEATHER_LEGGINGS)
				)
		{
			return Material.LEATHER;
		}
		
		if((item == Material.STONE_AXE)
				|| (item == Material.STONE_HOE)
				|| (item == Material.STONE_SHOVEL)
				|| (item == Material.STONE_SWORD)
				)
		{
			return Material.COBBLESTONE;
		}
		
		return mat;
	}
	
}
