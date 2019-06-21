package com.github.zamponimarco.elytrabooster.gui.settings;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.ElytraBoosterInventoryHolder;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public abstract class PortalSettingInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final String BACK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZlMTQ1ZTcxMjk1YmNjMDQ4OGU5YmI3ZTZkNjg5NWI3Zjk2OWEzYjViYjdlYjM0YTUyZTkzMmJjODRkZjViIn19fQ====";
	
	protected String key;
	protected AbstractPortal portal;
	protected HumanEntity player;

	public PortalSettingInventoryHolder(ElytraBooster plugin, String key, AbstractPortal portal, HumanEntity player,
			Object value) {
		super(plugin);
		this.key = key;
		this.portal = portal;
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
