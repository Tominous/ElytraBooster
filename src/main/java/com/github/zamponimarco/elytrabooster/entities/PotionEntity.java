package com.github.zamponimarco.elytrabooster.entities;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import com.github.zamponimarco.elytrabooster.boosts.Boost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.entityholders.EntityHolder;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;

public class PotionEntity extends Entity {

	private static final String POTION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMxMDRmMTlhOTQ1YzYyZTEwMzJkZTZlNmM2MzQyMDY2NDdkOTRlZDljMGE1ODRlNmQ2YjZkM2E0NzVmNTIifX19==";

	private Item item;
	private int effectTaskNumber;

	public PotionEntity(ElytraBooster plugin, EntityHolder holder, Location location, Boost boost) {
		super(plugin, holder, location, boost);
		spawn();
	}

	@Override
	public void spawn() {
		item = (Item) location.getWorld().spawnEntity(location, EntityType.DROPPED_ITEM);
		item.setGravity(false);
		item.setVelocity(new Vector());
		item.setPickupDelay(32767);
		item.setInvulnerable(true);
		item.setItemStack(HeadsUtil.skullFromValue(POTION_HEAD));
		runEffectTask();
	}

	@Override
	public void entityDespawn() {
		item.remove();
		plugin.getServer().getScheduler().cancelTask(effectTaskNumber);
	}

	public void onActivation() {
		location.getWorld().playSound(location, Sound.ITEM_BOTTLE_EMPTY, 20, 0);
	}

	private void runEffectTask() {
		this.effectTaskNumber = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
			if (item.isDead()) {
				entityDespawn();
				spawn();
			} else {
				location.getWorld().spawnParticle(Particle.SPELL_MOB, location, 0, 0.85, 1, 1, 1);
			}
		}, 0, 10).getTaskId();
	}

}
