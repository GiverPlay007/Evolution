package me.giverplay.evolution.comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;
import net.md_5.bungee.api.ChatColor;

public class ComandoPlugins extends Comando
{
	public ComandoPlugins()
	{
		super("plugins", ComandoType.GERAL, "/plugins");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(!(sender instanceof Player)){
			// org.bukkit.command.defaults.PluginsCommand
			StringBuilder pluginList = new StringBuilder();
			Plugin[] plugins = Bukkit.getPluginManager().getPlugins(); byte b; int i;
			Plugin[] arrayOfPlugin;
			for (i = (arrayOfPlugin = plugins).length, b = 0; b < i; ) { Plugin plugin = arrayOfPlugin[b];
			if (pluginList.length() > 0) {
				pluginList.append(ChatColor.WHITE);
				pluginList.append(", ");
			} 
			
			pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
			pluginList.append(plugin.getDescription().getName());
			b++; }
			
			sender.sendMessage("(" + plugins.length + "): " + pluginList.toString());
			return;
		}

		// Entendo que esta forma não é otimizada, portanto, será melhorada em alguma atualização futura

		Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
		
		Inventory inv = Bukkit.createInventory(null, 54, "§0§lPlugins (" + plugins.length + ")");
		
		for(int i = 0; i < plugins.length; i++){
			PluginDescriptionFile desc = plugins[i].getDescription();
			
			String name = desc.getName();
			String d = desc.getDescription();
			String w = desc.getWebsite();
			List<String> authors = desc.getAuthors();
			ItemStack book = new ItemStack(Material.BOOK);
			ItemMeta meta = book.getItemMeta();
			meta.setDisplayName("§a§l" + name);
			
			ArrayList<String> lore = new ArrayList<>();
			List<String> ndesc = new ArrayList<String>();
			
			if(d != null){
				int cont = 0;
				String line = "§f  ";
				
				for(int n = 0; n <d.split("").length; n++){
					if(cont == 45){
						cont = 0;
						ndesc.add(line);
						line = "§f  ";
					}
					cont++;
					line += d.charAt(n);
				}
				
				ndesc.add(line);
			}
			
			lore.add("§7----------------");
			
			if(d == null){
				lore.add("§cDescrição: §fIndisponível");
			} else{
				lore.add("§cDescrição:");
				
				for(String line : ndesc){
					lore.add(line);
				}
			}
			
			lore.add("§cWebsite: §f" + (w != null ? w : "Indisponível"));
			lore.add("§cAutores:");
			for(String s : authors){
				lore.add(" §f- " + s);
			}
			
			lore.add("§7----------------");
			
			meta.setLore(lore);
			book.setItemMeta(meta);
			
			inv.addItem(book);
		}
		
		((Player) sender).openInventory(inv);
	}
}
