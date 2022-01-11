package me.giverplay.evolution.module.modules.trash;

import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.command.commands.TrashCommand;
import me.giverplay.evolution.listeners.TrashModuleListener;
import me.giverplay.evolution.module.EvolutionModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class TrashModule extends EvolutionModule {

  private TrashModuleListener listener;
  private Inventory trash;
  private BukkitTask task;

  public TrashModule(Evolution evolution) {
    super(evolution, "Trash");
  }

  @Override
  protected void onEnable() {
    trash = Bukkit.createInventory(null, 54, ChatColor.DARK_RED + "Lixeira");
    listener = new TrashModuleListener(this);
    startTask();

    evolution.getCommandHandler().registerCommand(new TrashCommand(this));
    Bukkit.getPluginManager().registerEvents(listener, evolution);
  }

  @Override
  protected void onDisable() {
    evolution.getCommandHandler().unregisterCommand("trash");

    if(task != null) {
      task.cancel();
      task = null;
    }

    if(listener != null) {
      HandlerList.unregisterAll(listener);
      listener = null;
    }

    trash = null;
  }

  private void startTask() {
    task = Bukkit.getScheduler().runTaskTimer(evolution, this::clearTrash, 0L, 20L);
  }

  private void clearTrash() {
    ItemStack item;
    int next;

    for(int i = 53; i >= 0; i--) {
      item = trash.getItem(i);

      if(item != null && !item.getType().equals(Material.AIR)) {
        trash.setItem(i, new ItemStack(Material.AIR));
        next = i + 9;

        if(next <= 53) {
          trash.setItem(next, item);
        }
      }
    }
  }

  public Inventory getTrash() {
    return trash;
  }
}
