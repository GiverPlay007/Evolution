package me.giverplay.evolution.module.modules.kit;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Kit;
import com.earth2me.essentials.MetaItemStack;
import dev.arantes.inventorymenulib.buttons.ItemButton;
import dev.arantes.inventorymenulib.menus.InventoryGUI;
import dev.arantes.inventorymenulib.utils.InventorySize;
import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.module.EvolutionModule;
import me.giverplay.evolution.utils.ItemUtils;
import net.ess3.provider.SerializationProvider;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class KitModule extends EvolutionModule {

  private InventoryGUI categoriesMenu;
  private InventoryGUI ranksCategoryMenu;
  private InventoryGUI vipsCategoryMenu;
  private InventoryGUI specialCategoryMenu;

  public KitModule(Evolution evolution) {
    super(evolution, "Kit");
    evolution.requireEssentials();
  }

  @Override
  protected void onEnable() {
    saveDefaultConfig();
    reloadConfig();
    loadMenus();
  }

  @Override
  protected void onDisable() {

  }

  public void openKitCategoriesMenu(Player player) {
    categoriesMenu.show(player);
  }

  private void loadMenus() {
    categoriesMenu = new InventoryGUI("Kits", InventorySize.THREE_ROWS);
    categoriesMenu.setDefaultAllCancel(true);

    ConfigurationSection ranksSection = getConfig().getConfigurationSection("RankKits");
    ranksCategoryMenu = new InventoryGUI("Kits dos Ranks", InventorySize.SIX_ROWS);
    setCategoryButton(ranksSection, 13, ranksCategoryMenu);

    ConfigurationSection vipsSection = getConfig().getConfigurationSection("VipKits");
    vipsCategoryMenu = new InventoryGUI("Kits VIP", InventorySize.SIX_ROWS);
    setCategoryButton(vipsSection, 15, vipsCategoryMenu);

    ConfigurationSection specialSection = getConfig().getConfigurationSection("SpecialSKits");
    specialCategoryMenu = new InventoryGUI("Kits Especiais", InventorySize.SIX_ROWS);
    setCategoryButton(specialSection, 17, specialCategoryMenu);
  }

  private void setCategoryButton(ConfigurationSection section, int slot, InventoryGUI target) {
    ItemStack item = ItemUtils.fromSection(section);
    ItemButton button = new ItemButton(item).setGlow(true);
    categoriesMenu.setButton(slot, button);
    button.addAction(ClickType.LEFT, (event) -> target.show((Player) event.getWhoClicked()));
  }

  public List<ItemStack> getKitItems(String kitName) {
    Essentials essentials = evolution.getEssentials();
    Kit kit;

    try {
      kit = new Kit(kitName, essentials);
    } catch (Exception e) {
      getLogger().log(Level.SEVERE, "Failed to load Essentials kit: " + kitName, e);
      return new ArrayList<>();
    }

    return getKitItems(kit);
  }

  // https://github.com/EssentialsX/Essentials/blob/2.x/Essentials/src/main/java/com/earth2me/essentials/Kit.java
  public List<ItemStack> getKitItems(Kit kit) {
    List<ItemStack> result = new ArrayList<>();
    List<String> items;

    try {
      items = kit.getItems();
    } catch (Exception e) {
      getLogger().log(Level.SEVERE, "Failed to load items from Essentials kit: " + kit.getName(), e);
      return result;
    }

    Essentials essentials = evolution.getEssentials();
    SerializationProvider serializationProvider = essentials.getSerializationProvider();

    for (String kitItem : items) {
      ItemStack stack;

      if (kitItem.startsWith("@")) {
        if (serializationProvider == null) {
          getLogger().log(Level.WARNING, "Cannot deserialize item '%s' of kit %s".formatted(kitItem, kit.getName()));
          continue;
        }

        stack = serializationProvider.deserializeItem(Base64Coder.decodeLines(kitItem.substring(1)));
      } else {
        String[] parts = kitItem.split(" +");
        ItemStack parseStack = null;

        try {
          parseStack = essentials.getItemDb().get(parts[0], parts.length > 1 ? Integer.parseInt(parts[1]) : 1);
        } catch (Exception e) {
          getLogger().info("Item " + kitItem + " is invalid at kit " + kit.getName());
          continue;
        }

        if (parseStack.getType() == Material.AIR) continue;

        MetaItemStack metaStack = new MetaItemStack(parseStack);

        if (parts.length > 2) {
          try {
            metaStack.parseStringMeta(null, true, parts, 2, essentials);
          } catch (Exception e) {
            getLogger().info("Failed to parse item meta for item " + kitItem + " at kit " + kit.getName());
          }
        }

        stack = metaStack.getItemStack();
      }

      result.add(stack);
    }

    return result;
  }
}
