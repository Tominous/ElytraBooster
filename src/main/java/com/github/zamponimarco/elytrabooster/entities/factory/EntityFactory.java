package com.github.zamponimarco.elytrabooster.entities.factory;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.boosts.Boost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.entities.Entity;
import com.github.zamponimarco.elytrabooster.entityholders.EntityHolder;

public class EntityFactory {

	public static Entity buildEntity(Class<? extends Entity> entityClass, ElytraBooster plugin, EntityHolder holder, Location location, Boost boost) {
		try {
			return entityClass.getConstructor(ElytraBooster.class, EntityHolder.class, Location.class, Boost.class).newInstance(plugin, holder, location, boost);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	
}
