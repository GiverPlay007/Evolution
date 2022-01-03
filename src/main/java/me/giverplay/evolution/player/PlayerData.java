package me.giverplay.evolution.player;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import me.giverplay.evolution.module.EvolutionModule;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerData extends YamlConfiguration {

  private final File file;
  private final UUID uuid;

  public PlayerData(File playersFolder, UUID uuid) {
    this.uuid = uuid;
    this.file = new File(playersFolder, uuid.toString() + ".yml");
  }

  public String getNickname() {
    return getString("Player.Nickname");
  }

  public void setNickname(String nickname) {
    set("Player.Nickname", nickname);
  }

  public long getRegisteredAt() {
    return getLong("Player.RegisteredAt");
  }

  public void setRegisteredAt(long time) {
    set("Player.RegisteredAt", time);
  }

  public boolean save() {
    try {
      save(file);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean load() {
    try {
      load(file);
      return true;
    } catch (IOException | InvalidConfigurationException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean exists() {
    return file.exists();
  }

  public ConfigurationSection getNode(EvolutionModule module) {
    return getConfigurationSection(module.getName());
  }

  public ConfigurationSection createNode(EvolutionModule module) {
    return createSection(module.getName());
  }
}
