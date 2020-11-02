package me.giverplay.evolution_old.comandos;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.giverplay.evolution_old.Evolution;
import me.giverplay.evolution_old.api.comando.Comando;
import me.giverplay.evolution_old.api.comando.ComandoType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ComandoTPA extends Comando
{
	public static HashMap<Player, Player> tparequest = new HashMap<>();
	
	private static ArrayList<Player> delay = new ArrayList<>();
	
	private Evolution plugin;
	
	public ComandoTPA()
	{
		super("tpa", ComandoType.PLAYER, "/tpa [jogador]");
		plugin = Evolution.getInstance();
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (args.length == 0)
		{
			sender.sendMessage(getUsage());
			return;
		} 
		else if (args.length > 1)
		{
			sender.sendMessage(getUsage());
			return;
		}
		else
		{
			Player target = Bukkit.getPlayer(args[0]);
			
			if (checkPlayers(sender, target))
			{
				return;
			} 
			else
			{
				if (delay.contains((Player) sender))
				{
					sender.sendMessage("§cAguarde 20 segundos para usar este comando.");
					return;
				}
				
				if(!plugin.getPlayer(target.getName()).isTPAEnabled())
				{
					sender.sendMessage("§cJogador com TPA desativado.");
					return;
				}
				
				if(!plugin.getPlayer(sender.getName()).isTPAEnabled())
				{
					plugin.getPlayer(sender.getName()).setTPAEnabled(true);
					return;
				}
				
				sender.sendMessage("§eVocê enviou um pedido de teleporte a §6" + target.getName());
				target.sendMessage(" ");
				target.sendMessage(" §e" + sender.getName() + " §7deseja ir até você.");
				
				TextComponent tpamsg = new TextComponent("§7Clique");
				BaseComponent[] hoveraceitar = new ComponentBuilder("§aAceitar pedido de teleporte de " + sender.getName())
						.create();
				BaseComponent[] hovernegar = new ComponentBuilder("§cNegar pedido de teleporte de " + sender.getName())
						.create();
				
				TextComponent aquiaceitar = new TextComponent(" §a§l§nAQUI§7 ");
				aquiaceitar.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoveraceitar));
				aquiaceitar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaceitar " + sender.getName()));
				
				TextComponent aquinegar = new TextComponent(" §c§l§nAQUI§7 ");
				aquinegar.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hovernegar));
				aquinegar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpanegar " + sender.getName()));
				
				TextComponent ou = new TextComponent("§7para aceitar ou");
				TextComponent ou2 = new TextComponent("§7para negar.");
				
				tpamsg.addExtra(aquiaceitar);
				tpamsg.addExtra(ou);
				tpamsg.addExtra(aquinegar);
				tpamsg.addExtra(ou2);
				
				target.spigot().sendMessage(tpamsg);
				
				target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
				target.sendMessage(" ");
				delay.add((Player) sender);
				tparequest.put((Player) sender, target);
				BukkitScheduler sh = Bukkit.getScheduler();
				
				sh.scheduleSyncDelayedTask(plugin, new Runnable()
				{
					@Override
					public void run()
					{
						if (tparequest.get((Player) sender) == target)
						{
							tparequest.remove((Player) sender);
							sender.sendMessage("§cO seu pedido de teleporte para " + target.getName() + " expirou!");
							target.sendMessage("§cO pedido de teleporte de " + sender.getName() + " expirou!");
						}
						delay.remove((Player) sender);
					}
				}, 20 * 20);
				
			}
		}
	}
	
	public static boolean checkPlayers(CommandSender sender, Player target)
	{
		if (target == null)
		{
			sender.sendMessage("§cJogador não encontrado! ");
			return true;
		}
		
		if (sender.getName().equalsIgnoreCase(target.getName()))
		{
			sender.sendMessage("§cNão pode ser você mesmo!");
			return true;
		}
		return false;
	}
}
