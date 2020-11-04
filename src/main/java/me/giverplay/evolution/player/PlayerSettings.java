package me.giverplay.evolution.player;

public class PlayerSettings
{
  public static final int TELEPORT = 0;
  public static final int PRIVATE_CHAT = 1;
  public static final int GLOBAL_CHAT = 2;
  public static final int LOCAL_CHAT = 3;
  public static final int SCOREBOARD = 4;
  
  private final boolean[] flags;
  
  public PlayerSettings()
  {
    flags = new boolean[5];
  }
  
  public boolean isTeleportEnabled()
  {
    return flags[TELEPORT];
  }
  
  public void setTeleportEnabled(boolean enabled)
  {
    flags[TELEPORT] = enabled;
  }
  
  public boolean isPrivateChatEnabled()
  {
    return flags[PRIVATE_CHAT];
  }
  
  public void setPrivateChatEnabled(boolean enabled)
  {
    flags[PRIVATE_CHAT] = enabled;
  }
  
  public boolean isGlobalChatEnabled()
  {
    return flags[GLOBAL_CHAT];
  }
  
  public void setGlobalChatEnabled(boolean enabled)
  {
    flags[GLOBAL_CHAT] = enabled;
  }
  
  public boolean isLocalChatEnabled()
  {
    return flags[LOCAL_CHAT];
  }
  
  public void setLocalChatEnabled(boolean enabled)
  {
    flags[LOCAL_CHAT] = enabled;
  }
  
  public boolean isScoreboardEnabled()
  {
    return flags[SCOREBOARD];
  }
  
  public void setScoreboardEnabled(boolean enabled)
  {
    flags[SCOREBOARD] = enabled;
  }
}
