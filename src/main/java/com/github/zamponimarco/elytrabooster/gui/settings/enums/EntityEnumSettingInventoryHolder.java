package com.github.zamponimarco.elytrabooster.gui.settings.enums;

import org.bukkit.entity.HumanEntity;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;

public class EntityEnumSettingInventoryHolder extends EnumSettingInventoryHolder{

	private static final String FIREWORK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ==";
	private static final String MUSHROOM_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzU3MTk1MmEzNWMzMTYzYjhjMzNhMDkxOGQ2ZTlhNDUzM2Y2MjA1M2FkNGU2Y2ZjYjFmYTI3ZjU1MWFlZjIifX19==";
	private static final String POTION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMxMDRmMTlhOTQ1YzYyZTEwMzJkZTZlNmM2MzQyMDY2NDdkOTRlZDljMGE1ODRlNmQ2YjZkM2E0NzVmNTIifX19==";

	
	public EntityEnumSettingInventoryHolder(ElytraBooster plugin, String key, Booster booster, HumanEntity player,
			Object value) {
		super(plugin, key, booster, player, value);
	}

	@Override
	public void setUpMap() {
		headsMap.put("firework", HeadsUtil.skullFromValue(FIREWORK_HEAD));
		headsMap.put("mushroom", HeadsUtil.skullFromValue(MUSHROOM_HEAD));
		headsMap.put("potion", HeadsUtil.skullFromValue(POTION_HEAD));
	}

}
