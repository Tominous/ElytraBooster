package com.github.zamponimarco.elytrabooster.gui.settings.enums;

import org.bukkit.entity.HumanEntity;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;

public class SorterEnumSettingInventoryHolder extends EnumSettingInventoryHolder {

	private static final String NONE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==";
	private static final String CLOSING_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQ3NjUxYmM4NmI2YWJkODllZTdlYTY1NGQ0NjkwY2E2NDc0ZmFlMWY3ZjZkMjhiYzdkNGU0MTE2YTc0In19fQ==";
	private static final String RANDOM_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMxZWE4MjA0Y2FiOGYzMzI3ZmZjZWY0OTJkMTkzZGE2MzQ0YThmODY0NTUyNDQyZTE1NWZlNzNiYjZiYSJ9fX0===";

	
	public SorterEnumSettingInventoryHolder(ElytraBooster plugin, String key, Booster booster, HumanEntity player,
			Object value) {
		super(plugin, key, booster, player, value);
	}

	@Override
	public void setUpMap() {
		headsMap.put("none", HeadsUtil.skullFromValue(NONE_HEAD));
		headsMap.put("closing", HeadsUtil.skullFromValue(CLOSING_HEAD));
		headsMap.put("random", HeadsUtil.skullFromValue(RANDOM_HEAD));
	}

}
