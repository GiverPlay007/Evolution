package me.giverplay.evolution.module.modules.trash;

import dev.arantes.inventorymenulib.utils.InventorySize;
import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.command.commands.TrashCommand;
import me.giverplay.evolution.module.EvolutionModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class TrashModule extends EvolutionModule {

  public TrashModule(Evolution evolution) {
    super(evolution, "Trash");
  }

  @Override
  protected void onEnable() {
    evolution.getCommandHandler().registerCommand(new TrashCommand(this));
  }

  @Override
  protected void onDisable() {
    evolution.getCommandHandler().unregisterCommand("trash");
  }

  public Inventory getTrash() {
    return Bukkit.createInventory(null, 54, ChatColor.DARK_RED + "Lixeira");
  }
}
