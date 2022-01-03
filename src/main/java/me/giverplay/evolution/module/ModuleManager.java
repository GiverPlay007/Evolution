package me.giverplay.evolution.module;

import me.giverplay.evolution.Evolution;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

  private final Map<String, EvolutionModule> modules = new HashMap<>();
  private final Evolution evolution;

  public ModuleManager(Evolution evolution) {
    this.evolution = evolution;
  }

  public void enableAll() {
    ConfigurationSection config = evolution.getConfig().getConfigurationSection("ModuleEnabled");

    for (EvolutionModule module : modules.values()) {
      if(config.getBoolean(module.name)) {
        module.enable();
      }
    }
  }

  public void disableAll() {
    modules.values().forEach(EvolutionModule::disable);
  }

  public void registerModule(EvolutionModule module) {
    if(modules.containsKey(module.getName())) {
      throw new IllegalArgumentException("Module " + module.getName() + " is already registered");
    }

    modules.put(module.getName(), module);
  }

  public EvolutionModule getModule(String moduleName) {
    return modules.get(moduleName);
  }
}
