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

package dev.arantes.inventorymenulib;

import com.google.common.collect.Maps;
import dev.arantes.inventorymenulib.buttons.ClickAction;
import dev.arantes.inventorymenulib.buttons.ItemButton;
import dev.arantes.inventorymenulib.menus.InventoryGUI;
import dev.arantes.inventorymenulib.menus.PaginatedGUI;
import me.giverplay.evolution.utils.ItemUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PaginatedGUIBuilder {

  private final Map<Integer, ItemButton> buttons = Maps.newHashMap();
  private final ItemButton[] hotbar = new ItemButton[9];

  private final char[] shape;

  private final String name;

  private List<ItemButton> content;

  private ClickAction contentDefaultAction;
  private ItemButton borderButton;
  private ItemStack nextPageItem;
  private ItemStack previousPageItem;

  private boolean defaultCancel = false;
  private boolean defaultAllCancel = false;

  public PaginatedGUIBuilder(String name, String shape) {
    this.name = name;
    this.shape = shape.toCharArray();

    Validate.isTrue(this.shape.length % 9 == 0, "Shape length must be a multiple of 9");

    if(this.shape.length > 45) {
      throw new IndexOutOfBoundsException("Maximum shape length is 45");
    }
  }

  public PaginatedGUIBuilder setBorder(Material material, int amount, String name, String... lore) {
    return setBorder(new ItemButton(material, amount, name, lore));
  }

  public PaginatedGUIBuilder setBorder(ItemButton button) {
    this.borderButton = button;
    return this;
  }

  public PaginatedGUIBuilder setNextPageItem(Material material, int amount, String name, String... lore) {
    return setNextPageItem(ItemUtils.parse(material, amount, name, lore));
  }

  public PaginatedGUIBuilder setNextPageItem(ItemStack button) {
    this.nextPageItem = button;
    return this;
  }

  public PaginatedGUIBuilder setPreviousPageItem(Material material, int amount, String name, String... lore) {
    return setPreviousPageItem(ItemUtils.parse(material, amount, name, lore));
  }

  public PaginatedGUIBuilder setPreviousPageItem(ItemStack button) {
    this.previousPageItem = button;
    return this;
  }

  public PaginatedGUIBuilder setHotbarButton(byte pos, ItemButton button) {
    if(pos > 8) {
      throw new IndexOutOfBoundsException();
    }

    hotbar[pos] = button;
    return this;
  }

  public PaginatedGUIBuilder setContent(List<ItemButton> content) {
    this.content = content;
    return this;
  }

  public PaginatedGUIBuilder setContent(ItemButton... content) {
    this.content = Arrays.asList(content);
    return this;
  }

  public PaginatedGUIBuilder setContentDefaultAction(ClickAction action) {
    this.contentDefaultAction = action;
    return this;
  }

  public boolean isDefaultCancel() {
    return defaultCancel;
  }

  public void setDefaultCancel(boolean defaultCancel) {
    this.defaultCancel = defaultCancel;
  }

  public boolean isDefaultAllCancel() {
    return defaultAllCancel;
  }

  public void setDefaultAllCancel(boolean defaultAllCancel) {
    this.defaultAllCancel = defaultAllCancel;
  }

  public PaginatedGUIBuilder setButton(int slot, ItemButton button) {
    buttons.put(slot, button);
    return this;
  }

  public PaginatedGUI build() {
    int contentSize = 0;

    for (char c : shape) {
      if(c == '#') {
        contentSize++;
      }
    }

    final int amountOfPages = (int) ((content.size() / (float) contentSize) + 0.99);

    PaginatedGUI paginatedGUI = new PaginatedGUI();

    int currentItem = 0;
    for (int pageI = 0; pageI < amountOfPages; pageI++) {
      InventoryGUI page = new InventoryGUI(
        name.replace("{page}", pageI + ""),
        (shape.length + 9)
      );

      buttons.forEach(page::setButton);

      page.setDefaultAllCancel(defaultAllCancel);
      page.setDefaultCancel(defaultCancel);

      for (int i = 0; i < shape.length; i++) {
        final char current = shape[i];

        if(current == '>' && pageI < (amountOfPages - 1)) {
          final ItemButton btn = new ItemButton(nextPageItem);
          btn.addAction(ClickType.RIGHT, (InventoryClickEvent e) ->
            paginatedGUI.showNext((Player) e.getWhoClicked()));

          btn.addAction(ClickType.LEFT, (InventoryClickEvent e) ->
            paginatedGUI.showNext((Player) e.getWhoClicked()));

          page.setButton(i, btn);
          continue;
        }

        if(current == '<' && pageI != 0) {
          final ItemButton btn = new ItemButton(previousPageItem);
          btn.addAction(ClickType.RIGHT, (InventoryClickEvent e) ->
            paginatedGUI.showPrevious((Player) e.getWhoClicked()));

          btn.addAction(ClickType.LEFT, (InventoryClickEvent e) ->
            paginatedGUI.showPrevious((Player) e.getWhoClicked()));

          page.setButton(i, btn);
          continue;
        }

        if(current == '#') {
          if(currentItem < content.size()) {
            ItemButton cItem = content.get(currentItem++);
            if(cItem.getDefaultAction() == null && contentDefaultAction != null) {
              cItem.setDefaultAction(contentDefaultAction);
            }

            page.setButton(i, cItem);
          }
          continue;
        }

        if(borderButton != null) {
          page.setButton(i, borderButton);
        }
      }

      for (int hotbarI = 0; hotbarI < hotbar.length; hotbarI++) {
        final ItemButton item = hotbar[hotbarI];

        if(item != null) {
          page.setButton(shape.length + hotbarI, item);
        }
      }

      paginatedGUI.addPage(page);
    }

    return paginatedGUI;
  }
}
