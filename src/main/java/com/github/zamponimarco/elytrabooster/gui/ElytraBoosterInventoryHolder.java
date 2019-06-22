package com.github.zamponimarco.elytrabooster.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.settings.StringSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;
import com.google.common.collect.Lists;

public abstract class ElytraBoosterInventoryHolder implements InventoryHolder {

	private static final String DELETE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTljZGI5YWYzOGNmNDFkYWE1M2JjOGNkYTc2NjVjNTA5NjMyZDE0ZTY3OGYwZjE5ZjI2M2Y0NmU1NDFkOGEzMCJ9fX0=";
	private static final String CREATE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTcxZDg5NzljMTg3OGEwNTk4N2E3ZmFmMjFiNTZkMWI3NDRmOWQwNjhjNzRjZmZjZGUxZWExZWRhZDU4NTIifX19=";

	protected ElytraBooster plugin;
	protected Inventory inventory;
	protected Map<Integer, Consumer<InventoryClickEvent>> clickMap;

	public ElytraBoosterInventoryHolder(ElytraBooster plugin) {
		this.plugin = plugin;
		this.clickMap = new HashMap<>();
	}

	protected abstract void initializeInventory();

	public void handleClickEvent(InventoryClickEvent e) {
		clickMap.get(e.getSlot()).accept(e);
		e.setCancelled(true);
	}

	public void registerClickConsumer(int slot, ItemStack item, Consumer<InventoryClickEvent> clickConsumer) {
		inventory.setItem(slot, item);
		clickMap.put(slot, clickConsumer);
	}

	public void fillInventoryWith(Material material) {
		for (int i = 0; i < inventory.getSize(); i++) {
			if (inventory.getItem(i) == null) {
				registerClickConsumer(i, getPlaceholderItem(material), e -> {
				});
			}
		}
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	private ItemStack getPlaceholderItem(Material material) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}

	protected Consumer<InventoryClickEvent> getDeleteConsumer(Booster booster) {
		return e -> {
			BoosterManager<?> boosterManager = booster.getDataManager();
			booster.stopBoosterTask();
			boosterManager.removeBooster(booster.getId());
			boosterManager.getDataYaml().set(booster.getId(), null);
			boosterManager.saveConfig();
			e.getWhoClicked().sendMessage(MessagesUtil.color("&aBooster deleted, &6ID: &a" + booster.getId()));
			e.getWhoClicked().closeInventory();
		};
	}

	protected ItemStack getDeleteItem() {
		ItemStack item = HeadsUtil.skullFromValue(DELETE_HEAD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color("&c&lRemove Booster"));
		meta.setLore(Lists.newArrayList(MessagesUtil.color("&6&l- &e&lLeft click &6to delete booster")));
		item.setItemMeta(meta);
		return item;
	}

	protected Consumer<InventoryClickEvent> getCreateConsumer(String booster) {
		return e -> {
			e.getWhoClicked().openInventory(
					new StringSettingInventoryHolder(plugin, booster, null, e.getWhoClicked(), "").getInventory());
		};
	}

	protected ItemStack getCreateItem() {
		ItemStack item = HeadsUtil.skullFromValue(CREATE_HEAD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color("&a&lCreate Booster"));
		meta.setLore(Lists.newArrayList(MessagesUtil.color("&6&l- &e&lLeft click &6to create booster")));
		item.setItemMeta(meta);
		return item;
	}

}
