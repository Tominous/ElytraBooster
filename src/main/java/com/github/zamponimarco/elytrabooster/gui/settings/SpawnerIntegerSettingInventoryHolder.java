package com.github.zamponimarco.elytrabooster.gui.settings;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.SpawnerSettingsInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.SpawnerManager;
import com.github.zamponimarco.elytrabooster.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class SpawnerIntegerSettingInventoryHolder extends SpawnerSettingInventoryHolder {

	private static final String ARROW_LEFT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjk5ZjA0OTI4OGFjNTExZjZlN2VjNWM5MjM4Zjc2NTI3YzJmYmNhZDI4NTc0MzZhYzM4MTU5NmNjMDJlNCJ9fX0==";
	private static final String ARROW2_LEFT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZlMTQ1ZTcxMjk1YmNjMDQ4OGU5YmI3ZTZkNjg5NWI3Zjk2OWEzYjViYjdlYjM0YTUyZTkzMmJjODRkZjViIn19fQ===";
	private static final String ARROW3_LEFT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzhhMWYwMzdjOGU1MTc4YmJlNGNiOWE3ZDMzNWYxYjExMGM0NWMxYzQ2NWYxZDczZGNiZThjYWQ2OWQ5ZWNhIn19fQ===";
	private static final String ARROW_RIGHT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY4YWQyNTVmOTJiODM5YjVhOGQxYmJiOWJiNGQxYTVmMzI3NDNiNmNmNTM2NjVkOTllZDczMmFhOGJlNyJ9fX0====";
	private static final String ARROW2_RIGHT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY5N2MyNDg5MmNmYzAzYzcyOGZmYWVhYmYzNGJkZmI5MmQ0NTExNDdiMjZkMjAzZGNhZmE5M2U0MWZmOSJ9fX0====";
	private static final String ARROW3_RIGHT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGY0NDg2ODYzZjMwZTM4NDMyZGJkMjJlNTQxMjk2NDY0NGVjMjVlYTRmOTkxYTM4YzczNzM3NmU5NjA2NDc5In19fQ=====";
	private static final String SUBMIT = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE3NWM4ZTUxYzNkMTA1YmFiNGM3ZGUzM2E3NzA5MzczNjRiNWEwMWMxNWI3ZGI4MmNjM2UxZmU2ZWI5MzM5NiJ9fX0======";

	private int result;

	public SpawnerIntegerSettingInventoryHolder(ElytraBooster plugin, String key, AbstractSpawner spawner,
			HumanEntity player, Object value) {
		super(plugin, key, spawner, player, value);
		this.result = (int) value;
		initializeInventory();
	}

	@Override
	protected void initializeInventory() {
		SpawnerManager spawnerManager = plugin.getSpawnerManager();
		this.inventory = Bukkit.createInventory(this, 27, MessagesUtil.color("&6&lModify &e&l" + key));
		registerClickConsumer(11, getModifyItem(-1, HeadsUtil.skullFromValue(ARROW_LEFT_HEAD)),
				e -> modifyAndReload(-1));
		registerClickConsumer(10, getModifyItem(-10, HeadsUtil.skullFromValue(ARROW2_LEFT_HEAD)),
				e -> modifyAndReload(-10));
		registerClickConsumer(9, getModifyItem(-100, HeadsUtil.skullFromValue(ARROW3_LEFT_HEAD)),
				e -> modifyAndReload(-100));
		registerClickConsumer(15, getModifyItem(+1, HeadsUtil.skullFromValue(ARROW_RIGHT_HEAD)),
				e -> modifyAndReload(+1));
		registerClickConsumer(16, getModifyItem(+10, HeadsUtil.skullFromValue(ARROW2_RIGHT_HEAD)),
				e -> modifyAndReload(+10));
		registerClickConsumer(17, getModifyItem(+100, HeadsUtil.skullFromValue(ARROW3_RIGHT_HEAD)),
				e -> modifyAndReload(+100));
		registerClickConsumer(13, getConfirmItem(), e -> {
			spawnerManager.setParam(spawner.getId(), key, String.valueOf(result));
			spawner = spawnerManager.reloadSpawner(spawner);
			player.openInventory(new SpawnerSettingsInventoryHolder(plugin, spawner).getInventory());
			player.sendMessage(MessagesUtil.color(
					"&aSpawner modified, &6ID: &a" + spawner.getId() + ", &6" + key + ": &a" + String.valueOf(result)));
		});
		registerClickConsumer(26, getBackItem(),
				e -> player.openInventory(new SpawnerSettingsInventoryHolder(plugin, spawner).getInventory()));
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private void modifyAndReload(int addition) {
		result += addition;
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
