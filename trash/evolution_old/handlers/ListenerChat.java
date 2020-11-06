package me.giverplay.evolution_old.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.giverplay.evolution_old.Evolution;
import me.giverplay.evolution_old.api.manager.PlayerManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ListenerChat implements Listener
{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChat(AsyncPlayerChatEvent event)
	{
		PlayerManager pm = Evolution.getInstance().getPlayer(event.getPlayer().getName());
		
		int nivel = pm.getLevel();
		
		String rank = pm.getRank().getPrefix();
		
		String message = event.getMessage();
		String cor = (pm.getName().equals("GiverPlay007") ? "§a": (pm.getName().equals("PinkLady98") ? "§d" : "§f"));
    
		String format = "§e[" + nivel + "] §r" + PermissionsEx.getUser(pm.getPlayer()).getPrefix().replace("&", "§")
		               + rank + " " + cor + "%1$s§e: %2$s";
		
		event.setFormat(format);
		event.setMessage((pm.getPlayer().hasPermission("evolution.cores") ? message.replace("&", "§") : message));
	} 
}