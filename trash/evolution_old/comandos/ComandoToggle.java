package me.giverplay.evolution_old.comandos;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.giverplay.evolution_old.api.Stacks;
import me.giverplay.evolution_old.api.comando.Comando;
import me.giverplay.evolution_old.api.comando.ComandoType;

public class ComandoToggle extends Comando
{
	public ComandoToggle()
	{
		super("toggle", ComandoType.PLAYER, "/toggle");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Inventory inv = Bukkit.createInventory(null, 27, "§0§lConfigs");
		
		inv.setItem(10, Stacks.addIcon(Material.BOOK, "§a§lTell", " ", " &aClique para ativar ou", " &adesativar o tell", " "));
		inv.setItem(13, Stacks.addIcon(Material.WRITABLE_BOOK, "§a§lScoreboard", " ", " &aClique para ativar ou", " &adesativar a Scoreboard", " "));
		inv.setItem(16, Stacks.addIcon(Material.ENDER_PEARL, "§a§lTPA", " ", " &aClique para ativar ou", " &adesativar o TPA", " "));
		
		((Player) sender).openInventory(inv);
	}
}
