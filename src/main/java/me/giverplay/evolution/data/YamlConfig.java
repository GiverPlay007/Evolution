package me.giverplay.evolution.data;

import com.google.common.base.Charsets;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.giverplay.evolution.Evolution;
import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class YamlConfig
{
  private final Evolution plugin;
  private final String fileName;
  private final String folderName;
  
  private YamlConfiguration config;
  
  private File save;
  
  public YamlConfig(String name)
  {
    this(name, null);
  }
  
  public YamlConfig(String name, String folder)
  {
    Validate.notNull(name, "O nome do arquivo não pode ser nulo");
    
    this.plugin = Evolution.getInstance();
    this.fileName = name.endsWith(".yml") ? name : name + ".yml";
    this.folderName = folder;
    
    createSave();
  }
  
  private void createSave()
  {
    File folder = folderName != null ? new File(plugin.getDataFolder(), folderName) : plugin.getDataFolder();
    save = new File(folder, fileName);
  }
  
  public void saveDefault(boolean replace)
  {
    InputStream in = plugin.getResource(fileName);
  
    if(in == null)
    {
      plugin.getLogger().warning("Config " + fileName + " não existe dentro da jar.");
      return;
    }
  
    try
    {
      FileUtils.copyInputStreamToFile(in, save);
    }
    catch(IOException e)
    {
      plugin.getLogger().warning("Falha ao salvar config padrão de " + fileName);
      e.printStackTrace();
    }
  }
  
  public void reload()
  {
    config = YamlConfiguration.loadConfiguration(save);
    
    InputStream in = plugin.getResource(fileName);
    
    if(in == null)
    {
      return;
    }
    
    config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(in, Charsets.UTF_8)));
  }
  
  public void save()
  {
    try
    {
     config.save(save);
    }
    catch(IOException e)
    {
      plugin.getLogger().warning("Falha ao salvar a config " + fileName);
      e.printStackTrace();
    }
  }
  
  public Set<String> getKeys(boolean deep)
  {
    return config.getKeys(deep);
  }
  
  public Map<String, Object> getValues(boolean deep)
  {
    return config.getValues(deep);
  }
  
  public boolean contains(String path)
  {
    return config.contains(path);
  }
  
  public boolean contains(String path, boolean ignoreDefault)
  {
    return config.contains(path, ignoreDefault);
  }
  
  public boolean isSet(String path)
  {
    return config.isSet(path);
  }
  
  public String getCurrentPath()
  {
    return config.getCurrentPath();
  }
  
  public String getName()
  {
    return config.getName();
  }
  
  public Configuration getRoot()
  {
    return config.getRoot();
  }
  
  public ConfigurationSection getDefaultSection()
  {
    return config.getDefaultSection();
  }
  
  public void set(String path, Object value)
  {
    config.set(path, value);
  }
  
  public Object get(String path)
  {
    return config.get(path);
  }
  
  public Object get(String path, Object def)
  {
    return config.get(path, def);
  }
  
  public ConfigurationSection createSection(String path)
  {
    return config.createSection(path);
  }
  
  public ConfigurationSection createSection(String path, Map<?, ?> map)
  {
    return config.createSection(path, map);
  }
  
  public String getString(String path)
  {
    return config.getString(path);
  }
  
  public String getString(String path, String def)
  {
    return config.getString(path, def);
  }
  
  public boolean isString(String path)
  {
    return config.isString(path);
  }
  
  public int getInt(String path)
  {
    return config.getInt(path);
  }
  
  public int getInt(String path, int def)
  {
    return config.getInt(path, def);
  }
  
  public boolean isInt(String path)
  {
    return config.isInt(path);
  }
  
  public boolean getBoolean(String path)
  {
    return config.getBoolean(path);
  }
  
  public boolean getBoolean(String path, boolean def)
  {
    return config.getBoolean(path, def);
  }
  
  public boolean isBoolean(String path)
  {
    return config.isBoolean(path);
  }
  
  public double getDouble(String path)
  {
    return config.getDouble(path);
  }
  
  public double getDouble(String path, double def)
  {
    return config.getDouble(path, def);
  }
  
  public boolean isDouble(String path)
  {
    return config.isDouble(path);
  }
  
  public long getLong(String path)
  {
    return config.getLong(path);
  }
  
  public long getLong(String path, long def)
  {
    return config.getLong(path, def);
  }
  
  public boolean isLong(String path)
  {
    return config.isLong(path);
  }
  
  public List<?> getList(String path)
  {
    return config.getList(path);
  }
  
  public List<?> getList(String path, List<?> def)
  {
    return config.getList(path, def);
  }
  
  public boolean isList(String path)
  {
    return config.isList(path);
  }
  
  public List<String> getStringList(String path)
  {
    return config.getStringList(path);
  }
  
  public List<Integer> getIntegerList(String path)
  {
    return config.getIntegerList(path);
  }
  
  public List<Boolean> getBooleanList(String path)
  {
    return config.getBooleanList(path);
  }
  
  public List<Double> getDoubleList(String path)
  {
    return config.getDoubleList(path);
  }
  
  public List<Float> getFloatList(String path)
  {
    return config.getFloatList(path);
  }
  
  public List<Long> getLongList(String path)
  {
    return config.getLongList(path);
  }
  
  public List<Byte> getByteList(String path)
  {
    return config.getByteList(path);
  }
  
  public List<Character> getCharacterList(String path)
  {
    return config.getCharacterList(path);
  }
  
  public List<Short> getShortList(String path)
  {
    return config.getShortList(path);
  }
  
  public List<Map<?, ?>> getMapList(String path)
  {
    return config.getMapList(path);
  }
  
  public <T> T getObject(String path, Class<T> clazz)
  {
    return config.getObject(path, clazz);
  }
  
  public <T> T getObject(String path, Class<T> clazz, T def)
  {
    return config.getObject(path, clazz, def);
  }
  
  public <T extends ConfigurationSerializable> T getSerializable(String path, Class<T> clazz)
  {
    return config.getSerializable(path, clazz);
  }
  
  public <T extends ConfigurationSerializable> T getSerializable(String path, Class<T> clazz, T def)
  {
    return config.getSerializable(path, clazz, def);
  }
  
  public Vector getVector(String path)
  {
    return config.getVector(path);
  }
  
  public Vector getVector(String path, Vector def)
  {
    return config.getVector(path, def);
  }
  
  public boolean isVector(String path)
  {
    return config.isVector(path);
  }
  
  public OfflinePlayer getOfflinePlayer(String path)
  {
    return config.getOfflinePlayer(path);
  }
  
  public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def)
  {
    return config.getOfflinePlayer(path, def);
  }
  
  public boolean isOfflinePlayer(String path)
  {
    return config.isOfflinePlayer(path);
  }
  
  public ItemStack getItemStack(String path)
  {
    return config.getItemStack(path);
  }
  
  public ItemStack getItemStack(String path, ItemStack def)
  {
    return config.getItemStack(path, def);
  }
  
  public boolean isItemStack(String path)
  {
    return config.isItemStack(path);
  }
  
  public Color getColor(String path)
  {
    return config.getColor(path);
  }
  
  public Color getColor(String path, Color def)
  {
    return config.getColor(path, def);
  }
  
  public boolean isColor(String path)
  {
    return config.isColor(path);
  }
  
  public Location getLocation(String path)
  {
    return config.getLocation(path);
  }
  
  public Location getLocation(String path, Location def)
  {
    return config.getLocation(path, def);
  }
  
  public boolean isLocation(String path)
  {
    return config.isLocation(path);
  }
  
  public ConfigurationSection getConfigurationSection(String path)
  {
    return config.getConfigurationSection(path);
  }
  
  public boolean isConfigurationSection(String path)
  {
    return config.isConfigurationSection(path);
  }
  
  public static String createPath(ConfigurationSection section, String key)
  {
    return MemorySection.createPath(section, key);
  }
  
  public static String createPath(ConfigurationSection section, String key, ConfigurationSection relativeTo)
  {
    return MemorySection.createPath(section, key, relativeTo);
  }
}
