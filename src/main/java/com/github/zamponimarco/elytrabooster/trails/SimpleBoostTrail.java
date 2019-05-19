package com.github.zamponimarco.elytrabooster.trails;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class SimpleBoostTrail implements BoostTrail {

	Particle particle;
	
	public SimpleBoostTrail(String particle) {
		try {
			this.particle = Particle.valueOf(particle.toUpperCase());
		} catch (IllegalArgumentException e) {
			this.particle = Particle.FIREWORKS_SPARK;
		}
	}

	@Override
	public void spawnTrail(Player player) {
		player.getWorld().spawnParticle(particle, player.getLocation(), 1, 0, 0, 0, 0);
	}

}
