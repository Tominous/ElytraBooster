package com.github.zamponimarco.elytrabooster.boosters.spawners;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.entityholders.EntityHolder;

public class SphericSpawner extends AbstractSpawner {

	public SphericSpawner(ElytraBooster plugin, String id, Location center, double minRadius, double maxRadius,
			int cooldown, EntityHolder holder) {
		super(plugin, id, center, minRadius, maxRadius, cooldown, holder);
		runSpawnerTask();
	}

}
