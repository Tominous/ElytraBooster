package com.github.zamponimarco.elytrabooster.spawners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.entityholders.EntityHolder;
import com.github.zamponimarco.elytrabooster.managers.BoosterManager;

public abstract class AbstractSpawner implements Booster{

	protected ElytraBooster plugin;
	protected String id;
	protected Location center;
	protected double minRadius;
	protected double maxRadius;
	protected int cooldown;
	protected EntityHolder holder;

	protected int spawnTaskNumber;

	protected List<Location> entities;

	public AbstractSpawner(ElytraBooster plugin, String id, Location center, double minRadius, double maxRadius,
			int cooldown, EntityHolder holder) {
		this.plugin = plugin;
		this.id = id;
		this.center = center;
		this.minRadius = minRadius;
		this.maxRadius = maxRadius;
		this.cooldown = cooldown;
		this.holder = holder;

		this.entities = new ArrayList<Location>();
	}
	
	public BoosterManager<?> getDataManager() {
		return plugin.getSpawnerManager();
	}

	public void runSpawnerTask() {
		this.spawnTaskNumber = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> spawnEntity(), 0, cooldown)
				.getTaskId();
	}

	private void spawnEntity() {
		holder.spawnEntity(center, minRadius, maxRadius);
	}
	
	public EntityHolder getHolder() {
		return holder;
	}

	public void stopBoosterTask() {
		plugin.getServer().getScheduler().cancelTask(spawnTaskNumber);
		holder.despawnAll();
	}

	public String getId() {
		return id;
	}

	public Location getCenter() {
		return center;
	}

	public double getMinRadius() {
		return minRadius;
	}

	public double getMaxRadius() {
		return maxRadius;
	}

	public void setCenter(Location center) {
		this.center = center;
	}

	public int getCooldown() {
		return cooldown;
	}

}
