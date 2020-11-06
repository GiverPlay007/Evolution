package me.giverplay.evolution_old.comandos;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.giverplay.evolution_old.api.Menus;
import me.giverplay.evolution_old.api.comando.Comando;
import me.giverplay.evolution_old.api.comando.ComandoType;

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
			player.sendMessage("§cÉ necessário colocar o item que deseja reparar em sua mão!");
			return;
		}
		
		if(item.getDurability() == 0)
		{
			player.sendMessage("§cEste item não precisa ser reparado!");
			return;
		}
		
		player.openInventory(Menus.repararMenu(player));
	}
}
