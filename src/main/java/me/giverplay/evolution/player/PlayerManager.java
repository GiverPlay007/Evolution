package me.giverplay.evolution.player;

import me.giverplay.evolution.Evolution;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;

public class PlayerManager {

  private final HashMap<String, YamlConfiguration> dataCache = new HashMap<>();
  private final Evolution plugin;
  private final File playersFolder;

  public PlayerManager(Evolution plugin) {
    this.plugin = plugin;

    playersFolder = new File(plugin.getDataFolder(), "players");

    if(!playersFolder.exists()) {
      playersFolder.mkdirs();
    }
  }

  public YamlConfiguration getPlayerData(String playerName) {
    playerName = playerName.toLowerCase();

    if(dataCache.containsKey(playerName)) {
      return dataCache.get(playerName);
    }

    File playerFile = new File(playersFolder, playerName + ".yml");
    YamlConfiguration playerData = new YamlConfiguration();

    if(!playerFile.exists()) {
      plugin.getLogger().warning("Player data " + playerName + " does not exists");
      return playerData;
    }

    try {
      playerData.load(playerFile);
    } catch (IOException | InvalidConfigurationException e) {
      plugin.getLogger().log(Level.SEVERE, "Failed to load player data " + playerName, e);
      e.printStackTrace();
    }

    dataCache.put(playerName, playerData);
    return playerData;
  }
}
