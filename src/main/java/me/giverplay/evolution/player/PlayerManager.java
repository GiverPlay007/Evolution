package me.giverplay.evolution.player;

import me.giverplay.evolution.Evolution;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

  private final HashMap<UUID, PlayerData> dataCache = new HashMap<>();
  private final Evolution evolution;
  private final File playersFolder;

  public PlayerManager(Evolution evolution) {
    this.evolution = evolution;

    playersFolder = new File(evolution.getDataFolder(), "players");

    if(!playersFolder.exists()) {
      playersFolder.mkdirs();
    }
  }

  public void savePlayerData(OfflinePlayer offlinePlayer) {
    savePlayerData(offlinePlayer.getUniqueId());
  }

  public void savePlayerData(Player player) {
    savePlayerData(player.getUniqueId());
  }

  public void savePlayerData(UUID uuid) {
    if(!dataCache.containsKey(uuid)) {
      evolution.getLogger().warning("Could save player data " + uuid + " because it's not loaded");
      return;
    }

    PlayerData data = dataCache.get(uuid);

    if(!data.save()) {
      evolution.getLogger().severe("Failed to save player data " + data);
    }
  }

  public PlayerData getPlayerData(OfflinePlayer offlinePlayer) {
    return getPlayerData(offlinePlayer.getUniqueId());
  }

  public PlayerData getPlayerData(Player player) {
    return getPlayerData(player.getUniqueId());
  }

  public PlayerData getPlayerData(UUID uuid) {
    if(dataCache.containsKey(uuid)) {
      return dataCache.get(uuid);
    }

    PlayerData data = new PlayerData(playersFolder, uuid);

    if(!data.exists()) {
      evolution.getLogger().warning("Player data " + uuid + " does not exists");
      return data;
    }

    if(!data.load()) {
      evolution.getLogger().severe("Failed to load player data " + uuid);
    }

    dataCache.put(uuid, data);
    return data;
  }

  private File getPlayerFile(String uuid) {
    return new File(playersFolder, uuid + ".yml");
  }

  public boolean isPlayerRegistered(Player player) {
    return getPlayerFile(player.getUniqueId().toString()).exists();
  }

  public void registerPlayer(Player player) {
    UUID uuid = player.getUniqueId();
    PlayerData data = new PlayerData(playersFolder, uuid);

    data.setRegisteredAt(System.currentTimeMillis());
    data.setNickname(player.getName());

    if(!data.save()) {
      evolution.getLogger().severe("Failed to save player data " + uuid);
    }

    dataCache.put(uuid, data);
  }

  public void removeFromCache(Player player) {
    dataCache.remove(player.getUniqueId());
  }
}
