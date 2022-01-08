package me.giverplay.evolution.module.modules.kit;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Kit;
import com.earth2me.essentials.MetaItemStack;
import com.earth2me.essentials.User;
import com.earth2me.essentials.commands.Commandkit;
import com.earth2me.essentials.commands.NoChargeException;
import com.earth2me.essentials.utils.DateUtil;
import dev.arantes.inventorymenulib.buttons.ClickAction;
import dev.arantes.inventorymenulib.buttons.ItemButton;
import dev.arantes.inventorymenulib.menus.InventoryGUI;
import dev.arantes.inventorymenulib.utils.InventorySize;
import me.giverplay.evolution.Evolution;
import me.giverplay.evolution.command.commands.KitCommand;
import me.giverplay.evolution.module.EvolutionModule;
import me.giverplay.evolution.utils.ItemUtils;
import net.ess3.provider.SerializationProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import static com.earth2me.essentials.I18n.tl;

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

    evolution.getCommandHandler().registerCommand(new KitCommand(this));
  }

  @Override
  protected void onDisable() {
    evolution.getCommandHandler().unregisterCommand("kit");
  }

  public void openKitCategoriesMenu(Player player) {
    categoriesMenu.show(player);
  }

  private void loadMenus() {
    categoriesMenu = new InventoryGUI("Kits", InventorySize.THREE_ROWS);
    categoriesMenu.setDefaultAllCancel(true);

    ConfigurationSection ranksSection = getConfig().getConfigurationSection("RankKits");
    ranksCategoryMenu = new InventoryGUI("Kits dos Ranks", InventorySize.SIX_ROWS);
    ranksCategoryMenu.setDefaultCancel(true);
    setCategoryButton(ranksSection, ranksCategoryMenu);
    setKitButtons(ranksSection, ranksCategoryMenu);

    ConfigurationSection vipsSection = getConfig().getConfigurationSection("VipKits");
    vipsCategoryMenu = new InventoryGUI("Kits VIP", InventorySize.SIX_ROWS);
    vipsCategoryMenu.setDefaultCancel(true);
    setCategoryButton(vipsSection, vipsCategoryMenu);
    setKitButtons(vipsSection, vipsCategoryMenu);

    ConfigurationSection specialSection = getConfig().getConfigurationSection("SpecialKits");
    specialCategoryMenu = new InventoryGUI("Kits Especiais", InventorySize.SIX_ROWS);
    specialCategoryMenu.setDefaultCancel(true);
    setCategoryButton(specialSection, specialCategoryMenu);
    setKitButtons(specialSection, specialCategoryMenu);
  }

  private void setCategoryButton(ConfigurationSection section, InventoryGUI target) {
    ItemStack item = ItemUtils.fromSection(section);
    ItemButton button = new ItemButton(item).setGlow(true);
    categoriesMenu.setButton(section.getInt("Slot"), button);
    button.addAction(ClickType.LEFT, (event) -> target.show((Player) event.getWhoClicked()));
  }

  private void setKitButtons(ConfigurationSection section, InventoryGUI category) {
    for(String key : section.getKeys(false)) {
      if(key.equals("Slot") || key.equals("Item") || key.equals("Lore") || key.equals("Title")) continue;

      setKitButton(section.getConfigurationSection(key), category);
    }
  }

  private void setKitButton(ConfigurationSection section, InventoryGUI category) {
    ItemStack item = ItemUtils.fromSection(section);
    ItemButton button = new ItemButton(item).setGlow(true);
    category.setButton(section.getInt("Slot"), button);
    button.addAction(ClickType.LEFT, (event) -> previewKit((Player) event.getWhoClicked(), section.getName()));
  }

  public void previewKit(Player player, String kitName) {

    Essentials essentials = evolution.getEssentials();
    Kit kit;

    try {
      kit = new Kit(kitName.toLowerCase(), essentials);
    } catch (Exception e) {
      player.sendMessage(ChatColor.RED + "Esse kit não existe!");
      return;
    }

    InventoryGUI inventory = new InventoryGUI("Conteúdo do kit " + kitName, InventorySize.SIX_ROWS);
    inventory.setDefaultCancel(true);

    List<ItemStack> items = getKitItems(kit);

    for (int index = 0; index < items.size(); index++) {
      ItemButton button = new ItemButton(items.get(index));
      inventory.setButton(index, button);
    }

    ClickAction action = null;
    Material material;
    String title;
    String[] lore;

    try {
      User user = essentials.getUser(player);
      kit.checkPerms(user);

      long nextUse = kit.getNextUse(user);

      if(nextUse == 0) {
        material = Material.CHEST_MINECART;
        title = "&aPegar o kit";
        lore = new String[]{" ", " &aClique aqui para", " &cpegar o kit", " "};
        action = (event) -> claim(kitName, player);
      } else {
        material = Material.HOPPER_MINECART;
        title = "&cAguarde";
        lore = new String[] {" ", " &cVocê poderá pegar o kit", " &cnovamente em " + DateUtil.formatDateDiff(nextUse), " "};
      }
    } catch (Exception e) {
      material = Material.BARRIER;
      title = "&cSem permissão";
      lore = new String[] {" ", "&cVocê não pode pegar", " &cesse kit", " "};
    }

    ItemButton claim = new ItemButton(material, title, lore);

    if(action != null) claim.addAction(ClickType.LEFT, action);

    inventory.setButton(49, claim);
    inventory.show(player);
  }

  private void claim(String kitName, Player player) {
    try {
      Essentials essentials = evolution.getEssentials();
      Kit kit = new Kit(kitName.toLowerCase(), essentials);
      kit.expandItems(essentials.getUser(player));
      player.sendMessage(tl("kitReceive", kitName));
    } catch (Exception e) {
      getLogger().log(Level.SEVERE, "Failed to deliver kit %s to %s".formatted(kitName, player.getName()), e);
      player.sendMessage(ChatColor.RED + "Ocorreu um erro ao tentar pegar o kit, por favor, comunique um administrador.");
    }
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
