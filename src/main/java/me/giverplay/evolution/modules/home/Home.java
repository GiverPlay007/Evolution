package me.giverplay.evolution.modules.home;

import org.bukkit.Location;
import org.bukkit.World;

public class Home
{
  private Location loc;
  private String name;
  
  public Home(Location loc, String name)
  {
    this.loc = loc.clone();
    this.name = name;
  }
  
  public Location getLocation()
  {
    return loc;
  }
  
  public String getName()
  {
    return name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setLocation(Location loc)
  {
    this.loc = loc;
  }
  
  public double getX()
  {
    return loc.getX();
  }
  
  public double getY()
  {
    return loc.getY();
  }
  
  public void setX(double x)
  {
    loc.setX(x);
  }
  
  public void setY(double y)
  {
    loc.setY(y);
  }
  
  public World getWorld()
  {
    return loc.getWorld();
  }
  
  public void setWorld(World world)
  {
    loc.setWorld(world);
  }
  
  public float getPitch()
  {
    return loc.getPitch();
  }
  
  public void setPitch(float pitch)
  {
    loc.setPitch(pitch);
  }
  
  public double getYaw()
  {
    return loc.getYaw();
  }
  
  public void setYaw(double yaw)
  {
    loc.setY(yaw);
  }
}
