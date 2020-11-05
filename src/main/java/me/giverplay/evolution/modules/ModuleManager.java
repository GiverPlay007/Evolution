package me.giverplay.evolution.modules;

import java.util.ArrayList;
import me.giverplay.evolution.EvolutionAPI;
import me.giverplay.evolution.data.YamlConfig;
import me.giverplay.evolution.modules.chat.ChatModule;
import me.giverplay.evolution.modules.home.HomeModule;

public class ModuleManager
{
  private final ArrayList<Module> modules = new ArrayList<>();
  private final EvolutionAPI plugin;
  private final YamlConfig config;
  
  private HomeModule homes;
  private ChatModule chat;
  
  public ModuleManager(EvolutionAPI api)
  {
    this.plugin = api;
    this.config = plugin.getConfig();
  }
  
  public void enable()
  {
    plugin.getLogger().info("Iniciando carregamento de modulos.");
    
    modules.add(homes = new HomeModule(plugin));
    modules.add(chat = new ChatModule(plugin));
    
    modules.forEach(this::checkEnable);
    plugin.getLogger().info("Carregamento de modulos finalizado.");
  }
  
  public void disable()
  {
    plugin.getLogger().info("Desabilitando modulos.");
    modules.forEach(Module::disable);
    plugin.getLogger().info("Modulos desabilitados.");
  }
  
  private void checkEnable(Module module)
  {
    if(!config.getBoolean("modules." + module.getName()))
    {
      plugin.getLogger().warning("Modulo " + module.getName() + " não está ativado em settings.yml");
      return;
    }
    
    module.enable();
  }
  
  public HomeModule getHomesModule()
  {
    return homes;
  }
  
  public ChatModule getChatModule()
  {
    return chat;
  }
}
