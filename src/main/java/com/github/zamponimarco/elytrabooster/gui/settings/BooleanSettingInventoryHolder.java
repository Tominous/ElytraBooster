package com.github.zamponimarco.elytrabooster.gui.settings;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.factory.SettingsInventoryHolderFactory;
import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class BooleanSettingInventoryHolder extends SettingInventoryHolder {

	private static final String TRUE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE3NWM4ZTUxYzNkMTA1YmFiNGM3ZGUzM2E3NzA5MzczNjRiNWEwMWMxNWI3ZGI4MmNjM2UxZmU2ZWI5MzM5NiJ9fX0==";
	private static final String FALSE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY3NGQ0ZGI0Y2MzYmU0MWEzNzNkOWVmOWNhYzI3ZTYzNThjNTNmNjQxMTVkMTUwMjQzZjI1YWNmNjRmMmY1MCJ9fX0====";

	public BooleanSettingInventoryHolder(ElytraBooster plugin, String key, Booster booster, HumanEntity player,
			Object value) {
		super(plugin, key, booster, player, value);
		initializeInventory();
	}

	@Override
	protected void initializeInventory() {
		this.inventory = Bukkit.createInventory(this, 27, MessagesUtil.color("&6&lModify &e&l" + key));
		registerClickConsumer(12, getBooleanItem(HeadsUtil.skullFromValue(TRUE_HEAD), true), getBooleanConsumer(true));
		registerClickConsumer(14, getBooleanItem(HeadsUtil.skullFromValue(FALSE_HEAD), false),
				getBooleanConsumer(false));
		registerClickConsumer(26, getBackItem(), e -> player
				.openInventory(SettingsInventoryHolderFactory.buildSettingsInventoryHolder(plugin, booster).getInventory()));
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private Consumer<InventoryClickEvent> getBooleanConsumer(boolean value) {
		return e -> {
			BoosterManager<?> boosterManager = booster.getDataManager();
			boosterManager.setParam(booster.getId(), key, String.valueOf(value));
			booster = boosterManager.reloadBooster(booster);
			player.sendMessage(MessagesUtil.color(
					"&aPortal modified, &6ID: &a" + booster.getId() + ", &6" + key + ": &a" + String.valueOf(value)));
			player.openInventory(
					SettingsInventoryHolderFactory.buildSettingsInventoryHolder(plugin, booster).getInventory());
		};
	}

	private ItemStack getBooleanItem(ItemStack item, boolean value) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color("&6&lModify -> &e&l" + String.valueOf(value)));
		item.setItemMeta(meta);
		return item;
	}

}
