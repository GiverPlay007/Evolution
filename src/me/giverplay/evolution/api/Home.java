package me.giverplay.evolution.api;

import org.bukkit.Location;

public class Home
{
	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;
	private String world;
	private String homeName;
	
	public Home(Location l)
	{
		setWorld(l.getWorld().getName());
		setX(l.getX());
		setY(l.getY());
		setZ(l.getZ());
		setYaw(l.getYaw());
		setPitch(l.getPitch());
	}

	public String getWorld()
	{
		return world;
	}

	public void setWorld(String w)
	{
		this.world = w;
	}

	public float getPitch()
	{
		return pitch;
	}

	public void setPitch(float pitch)
	{
		this.pitch = pitch;
	}


	public float getYaw()
	{
		return yaw;
	}

	public void setYaw(float yaw)
	{
		this.yaw = yaw;
	}

	public double getZ()
	{
		return z;
	}

	public void setZ(double z)
	{
		this.z = z;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}
	
	public String getHomeName()
	{
		return homeName;
	}

	public void setHomeName(String homeName)
	{
		this.homeName = homeName;
	}
}
