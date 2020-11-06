package me.giverplay.evolution.player;

public class EvolutionPlayer
{
  private final PlayerSettings settings;
  private final String name;
  
  public EvolutionPlayer(String name)
  {
    this.name = name;
    this.settings = new PlayerSettings();
  }
  
  public String getName()
  {
    return name;
  }
  
  public PlayerSettings getPlayerSettings()
  {
    return settings;
  }
}
