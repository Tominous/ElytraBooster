package com.github.zamponimarco.elytrabooster.entities;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.boosts.Boost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.entityholders.EntityHolder;
import com.github.zamponimarco.elytrabooster.events.PlayerBoostEvent;

public abstract class Entity {

	protected ElytraBooster plugin;
	protected EntityHolder holder;
	protected Location location;
	protected Boost boost;

	private int checkTasknumber;

	public Entity(ElytraBooster plugin, EntityHolder holder, Location location, Boost boost) {
		this.plugin = plugin;
		this.holder = holder;
		this.location = location;
		this.boost = boost;
		runEntityTask();
	}

	public void runEntityTask() {
		checkTasknumber = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> checkPlayersPassing(), 0, 1)
				.getTaskId();
	}

	private void checkPlayersPassing() {
		plugin.getStatusMap().keySet().forEach(player -> {
			if (!plugin.getStatusMap().get(player) && player.hasPermission("eb.portals.boost")
					&& player.getLocation().distance(location) <= 1.0) {
				Bukkit.getPluginManager().callEvent(new PlayerBoostEvent(plugin, player, boost));
				plugin.getServer().getScheduler().cancelTask(checkTasknumber);
				holderDespawn();
			}
		});
	}

	public void holderDespawn() {
		entityDespawn();
		holder.despawn(this);
	}

	public abstract void spawn();

	public abstract void entityDespawn();

}
