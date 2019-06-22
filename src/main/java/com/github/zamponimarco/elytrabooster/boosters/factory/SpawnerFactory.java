package com.github.zamponimarco.elytrabooster.boosters.factory;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.github.zamponimarco.elytrabooster.boosters.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.boosters.spawners.SphericSpawner;
import com.github.zamponimarco.elytrabooster.boosts.Boost;
import com.github.zamponimarco.elytrabooster.boosts.SimpleBoost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.entities.Entity;
import com.github.zamponimarco.elytrabooster.entities.FireworkEntity;
import com.github.zamponimarco.elytrabooster.entityholders.EntityHolder;
import com.github.zamponimarco.elytrabooster.trails.BoostTrail;
import com.github.zamponimarco.elytrabooster.trails.factory.BoostTrailFactory;

public class SpawnerFactory implements BoosterFactory {

	@SuppressWarnings("unchecked")
	public static AbstractSpawner buildBooster(ElytraBooster plugin, ConfigurationSection spawnerConfiguration) {

		String id = spawnerConfiguration.getName();

		World world = plugin.getServer().getWorld(spawnerConfiguration.getString("world"));
		double x = spawnerConfiguration.getDouble("x");
		double y = spawnerConfiguration.getDouble("y");
		double z = spawnerConfiguration.getDouble("z");
		Location center = new Location(world, x, y, z);

		double minRadius = spawnerConfiguration.getDouble("minRadius", 1.0);

		double maxRadius = spawnerConfiguration.getDouble("maxRadius", 10.0);

		int maxEntities = spawnerConfiguration.getInt("maxEntities", 2);

		double initialVelocity = spawnerConfiguration.getDouble("initialVelocity", 3.0);

		double finalVelocity = spawnerConfiguration.getDouble("finalVelocity", 1.0);

		int boostDuration = spawnerConfiguration.getInt("boostDuration", 30);

		String trailString = spawnerConfiguration.getString("trail", "simple");
		BoostTrail trail = BoostTrailFactory.buildBoostTrail(trailString);

		Boost boost = new SimpleBoost(boostDuration, initialVelocity, finalVelocity, trail);

		String entityString = spawnerConfiguration.getString("entity", "firework");
		Class<? extends Entity> entityClass = FireworkEntity.class;
		try {
			entityClass = (Class<? extends Entity>) Class.forName(
					"com.github.zamponimarco.elytrabooster.entities." + WordUtils.capitalize(entityString) + "Entity");
		} catch (Exception e) {
		}

		EntityHolder holder = new EntityHolder(plugin, entityClass, maxEntities, boost);

		int cooldown = spawnerConfiguration.getInt("cooldown", 60);

		return new SphericSpawner(plugin, id, center, minRadius, maxRadius, cooldown, holder);
	}

}
