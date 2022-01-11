package me.giverplay.evolution.listeners;

import me.giverplay.evolution.module.modules.trash.TrashModule;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class TrashModuleListener implements Listener {

  private final TrashModule trashModule;

  public TrashModuleListener(TrashModule trashModule) {
    this.trashModule = trashModule;
  }

  @EventHandler(ignoreCancelled = true)
  public void onInteract(PlayerInteractEvent event) {
    Block block = event.getClickedBlock();

    if(block == null || !(block.getState() instanceof Sign sign)) return;

    if(sign.getLine(0).contains("[Lixeira]")) {
      event.setCancelled(true);
      event.getPlayer().openInventory(trashModule.getTrash());
    }
  }
}
