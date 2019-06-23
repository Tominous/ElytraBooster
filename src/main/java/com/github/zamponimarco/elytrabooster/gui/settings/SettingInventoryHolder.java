package com.github.zamponimarco.elytrabooster.gui.settings;

import java.util.function.Consumer;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.ElytraBoosterInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.factory.SettingsInventoryHolderFactory;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public abstract class SettingInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final String BACK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZlMTQ1ZTcxMjk1YmNjMDQ4OGU5YmI3ZTZkNjg5NWI3Zjk2OWEzYjViYjdlYjM0YTUyZTkzMmJjODRkZjViIn19fQ====";

	protected String key;
	protected Booster booster;
	protected HumanEntity player;

	public SettingInventoryHolder(ElytraBooster plugin, String key, Booster booster, HumanEntity player, Object value) {
		super(plugin);
		this.key = key;
		this.booster = booster;
		this.player = player;
	}

	protected ItemStack getBackItem() {
		ItemStack item = HeadsUtil.skullFromValue(BACK_HEAD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color("&6&lGo back"));
		item.setItemMeta(meta);
		return item;
	}

	protected Consumer<InventoryClickEvent> getBackConsumer() {
		return e -> player.openInventory(
				SettingsInventoryHolderFactory.buildSettingsInventoryHolder(plugin, booster).getInventory());
	}

}
