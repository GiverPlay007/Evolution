package me.giverplay.evolution.module;

import com.google.common.base.Charsets;
import me.giverplay.evolution.Evolution;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class EvolutionModule {

  protected final Evolution evolution;
  protected final String name;

  private final String configName;
  private final File configFile;

  private FileConfiguration config;
  private boolean isEnabled;

  public EvolutionModule(Evolution evolution, String name) {
    this.evolution = evolution;
    this.name = name;
    this.configName = name + ".yml";
    this.configFile = new File(evolution.getDataFolder(), configName);
  }

  protected abstract void onEnable();

  protected abstract void onDisable();

  public final void enable() {
    evolution.getLogger().info("Enabling module " + name);
    isEnabled = true;
    onEnable();
  }

  public final void disable() {
    evolution.getLogger().info("Disabling module " + name);
    isEnabled = false;
    onDisable();
  }

  public void saveConfig() {
    try {
      getConfig().save(configFile);
    } catch (IOException e) {
      getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
    }
  }

  public void saveDefaultConfig() {
    if (!configFile.exists()) {
      evolution.saveResource(configName, false);
    }
  }

  public void reloadConfig() {
    config = YamlConfiguration.loadConfiguration(configFile);

    final InputStream defaults = evolution.getResource(configName);

    if (defaults == null) return;

    config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defaults, Charsets.UTF_8)));
  }

  public final String getName() {
    return name;
  }

  public FileConfiguration getConfig() {
    return config;
  }

  public final boolean isEnabled() {
    return isEnabled;
  }

  public Evolution getEvolution() {
    return evolution;
  }

  public Logger getLogger() {
    return evolution.getLogger();
  }
}
