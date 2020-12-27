package me.giverplay.evolution.modules;

public interface Module
{
  String getName();
  
  void enable();
  
  void disable();
  
  boolean isEnabled();

  default void setupCommands() { }

  default void registerEvents() { }

  default void unregisterEvents() { }

  default void toggleCommands(boolean enabled){ }
}
