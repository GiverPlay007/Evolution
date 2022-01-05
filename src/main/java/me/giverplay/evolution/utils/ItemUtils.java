package me.giverplay.evolution.utils;

import org.bukkit.Material;
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
    ItemMeta meta = item.getItemMeta();

    if(glow) {
      item.addUnsafeEnchantment(
        item.getType() != Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK,
        10
      );
      meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    } else {
      item.removeEnchantment(item.getType() != Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK);
      meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
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
}
