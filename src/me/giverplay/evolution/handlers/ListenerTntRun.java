package me.giverplay.evolution.handlers;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.giverplay.evolution.Variaveis;
import me.giverplay.evolution.api.manager.PlayerManager;
import me.giverplay.evolution.eventos.TNTRun;

@SuppressWarnings("deprecation")
public class ListenerTntRun implements Listener
{
	@EventHandler(ignoreCancelled = false)
	public void onSelection(PlayerInteractEvent event){
		if(!event.hasBlock() || !(event.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD) ) return;

		PlayerManager player = Variaveis.playersHashMap.get(event.getPlayer().getName());

		if(!player.isDeveloper()) return;
		
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			TNTRun.inicio = event.getClickedBlock();
			player.sendMessage("§eInicio da base selecionado");
		}

		if(event.getAction() == Action.LEFT_CLICK_BLOCK){
			TNTRun.inicio = event.getClickedBlock();
			player.sendMessage("§eFim da base selecionado");
		}
	}
}
