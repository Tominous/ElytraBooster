package com.github.zamponimarco.elytrabooster.gui.settings;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.core.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.factory.SettingsInventoryHolderFactory;
import com.github.zamponimarco.elytrabooster.managers.BoosterManager;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class DoubleSettingInventoryHolder extends SettingInventoryHolder {

	private static final String ARROW_LEFT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjk5ZjA0OTI4OGFjNTExZjZlN2VjNWM5MjM4Zjc2NTI3YzJmYmNhZDI4NTc0MzZhYzM4MTU5NmNjMDJlNCJ9fX0==";
	private static final String ARROW2_LEFT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZlMTQ1ZTcxMjk1YmNjMDQ4OGU5YmI3ZTZkNjg5NWI3Zjk2OWEzYjViYjdlYjM0YTUyZTkzMmJjODRkZjViIn19fQ===";
	private static final String ARROW3_LEFT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzhhMWYwMzdjOGU1MTc4YmJlNGNiOWE3ZDMzNWYxYjExMGM0NWMxYzQ2NWYxZDczZGNiZThjYWQ2OWQ5ZWNhIn19fQ===";
	private static final String ARROW_RIGHT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY4YWQyNTVmOTJiODM5YjVhOGQxYmJiOWJiNGQxYTVmMzI3NDNiNmNmNTM2NjVkOTllZDczMmFhOGJlNyJ9fX0====";
	private static final String ARROW2_RIGHT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY5N2MyNDg5MmNmYzAzYzcyOGZmYWVhYmYzNGJkZmI5MmQ0NTExNDdiMjZkMjAzZGNhZmE5M2U0MWZmOSJ9fX0====";
	private static final String ARROW3_RIGHT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGY0NDg2ODYzZjMwZTM4NDMyZGJkMjJlNTQxMjk2NDY0NGVjMjVlYTRmOTkxYTM4YzczNzM3NmU5NjA2NDc5In19fQ=====";
	private static final String SUBMIT = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE3NWM4ZTUxYzNkMTA1YmFiNGM3ZGUzM2E3NzA5MzczNjRiNWEwMWMxNWI3ZGI4MmNjM2UxZmU2ZWI5MzM5NiJ9fX0======";

	private double result;

	public DoubleSettingInventoryHolder(ElytraBooster plugin, String key, Booster booster, HumanEntity player,
			Object value) {
		super(plugin, key, booster, player, value);
		this.result = (double) value;
		initializeInventory();
	}

	@Override
	protected void initializeInventory() {
		BoosterManager<?> boosterManager = booster.getDataManager();
		this.inventory = Bukkit.createInventory(this, 27, MessagesUtil.color("&6&lModify &e&l" + key));
		registerClickConsumer(11, getModifyItem(-0.01, HeadsUtil.skullFromValue(ARROW_LEFT_HEAD)),
				e -> modifyAndReload(-0.01));
		registerClickConsumer(10, getModifyItem(-0.1, HeadsUtil.skullFromValue(ARROW2_LEFT_HEAD)),
				e -> modifyAndReload(-0.1));
		registerClickConsumer(9, getModifyItem(-1.0, HeadsUtil.skullFromValue(ARROW3_LEFT_HEAD)),
				e -> modifyAndReload(-1));
		registerClickConsumer(15, getModifyItem(+0.01, HeadsUtil.skullFromValue(ARROW_RIGHT_HEAD)),
				e -> modifyAndReload(+0.01));
		registerClickConsumer(16, getModifyItem(+0.1, HeadsUtil.skullFromValue(ARROW2_RIGHT_HEAD)),
				e -> modifyAndReload(+0.1));
		registerClickConsumer(17, getModifyItem(+1.0, HeadsUtil.skullFromValue(ARROW3_RIGHT_HEAD)),
				e -> modifyAndReload(+1));
		registerClickConsumer(13, getConfirmItem(), e -> {
			boosterManager.setParam(booster.getId(), key, String.valueOf(result));
			booster = boosterManager.reloadBooster(booster);
			player.openInventory(
					SettingsInventoryHolderFactory.buildSettingsInventoryHolder(plugin, booster).getInventory());
			player.sendMessage(MessagesUtil.color(
					"&aPortal modified, &6ID: &a" + booster.getId() + ", &6" + key + ": &a" + String.valueOf(result)));
		});
		registerClickConsumer(26, getBackItem(), e -> player.openInventory(
				SettingsInventoryHolderFactory.buildSettingsInventoryHolder(plugin, booster).getInventory()));
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private void modifyAndReload(double addition) {
		result += addition;
		result = (double) Math.round(result * 100d) / 100d;
		inventory.setItem(13, getConfirmItem());
	}

	private ItemStack getModifyItem(double addition, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color("&6&lModify -> &e&l" + String.valueOf(addition)));
		item.setItemMeta(meta);
		return item;
	}

	private ItemStack getConfirmItem() {
		ItemStack item = HeadsUtil.skullFromValue(SUBMIT);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color("&6&lResult = &e&l" + String.valueOf(result)));
		item.setItemMeta(meta);
		return item;
	}

}
