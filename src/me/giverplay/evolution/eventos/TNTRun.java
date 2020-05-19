package me.giverplay.evolution.eventos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.giverplay.evolution.Variaveis;

public class TNTRun
{
	public static void init(){
		acontecendo = false;
		started = false;
		inicio = null;
		fim = null;
		participantes = new ArrayList<>();
	}
	
	public static ArrayList<Player> participantes;

	public static boolean started;
	public static boolean acontecendo;
	
	public static Block inicio;
	public static Block fim;
	
	public static Location getCamarote()
	{
		return Variaveis.configs.getLocation("eventos.tntrun.camarote");
	}

	public static Location getStaff()
	{
		return Variaveis.configs.getLocation("eventos.tntrun.staff");
	}

	public static Location getInicio()
	{
		return Variaveis.configs.getLocation("eventos.tntrun.inicio");
	}

	public static void add(Player p)
	{
		p.teleport(getInicio());
		participantes.add(p);

		for(Player pl : participantes)
		{
			pl.sendMessage("§a" + p.getName() + " entrou!");
		}
	}

	public static void setBase()
	{
		int xMaior, xMenor, zMaior, zMenor;
		
		xMenor = (inicio.getX() < fim.getX() ? inicio.getX() : fim.getX());
		xMaior = (inicio.getX() > fim.getX() ? inicio.getX() : fim.getX());
		zMenor = (inicio.getZ() < fim.getZ() ? inicio.getZ() : fim.getZ());
		zMaior = (inicio.getZ() > fim.getZ() ? inicio.getZ() : fim.getZ());
		
		Variaveis.configs.set("eventos.tntrun.world", inicio.getWorld().getName());
		Variaveis.configs.set("eventos.tntrun.base.x", xMenor);
		Variaveis.configs.set("eventos.tntrun.base.y", inicio.getY());
		Variaveis.configs.set("eventos.tntrun.base.z", zMenor);
		Variaveis.configs.set("eventos.tntrun.base.x2", xMaior);
		Variaveis.configs.set("eventos.tntrun.base.y2", fim.getY());
		Variaveis.configs.set("eventos.tntrun.base.z2", zMaior);
		Variaveis.configs.saveConfig();
	}

	public static void setCamarote(Location loc)
	{
		Variaveis.configs.setLocation("eventos.tntrun.camarote", loc);
		Variaveis.configs.saveConfig();
	}

	public static void setStaff(Location loc)
	{
		Variaveis.configs.setLocation("eventos.tntrun.staff", loc);
		Variaveis.configs.saveConfig();
	}

	public static void setInicio(Location loc)
	{
		Variaveis.configs.setLocation("eventos.tntrun.inicio", loc);
		Variaveis.configs.saveConfig();
	}
	
	public static void reset()
	{
		World w = Bukkit.getWorld(Variaveis.configs.getString("eventos.tntrun.world"));
		
		int x1 = Variaveis.configs.getInt("eventos.tntrun.base.x");
		int x2 = Variaveis.configs.getInt("eventos.tntrun.base.x2");
		int yb = Variaveis.configs.getInt("eventos.tntrun.base.y");
		int z1 = Variaveis.configs.getInt("eventos.tntrun.base.z");
		int z2 = Variaveis.configs.getInt("eventos.tntrun.base.z2");
		
		// TNT
		for (int x = x1; x <= x2; x++)
			for (int z = z1; z <= z2; z++)
			  w.getBlockAt(x, yb, z).setType(Material.TNT);
		
		// GRAVEL
		for (int x = x1; x <= x2; x++)
			for (int z = z1; z <= z2; z++)
			  w.getBlockAt(x, yb + 1, z).setType(Material.GRAVEL);
		
		// PLATE
		for (int x = x1; x <= x2; x++)
			for (int z = z1; z <= z2; z++)
			  w.getBlockAt(x, yb + 2, z).setType(Material.STONE_PRESSURE_PLATE);
	}
}
