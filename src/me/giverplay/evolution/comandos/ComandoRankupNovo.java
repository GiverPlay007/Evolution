package me.giverplay.evolution.comandos;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.api.RankNovo;
import me.giverplay.evolution.api.comando.Comando;
import me.giverplay.evolution.api.comando.ComandoType;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ComandoRankupNovo extends Comando
{
	private Evolution plugin;
	
	public ComandoRankupNovo()
	{
		super("rankup", ComandoType.PLAYER, "/rankup");
		plugin = Evolution.getInstance();
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		PlayerManager player = plugin.getPlayer(sender.getName());
		
		if(player.getRank().isLastRank())
		{
			player.sendMessage("§eVocê já está no último rank, meu consagrado");
			return;
		}
		
		final RankNovo proximo = plugin.getRanks().get(player.getRank().getNextRank());
		
		if(player.getLevel() < proximo.getMinLevel())
		{
			player.sendMessage("\n§cVocê não possui o seguinte requisito: §fNível " + proximo.getMinLevel() + "\n");
			return;
		}
		
		List<ItemStack> req = proximo.getCost();
		String erro = null;
		
		if(!req.isEmpty())
		{
			for(ItemStack stack : req)
			{
				if(!player.getPlayer().getInventory().contains(stack, stack.getAmount()))
				{
					if(erro == null)
					{
						erro = "\n§cVocê não possui o seguinte requisito: ";
					}
					
					erro += format(stack);
					erro += "; ";
				}
			}
		}
		
		if(erro != null)
		{
			player.sendMessage(erro);
			return;
		}
		
		if(!req.isEmpty())
		{
			for(ItemStack stack : req)
			{
				player.getPlayer().getInventory().removeItem(stack);
			}
		}
		
		player.setRank(proximo);
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			plugin.sendAction(p, "§6" + player.getName() + " §eupou para " + proximo.getPrefix());
		}
	}
	
	private String format(ItemStack item)
	{
		return item.getAmount() + "x " + (item.getType() == Material.DIAMOND ? "diamante" : item.getType() == Material.NETHERITE_INGOT ? "barra de Netherite" : item.getType().name().toLowerCase());
	}
}
