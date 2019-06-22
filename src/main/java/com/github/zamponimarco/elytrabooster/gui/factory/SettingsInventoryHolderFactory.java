package com.github.zamponimarco.elytrabooster.gui.factory;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.boosters.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.boosters.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.ElytraBoosterInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.PortalSettingsInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.SpawnerSettingsInventoryHolder;

public class SettingsInventoryHolderFactory {

	public static ElytraBoosterInventoryHolder buildSettingsInventoryHolder(ElytraBooster plugin, Booster booster) {
		if (AbstractPortal.class.isAssignableFrom(booster.getClass())) {
			return new PortalSettingsInventoryHolder(plugin, (AbstractPortal) booster);
		} else if (AbstractSpawner.class.isAssignableFrom(booster.getClass())) {
			return new SpawnerSettingsInventoryHolder(plugin, (AbstractSpawner) booster);
		}
		return null;
	}

}
