package com.github.zamponimarco.elytrabooster.gui.factory;

import com.github.zamponimarco.elytrabooster.core.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.ElytraBoosterInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.PortalSettingsInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.SpawnerSettingsInventoryHolder;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.spawners.AbstractSpawner;

public class SettingsInventoryHolderFactory {

	public static ElytraBoosterInventoryHolder buildSettingsInventoryHolder(ElytraBooster plugin, Booster booster) {
		if (AbstractPortal.class.isAssignableFrom(booster.getClass())) {
			return new PortalSettingsInventoryHolder(plugin, (AbstractPortal) booster);
		} else if (AbstractSpawner.class.isAssignableFrom(booster.getClass())) {
			return new SpawnerSettingsInventoryHolder(plugin, (AbstractSpawner) booster);
		}
		System.out.println("malissimo");
		return null;
	}

}
