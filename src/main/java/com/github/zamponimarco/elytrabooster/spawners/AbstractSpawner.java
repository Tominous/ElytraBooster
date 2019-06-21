package com.github.zamponimarco.elytrabooster.spawners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.entityholders.EntityHolder;

public abstract class AbstractSpawner {

	protected ElytraBooster plugin;
	protected String id;
	protected Location center;
	protected double minRadius;
	protected double maxRadius;
	protected int cooldown;
	protected EntityHolder holder;

	protected int spawnTaskNumber;

	protected List<Location> entities;
	protected int checkInterval;

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

}
