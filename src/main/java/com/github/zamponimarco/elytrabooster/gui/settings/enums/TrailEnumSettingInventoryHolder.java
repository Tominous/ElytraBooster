package com.github.zamponimarco.elytrabooster.gui.settings.enums;

import org.bukkit.entity.HumanEntity;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;

public class TrailEnumSettingInventoryHolder extends EnumSettingInventoryHolder {

	private static final String SIMPLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ==";
	private static final String RAINBOW_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY5ZTNlNmU1YjJiOTJmMGJlYjM2OGI3MzhiOTkzZDdiYTIyNWJmOWJiMjc1OGJmYzlmYzJkYWJhNGE1YTdkIn19fQ====";
	private static final String HELIX_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE3MDg1NzRlN2U5YTZhNzZjOWFhODIxNDc5N2IxYWMzODk1MDY4OWVmOWNkZGExOGQ1MDM2MjM5OGI2MTAxZCJ9fX0=";
	private static final String NONE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ===";

	
	public TrailEnumSettingInventoryHolder(ElytraBooster plugin, String key, Booster booster, HumanEntity player,
			Object value) {
		super(plugin, key, booster, player, value);
	}

	@Override
	public void setUpMap() {
		headsMap.put("simple", HeadsUtil.skullFromValue(SIMPLE_HEAD));
		headsMap.put("rainbow", HeadsUtil.skullFromValue(RAINBOW_HEAD));
		headsMap.put("helix", HeadsUtil.skullFromValue(HELIX_HEAD));
		headsMap.put("none", HeadsUtil.skullFromValue(NONE_HEAD));
	}

}
