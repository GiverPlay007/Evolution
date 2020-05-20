package me.giverplay.evolution.comandos;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.giverplay.evolution.api.Menus;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;

@SuppressWarnings("deprecation")
public class ComandoReparar extends Comando
{
	public ComandoReparar()
	{
		super("reparar", ComandoType.PLAYER, "/reparar");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		ItemStack item = player.getInventory().getItemInMainHand();
		
		if(item.getType() == Material.AIR)
		{
			player.sendMessage("ßc… necess·rio colocar o item que deseja reparar em sua m„o!");
			return;
		}
		
		if(item.getDurability() == 0)
		{
			player.sendMessage("ßcEste item n„o precisa ser reparado!");
			return;
		}
		
		player.openInventory(Menus.repararMenu(player));
	}
}
