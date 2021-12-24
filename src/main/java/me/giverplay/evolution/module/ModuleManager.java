package me.giverplay.evolution.module;

import me.giverplay.evolution.Evolution;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

  private final Map<String, Module> modules = new HashMap<>();
  private final Evolution evolution;

  public ModuleManager(Evolution evolution) {
    this.evolution = evolution;
  }

  public void enableAll() {
    modules.values().forEach(Module::enable);
  }

  public void disableAll() {
    modules.values().forEach(Module::disable);
  }

  public void registerModule(Module module) {
    if(modules.containsKey(module.getName())) {
      throw new IllegalArgumentException("Module " + module.getName() + " is already registered");
    }

    modules.put(module.getName(), module);
  }

  public Module getModule(String moduleName) {
    return modules.get(moduleName);
  }
}
