package com.github.zamponimarco.elytrabooster.gui.settings;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.ElytraBoosterInventoryHolder;
import com.github.zamponimarco.elytrabooster.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public abstract class SpawnerSettingInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final String BACK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZlMTQ1ZTcxMjk1YmNjMDQ4OGU5YmI3ZTZkNjg5NWI3Zjk2OWEzYjViYjdlYjM0YTUyZTkzMmJjODRkZjViIn19fQ====";
	
	protected String key;
	protected AbstractSpawner spawner;
	protected HumanEntity player;
	
	public SpawnerSettingInventoryHolder(ElytraBooster plugin, String key, AbstractSpawner spawner, HumanEntity player,
			Object value) {
		super(plugin);
		this.key = key;
		this.spawner = spawner;
		this.player = player;
	}
	
	protected ItemStack getBackItem() {
		ItemStack item = HeadsUtil.skullFromValue(BACK_HEAD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color( "&6&lGo back"));
		item.setItemMeta(meta);
		return item;
	}

}
