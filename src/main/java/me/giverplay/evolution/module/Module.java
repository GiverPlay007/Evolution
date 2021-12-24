package me.giverplay.evolution.module;

import me.giverplay.evolution.Evolution;

import java.util.logging.Logger;

public abstract class Module {

  protected final Evolution evolution;
  protected final String name;

  private boolean isEnabled;

  public Module(Evolution evolution, String name) {
    this.evolution = evolution;
    this.name = name;
  }

  public final void enable() {
    evolution.getLogger().info("Enabling module " + name);
    isEnabled = true;
  }

  public final void disable() {
    evolution.getLogger().info("Disabling module " + name);
    isEnabled = false;
  }

  protected abstract void onEnable();

  protected abstract void onDisable();

  public final String getName() {
    return name;
  }

  public final boolean isEnabled() {
    return isEnabled;
  }

  public Logger getLogger() {
    return evolution.getLogger();
  }
}
