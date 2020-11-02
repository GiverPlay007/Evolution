package me.giverplay.evolution_old.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.giverplay.evolution_old.Evolution;
import me.giverplay.evolution_old.api.manager.PlayerManager;

public class ListenerEntityDeathBroadcast implements Listener
{
	@EventHandler
	public void quandoMatar(EntityDeathEvent event)
	{
		if((event.getEntity().getKiller() == null)) return;
		
		EntityType type = event.getEntity().getType();
		PlayerManager player = Evolution.getInstance().getPlayer(event.getEntity().getKiller().getName());
		
		if(type == EntityType.ENDER_DRAGON)
		{
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("§6" + player.getName() + " §ematou um Dragão, agora recebeu a recompensa!");
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("§a + R$200.00");
			Bukkit.broadcastMessage("§a + 200 EXP");
			Bukkit.broadcastMessage(" ");
			
			player.giveMoney(200D);
		}
		
		if(type == EntityType.WITHER)
		{
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("§6" + player.getName() + " §ematou um Wither Boss, agora recebeu a recompensa!");
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("§a + R$200.00");
			Bukkit.broadcastMessage("§a + 200 EXP");
			Bukkit.broadcastMessage(" ");
			
			player.giveMoney(200);
		}
	}
}
