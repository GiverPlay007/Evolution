package me.giverplay.evolution.handlers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.giverplay.evolution.api.EvolutionAPI;
import me.giverplay.evolution.api.manager.PlayerManager;

public class ListenerSigns implements Listener 
{
	@EventHandler
	public void onSignCreate(SignChangeEvent event)
	{
		if((event.getLine(0).toLowerCase().contains("[evolution]")))
		{
			if(!EvolutionAPI.getPlayer(event.getPlayer().getName()).isAdmin())
			{
				event.setCancelled(true);
				event.getPlayer().sendMessage("§cSem permissao para isso");
				return;
			}
			
			if(event.getLine(1).toLowerCase().contains("spawn")){
				event.setLine(0, "§e[Evolution]");
				event.setLine(1, "§e§lSpawn");
				event.setLine(2, " ");
				event.setLine(3, "§aTeleportar");
			}
		}
	}

	@EventHandler
	public void onSignClick(PlayerInteractEvent event)
	{
		if(!event.hasBlock()) return;

		Block block = event.getClickedBlock();

		if(!(block.getType() == Material.BIRCH_WALL_SIGN)) return;

		Sign sign = (Sign) block.getState();
		PlayerManager player = EvolutionAPI.getPlayer(event.getPlayer().getName());

		if(!(sign.getLine(0).contains("§e[Evolution]"))) return;
		
		if(sign.getLine(1) == "§e§lSpawn"){
			player.getPlayer().chat("/spawn");
			return;
		}
	}

	@EventHandler
	public void onSignBreak(BlockBreakEvent event)
	{
		if(event.getBlock().getType() == Material.BIRCH_SIGN || event.getBlock().getType() == Material.BIRCH_WALL_SIGN)
		{
			Sign sign = (Sign) event.getBlock().getState();

			if(sign.getLine(0).toLowerCase().contains("[evolution]"))
			{
				if(!EvolutionAPI.getPlayer(event.getPlayer().getName()).isAdmin())
				{
					event.setCancelled(true);
					event.getPlayer().sendMessage("§cSem permissão para isso");
				}
			}
		}
	}
	
	@EventHandler
	public void onSignChangeColored(SignChangeEvent event)
	{
		if(!event.getPlayer().hasPermission("evolution.placacolorida")) return;
		
		for(int i = 0; i < event.getLines().length; i++) event.setLine(i, event.getLine(i).replace("&", "§"));
	}
}