/*
 * This file is part of InventoryMenuLib, licensed under the MIT License
 *
 * Copyright (c) Gustavo Arantes (me@arantes.dev)
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.arantes.inventorymenulib.buttons;

import me.giverplay.evolution.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemButton {

  private final Map<ClickType, ClickAction> actions;

  private ItemStack item;
  private ClickAction defaultAction;

  public ItemButton() {
    this.actions = new HashMap<>();
  }

  public ItemButton(ItemStack item) {
    this.item = item;
    this.actions = new HashMap<>();
  }

  public ItemButton(Material material, String name, String... lore) {
    this(material, 1, name, lore);
  }

  public ItemButton(Material material, int amount, String name, String... lore) {
    this.actions = new HashMap<>();
    setItem(material, amount, name, lore);
  }

  public ItemButton setItem(Material material, String name, String... lore) {
    setItem(material, 1, name, lore);
    return this;
  }

  public ItemButton setItem(Material material, int amount, String name, String... lore) {
    this.item = ItemUtils.parse(material, amount, name, lore);
    return this;
  }

  public ItemButton setDamage(short damage) {
    ItemUtils.setDamage(item, damage);
    return this;
  }

  @Deprecated()
  public ItemButton setData(MaterialData data) {
    item.setData(data);
    return this;
  }

  public ItemButton setName(String name) {
    ItemUtils.setDisplayName(item, name);
    return this;
  }

  public ItemButton setLore(String... lines) {
    ItemUtils.setLore(item, lines);
    return this;
  }

  public ItemButton setLore(List<String> lines) {
    ItemUtils.setLore(item, lines);
    return this;
  }

  public ItemButton setLore(int pos, String line) {
    ItemUtils.setLore(item, pos, line);
    return this;
  }

  public ItemButton addLore(String... lines) {
    ItemUtils.addLore(item, lines);
    return this;
  }

  public ItemButton setGlow(boolean glow) {
    ItemUtils.setGlow(item, glow);
    return this;
  }

  public ItemButton addAction(ClickType type, ClickAction action) {
    actions.put(type, action);
    return this;
  }

  public ClickAction getDefaultAction() {
    return defaultAction;
  }

  public ItemButton setDefaultAction(ClickAction action) {
    this.defaultAction = action;
    return this;
  }

  public ItemStack getItem() {
    return item;
  }

  public ClickAction getAction(ClickType type) {
    if(!actions.containsKey(type)) {
      if(defaultAction != null) {
        return defaultAction;
      }

      return null;
    }

    return actions.get(type);
  }
}
