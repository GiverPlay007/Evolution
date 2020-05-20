package me.giverplay.evolution.api;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.entity.Player;

import me.giverplay.evolution.Evolution;

public class PlayerWarp
{
	private Location loc;
	private String warpname;
	private String owner;
	
	public PlayerWarp(Location l, String name, String o)
	{
		loc = l;
		warpname = name;
		owner = o;
	}

	public String getWarpName()
	{
		return warpname;
	}
	
	public String getOwner()
	{
		return owner;
	}
	
	public Location getLocation()
	{
		return loc;
	}
	
	public void saveWarp()
	{
		Evolution.getInstance().getWarpsConfig().getConfig().set(owner + "." + warpname + ".loc", loc);
		Evolution.getInstance().getWarpsConfig().saveConfig();
	}
	
	public void deleteWarp()
	{
		Evolution.getInstance().getWarpsConfig().set(owner + "." + warpname, null);
		Evolution.getInstance().getWarpsConfig().saveConfig();
	}
	
	public void teleportWarp(Player player)
	{
		player.teleport(loc);
		player.playNote(loc, Instrument.FLUTE, Note.natural(1, Tone.D));
		player.sendMessage("§eTeleportado com sucesso!");
	}
}
