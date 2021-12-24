package me.giverplay.evolution.module;

import me.giverplay.evolution.Evolution;

public abstract class Module {

  protected final Evolution evolution;
  protected final String name;

  private boolean isEnabled;

  public Module(Evolution plugin, String name) {
    this.evolution = plugin;
    this.name = name;
  }

  public void enable() {
    evolution.getLogger().info("Enabling module " + name);
    isEnabled = true;
  }

  public void disable() {
    evolution.getLogger().info("Disabling module " + name);
    isEnabled = false;
  }

  protected abstract void onEnable();

  protected abstract void onDisable();

  public String getName() {
    return name;
  }

  public boolean isEnabled() {
    return isEnabled;
  }
}
