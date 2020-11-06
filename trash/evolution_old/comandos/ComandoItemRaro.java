package me.giverplay.evolution_old.comandos;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.giverplay.evolution_old.api.comando.Comando;
import me.giverplay.evolution_old.api.comando.ComandoType;

public class ComandoItemRaro extends Comando
{
	public ComandoItemRaro()
	{
		super("itemraro", ComandoType.PLAYER, "/itemraro [<create> <quantidade> <id>]");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(args.length == 0)
		{
			// MENU
			return;
		}
		
		if(!sender.hasPermission("evolution.admin"))
		{
			sender.sendMessage("§cUtilize apenas /itemraro");
			return;
		}
		
		if(args[0].equalsIgnoreCase("create"))
		{
			if(args.length < 3)
			{
				sender.sendMessage(getUsage());
				return;
			}
			
			Player player = (Player) sender;
			ItemStack item = player.getInventory().getItemInMainHand();
			
			if(item == null || item.getType() == Material.AIR)
			{
				player.sendMessage("§cÉ necessário ter um item de verdade para tornar ele raro :3");
				return;
			}
		}
		else
		{
			sender.sendMessage("§cSub-comando inválido");
		}
	}
}
