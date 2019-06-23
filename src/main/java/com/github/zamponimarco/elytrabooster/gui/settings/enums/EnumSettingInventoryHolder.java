package com.github.zamponimarco.elytrabooster.gui.settings.enums;

import java.util.LinkedHashMap;
import java.util.Map;
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
import com.github.zamponimarco.elytrabooster.gui.settings.SettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.StringSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public abstract class EnumSettingInventoryHolder extends SettingInventoryHolder {

	private static final String PENCIL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjI4ZDk4Y2U0N2ZiNzdmOGI2MDRhNzY2ZGRkMjU0OTIzMjU2NGY5NTYyMjVjNTlmM2UzYjdiODczYTU4YzQifX19==";

	Map<String, ItemStack> headsMap;

	public EnumSettingInventoryHolder(ElytraBooster plugin, String key, Booster booster, HumanEntity player,
			Object value) {
		super(plugin, key, booster, player, value);
		headsMap = new LinkedHashMap<String, ItemStack>();
		setUpMap();
		initializeInventory();
	}

	@Override
	protected void initializeInventory() {
		inventory = Bukkit.createInventory(this, 27, MessagesUtil.color("&6&lModify &e&l" + key));
		int slot = 0;
		for (String value : headsMap.keySet()) {
			registerClickConsumer(slot, getEnumItem(value), getEnumConsumer(value));
			slot++;
		}
		registerClickConsumer(25, getStringItem(), e -> e.getWhoClicked()
				.openInventory(new StringSettingInventoryHolder(plugin, key, booster, player, "").getInventory()));
		registerClickConsumer(26, getBackItem(), getBackConsumer());
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	public abstract void setUpMap();

	private Consumer<InventoryClickEvent> getEnumConsumer(String value) {
		return e -> {
			BoosterManager<?> boosterManager = booster.getDataManager();
			boosterManager.setParam(booster.getId(), key, String.valueOf(value));
			booster = boosterManager.reloadBooster(booster);
			player.openInventory(
					SettingsInventoryHolderFactory.buildSettingsInventoryHolder(plugin, booster).getInventory());
			player.sendMessage(MessagesUtil.color(
					"&aPortal modified, &6ID: &a" + booster.getId() + ", &6" + key + ": &a" + String.valueOf(value)));
		};
	}

	private ItemStack getEnumItem(String newValue) {
		ItemStack item = headsMap.get(newValue);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color("&6&lModify -> &e&l" + newValue));
		item.setItemMeta(meta);
		return item;
	}

	private ItemStack getStringItem() {
		ItemStack item = HeadsUtil.skullFromValue(PENCIL_HEAD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color("&6&lSet a custom value"));
		item.setItemMeta(meta);
		return item;
	}

}
