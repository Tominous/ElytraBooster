package com.github.zamponimarco.elytrabooster.gui.settings;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.SpawnerSettingsInventoryHolder;
import com.github.zamponimarco.elytrabooster.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class SpawnerStringSettingInventoryHolder extends SpawnerSettingInventoryHolder {

	private static final String MODIFY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE3NWM4ZTUxYzNkMTA1YmFiNGM3ZGUzM2E3NzA5MzczNjRiNWEwMWMxNWI3ZGI4MmNjM2UxZmU2ZWI5MzM5NiJ9fX0==";
	
	private static Map<HumanEntity, Map<AbstractSpawner, String>> settingsMap;

	public SpawnerStringSettingInventoryHolder(ElytraBooster plugin, String key, AbstractSpawner spawner, HumanEntity player,
			Object value) {
		super(plugin, key, spawner, player, value);
		settingsMap = new HashMap<>();
		initializeInventory();
	}

	@Override
	protected void initializeInventory() {
		this.inventory = Bukkit.createInventory(this, 27, MessagesUtil.color("&6&lModify &e&l" + key));
		registerClickConsumer(13, getStringItem(HeadsUtil.skullFromValue(MODIFY_HEAD)), e -> playerCanWrite());
		registerClickConsumer(26, getBackItem(),
				e -> player.openInventory(new SpawnerSettingsInventoryHolder(plugin, spawner).getInventory()));
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private void playerCanWrite() {
		player.sendMessage(MessagesUtil.color(
				"&aTo modify the parameter type in chat the &6&lnew value&a.\n&aType &6&l'exit' &ato leave the value unmodified."));
		settingsMap.put(player, new HashMap<>());
		settingsMap.get(player).put(spawner, key);
		player.closeInventory();
	}

	public static Map<HumanEntity, Map<AbstractSpawner, String>> getSettingsMap() {
		return settingsMap;
	}

	private ItemStack getStringItem(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color("&6&lModify Value"));
		item.setItemMeta(meta);
		return item;
	}

}
