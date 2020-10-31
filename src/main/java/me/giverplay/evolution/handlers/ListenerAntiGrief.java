package me.giverplay.evolution.handlers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import br.net.fabiozumbi12.RedProtect.Bukkit.RedProtect;
import br.net.fabiozumbi12.RedProtect.Bukkit.Region;
import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ListenerAntiGrief implements Listener
{
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		Block block = event.getBlock();
		
		if(block.getWorld().getName() != "world") return;
		
		Player player = event.getPlayer();
		PlayerManager pm = Evolution.getInstance().getPlayer(player.getName());
		Region r = RedProtect.get().rm.getTopRegion(block.getLocation());
		
		if(r == null && !pm.isAdmin() && !pm.isDeveloper())
		{
			event.setCancelled(true);
			player.sendMessage("�aVocê não pode alterar o ambiente fora de seu terreno neste mundo");
			return;
		}
		
		if(block.getY() < 64)
		{
			event.setCancelled(true);
			player.sendMessage("§aVocê não pode alterar abaixo da camada <camadaQueVaiSerLimitada>");
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		Block block = event.getBlock();
		
		if(block.getWorld().getName() != "world") return;
		
		PlayerManager player = Evolution.getInstance().getPlayer(event.getPlayer().getName());
		
		if(player.isAdmin() || player.isDeveloper()) return;
		
		if(RedProtect.get().rm.getTopRegion(block.getLocation()) != null) return;
		
		if(!isPlaca(block))
		{ // Se o bloco que colocou for diferente de placa (pra criar RP)
			event.setCancelled(true);
			player.sendMessage("§aVocê está fora de seu terreno");
			return;
		}
		
		Sign sign = (Sign) block.getState();
		
		if(!sign.getLine(0).toLowerCase().contains("[rp]"))
		{
			event.setCancelled(true);
			player.sendMessage("§cAltere somente o seu terreno, amigo");
		}
	}
	
	public static boolean isPlaca(Block block)
	{
		Material type = block.getType();
		
		return (type == Material.ACACIA_SIGN || type == Material.ACACIA_WALL_SIGN
				    || type == Material.BIRCH_SIGN || type == Material.BIRCH_WALL_SIGN
				    || type == Material.OAK_SIGN || type == Material.OAK_WALL_SIGN
				    || type == Material.DARK_OAK_SIGN || type == Material.DARK_OAK_WALL_SIGN
				    || type == Material.JUNGLE_SIGN || type == Material.JUNGLE_WALL_SIGN
				    || type == Material.SPRUCE_SIGN || type == Material.SPRUCE_WALL_SIGN);
	}
}
