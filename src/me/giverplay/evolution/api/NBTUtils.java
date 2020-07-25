package me.giverplay.evolution.api;

import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R1.NBTTagCompound;

public class NBTUtils
{
	public static ItemStack setNBTString(ItemStack item, String key, String value)
	{
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
		
		itemCompound.setString(key, value);
		nmsItem.setTag(itemCompound);
		
		ItemStack finalitem = CraftItemStack.asBukkitCopy(nmsItem);
		return finalitem;
	}
	
	public static ItemStack setNBTDouble(ItemStack item, String key, double value)
	{
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
		
		itemCompound.setDouble(key, value);
		nmsItem.setTag(itemCompound);
		
		ItemStack finalitem = CraftItemStack.asBukkitCopy(nmsItem);
		return finalitem;
	}
	
	public static ItemStack setNBTBoolean(ItemStack item, String key, Boolean value)
	{
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
		
		itemCompound.setBoolean(key, value);
		nmsItem.setTag(itemCompound);
		
		ItemStack finalitem = CraftItemStack.asBukkitCopy(nmsItem);
		return finalitem;
	}
	
	public static ItemStack setNBTInt(ItemStack item, String key, Integer value)
	{
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
		
		itemCompound.setInt(key, value);
		nmsItem.setTag(itemCompound);
		
		ItemStack finalitem = CraftItemStack.asBukkitCopy(nmsItem);
		return finalitem;
	}
	
	public static ItemStack setNBTLong(ItemStack item, String key, Long value)
	{
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
		
		itemCompound.setLong(key, value);
		nmsItem.setTag(itemCompound);
		
		ItemStack finalitem = CraftItemStack.asBukkitCopy(nmsItem);
		return finalitem;
	}
	
	public static ItemStack setNBTFloat(ItemStack item, String key, Float value)
	{
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
		
		itemCompound.setFloat(key, value);
		nmsItem.setTag(itemCompound);
		
		ItemStack finalitem = CraftItemStack.asBukkitCopy(nmsItem);
		return finalitem;
	}
	
	public static String getNBTString(ItemStack item, String key) 
	{
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
		
		if (itemCompound.hasKey(key)) 
		{
			return itemCompound.getString(key);
		}
		
		return null;
	}
	
	public static double getNBTDouble(ItemStack item, String key, double def) 
	{
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
		
		if (itemCompound.hasKey(key)) 
		{
			return itemCompound.getDouble(key);
		}
		
		return def;
	}
	
	public static boolean getNBTBoolean(ItemStack item, String key) 
	{
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
		
		if (itemCompound.hasKey(key)) 
		{
			return itemCompound.getBoolean(key);
		}
		
		return false;
	}
	
	public static int getNBTInt(ItemStack item, String key, int def) 
	{
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
		
		if (itemCompound.hasKey(key)) 
		{
			return itemCompound.getInt(key);
		}
		
		return def;
	}
	
	public static Long getNBTLong(ItemStack item, String key, long def) 
	{
		net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
		
		if (itemCompound.hasKey(key)) 
		{
			return itemCompound.getLong(key);
		}
		
		return def;
	}
}
