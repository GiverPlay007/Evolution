package me.giverplay.evolution.api.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import me.giverplay.evolution.Evolution;

public class ConfigManager
{
  public static ConfigManager cnf = new ConfigManager("configuracao");
  private Evolution plugin;
  private String name;
  private File file;
  private YamlConfiguration config;
  
  public ConfigManager(String nome)
  {
  	plugin = Evolution.getInstance();
  	
    setName(nome + ".yml");
    reloadConfig();
  }	  
	  
  public String getName()
  {
    return this.name;
  }
	  
  public void setName(String name)
  {
    this.name = name;
  }
	  
  public File getFile()
  {
    return this.file;
  }
	  
  public YamlConfiguration getConfig()
  {
    return this.config;
  }
	  
  public void saveConfig()
  {
    try
    {
      getConfig().save(getFile());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
	  
  public void saveDefault()
  {
    getConfig().options().copyDefaults(true);
  }
  
  public void saveDefaultConfig()
  {
    if (!existeConfig())
    {
      plugin.saveResource(getName(), false);
      reloadConfig();
    }
  }
	  
  public void reloadConfig()
  {
    this.file = new File(plugin.getDataFolder(), getName());
    this.config = YamlConfiguration.loadConfiguration(getFile());
  }
	  
  public void deleteConfig()
  {
    getFile().delete();
  }
  
  public boolean existeConfig()
  {
    return getFile().exists();
  }
	  
  public String getString(String path)
  {
    return getConfig().getString(path);
  }
  
  public int getInt(String path)
  {
    return getConfig().getInt(path);
  }
  
  public boolean getBoolean(String path)
  {
    return getConfig().getBoolean(path);
  }
  
  public double getDouble(String path)
  {
    return getConfig().getDouble(path);
  }
  
  public List<?> getList(String path)
  {
    return getConfig().getList(path);
  }
	  
  public long getLong(String path)
  {
    return getConfig().getLong(path);
  }
  
  public float getFloat(String path)
  {
    return getConfig().getInt(path);
  }
	  
  public Object get(String path)
  {
    return getConfig().get(path);
  }
  
  public List<Map<?, ?>> getMapList(String path)
  {
    return getConfig().getMapList(path);
  }
  
  public List<String> getStringList(String path)
  {
    return getConfig().getStringList(path);
  }
  public List<Integer> getIntegerList(String path)
  {
    return getConfig().getIntegerList(path);
  }
  
  public boolean contains(String path)
  {
    return getConfig().contains(path);
  }
  
  public void set(String path, Object value)
  {
    getConfig().set(path, value);
  }
  
  public void setString(String path, String value)
  {
    getConfig().set(path, value);
  }
  
  public void setLocation(String path, Location loc)
  {
  	config.set(path, loc);
  }
  
  public Location getLocation(String path)
  {
  	return config.getLocation(path);
  }
}
