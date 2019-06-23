package com.github.zamponimarco.elytrabooster.gui.settings.enums;

import org.bukkit.entity.HumanEntity;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;

public class ShapeEnumSettingInventoryHolder extends EnumSettingInventoryHolder {

	private static final String CIRCLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQ3NjUxYmM4NmI2YWJkODllZTdlYTY1NGQ0NjkwY2E2NDc0ZmFlMWY3ZjZkMjhiYzdkNGU0MTE2YTc0In19fQ==";
	private static final String TRIANGLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRhOWZiMzU1MmQ1NTE1NTNkOWRkNDNjMmJiMWQyNjg4OTNkZjY4ZDczZTQ2MTEzNDNiNTcyYWU2NDI1Y2EifX19==";
	private static final String RECTANGLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMxZWE4MjA0Y2FiOGYzMzI3ZmZjZWY0OTJkMTkzZGE2MzQ0YThmODY0NTUyNDQyZTE1NWZlNzNiYjZiYSJ9fX0===";
	private static final String SQUARE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjRlYjgyNDVlOTJlYjY4YmI3N2VhMjZkNTU5YzM4YTNhZGYxOGYzY2VhNWJmMWRkZWM3ZDdmOGM1NTQ0NDhiIn19fQ====";
	
	public ShapeEnumSettingInventoryHolder(ElytraBooster plugin, String key, Booster booster, HumanEntity player,
			Object value) {
		super(plugin, key, booster, player, value);
	}

	@Override
	public void setUpMap() {
		headsMap.put("circle", HeadsUtil.skullFromValue(CIRCLE_HEAD));
		headsMap.put("square", HeadsUtil.skullFromValue(SQUARE_HEAD));
		headsMap.put("rectangle", HeadsUtil.skullFromValue(RECTANGLE_HEAD));
		headsMap.put("triangle", HeadsUtil.skullFromValue(TRIANGLE_HEAD));
	}

}
