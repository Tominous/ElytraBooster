package com.github.zamponimarco.elytrabooster.entities;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import com.github.zamponimarco.elytrabooster.boosts.Boost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.entityholders.EntityHolder;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;

public class FireworkEntity extends Entity {

	private static final String FIREWORK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ==";

	private Item item;
	private int effectTaskNumber;

	public FireworkEntity(ElytraBooster plugin, EntityHolder holder, Location location, Boost boost) {
		super(plugin, holder, location, boost);
		spawn();
		runEntityTask();
	}

	@Override
	public void spawn() {
		item = (Item) location.getWorld().spawnEntity(location, EntityType.DROPPED_ITEM);
		item.setGravity(false);
		item.setVelocity(new Vector());
		item.setPickupDelay(32767);
		item.setInvulnerable(true);
		item.setItemStack(HeadsUtil.skullFromValue(FIREWORK_HEAD));
		runEffectTask();
	}

	@Override
	public void entityDespawn() {
		item.remove();
		plugin.getServer().getScheduler().cancelTask(effectTaskNumber);
	}

	private void runEffectTask() {
		this.effectTaskNumber = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
			if (item.isDead()) {
				entityDespawn();
				spawn();
			} else {				
				location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 3, 0.1, 0.1, 0.1, 0.01);
			}
		}, 0, 4).getTaskId();
	}

}
