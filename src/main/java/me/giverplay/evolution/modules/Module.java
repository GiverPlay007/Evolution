package me.giverplay.evolution.modules;

public interface Module
{
  String getName();
  
  void enable();
  
  void disable();
  
  boolean isEnabled();
}
