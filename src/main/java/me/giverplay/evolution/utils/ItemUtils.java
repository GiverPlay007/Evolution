package me.giverplay.evolution.utils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ItemUtils {
  private ItemUtils() { }

  public static ItemStack parse(Material material, int amount, String name, String... lore) {
    return parse(material, amount, name, Arrays.asList(lore));
  }

  public static ItemStack parse(Material material, int amount, String name, List<String> lore) {
    ItemStack item = new ItemStack(material, amount);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(ColorUtils.translate(name));
    item.setItemMeta(meta);

    return setLore(item, lore);
  }

  public static ItemStack setDisplayName(ItemStack item, String name) {
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(ColorUtils.translate(name));
    item.setItemMeta(meta);
    return item;
  }

  public static ItemStack setLore(ItemStack item, String... lore) {
    return setLore(item, Arrays.asList(lore));
  }

  public static ItemStack setLore(ItemStack item, List<String> lines) {
    ItemMeta meta = item.getItemMeta();
    meta.setLore(lines.stream().map(ColorUtils::translate).collect(Collectors.toList()));
    item.setItemMeta(meta);
    return item;
  }

  public static ItemStack setLore(ItemStack item, int pos, String line) {
    ItemMeta meta = item.getItemMeta();
    List<String> lorries = meta.hasLore() ? meta.getLore() : new ArrayList<>();

    if(lorries.get(pos) != null) {
      lorries.set(pos, ColorUtils.translate(line));

      meta.setLore(lorries);
      item.setItemMeta(meta);
    }

    return item;
  }

  public static ItemStack addLore(ItemStack item, String... lines) {
    ItemMeta meta = item.getItemMeta();
    List<String> lore = meta.getLore();

    lore.addAll(Arrays.stream(lines).map(ColorUtils::translate).collect(Collectors.toList()));

    meta.setLore(lore);
    item.setItemMeta(meta);

    return item;
  }

  public static ItemStack setGlow(ItemStack item, boolean glow) {
    ItemMeta meta;

    if(glow) {
      item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
      meta = item.getItemMeta();
      meta.addItemFlags(ItemFlag.values());
    } else {
      item.removeEnchantment(Enchantment.DURABILITY);
      meta = item.getItemMeta();
      meta.removeItemFlags(ItemFlag.values());
    }

    item.setItemMeta(meta);
    return item;
  }

  public static ItemStack setDamage(ItemStack item, short damage) {
    ItemMeta meta = item.getItemMeta();

    if(meta instanceof Damageable) {
      ((Damageable) meta).setDamage(damage);
    }

    item.setItemMeta(meta);
    return item;
  }

  public static ItemStack fromSection(ConfigurationSection section) {
    Material material = Material.getMaterial(section.getString("Item"));
    String name = section.getString("Title");
    List<String> lore = section.getStringList("Lore");

    return setLore(parse(material, 1, name), lore);
  }
}
