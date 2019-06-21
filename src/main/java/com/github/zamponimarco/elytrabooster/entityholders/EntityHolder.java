package com.github.zamponimarco.elytrabooster.entityholders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.boosts.Boost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.entities.Entity;
import com.github.zamponimarco.elytrabooster.entities.FireworkEntity;

public class EntityHolder {
	
	private ElytraBooster plugin;
//	private Class<? extends Entity> entityClass;
	private List<Entity> entities;
	private int maxEntities;
	private Boost boost;
	
	public EntityHolder(ElytraBooster plugin, Class<? extends Entity> entityClass, int maxEntities, Boost boost) {
		this.plugin = plugin;
//		this.entityClass = entityClass;
		this.maxEntities = maxEntities;
		this.boost = boost;
		this.entities = new ArrayList<Entity>();
	}
	
	public void spawnEntity(Location center, double minRadius, double maxRadius) {
		if (entities.size() < maxEntities) {
			Random r = new Random();
			double multiply = maxRadius - minRadius;
			double randomX = r.nextDouble() * 2 - 1;
			double x = center.getX() + randomX * multiply + (Math.signum(randomX) > 0 ? minRadius : -minRadius);
			double randomY = r.nextDouble() * 2 - 1;
			double y = center.getY() + randomY * multiply + (Math.signum(randomX) > 0 ? minRadius : -minRadius);
			double randomZ = r.nextDouble() * 2 - 1;
			double z = center.getZ() + randomZ * multiply + (Math.signum(randomX) > 0 ? minRadius : -minRadius);
			entities.add(new FireworkEntity(plugin, this, new Location(center.getWorld(), x, y, z), boost));
		}
	}
	
	public void despawnAll() {
		entities.forEach(entity -> entity.entityDespawn());
		entities.clear();
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public int getMaxEntities() {
		return maxEntities;
	}

	public void despawn(Entity entity) {
		entities.remove(entity);	
	}

}
