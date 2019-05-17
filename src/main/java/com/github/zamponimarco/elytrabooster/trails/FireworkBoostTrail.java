package com.github.zamponimarco.elytrabooster.trails;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class FireworkBoostTrail implements BoostTrail {

	@Override
	public void spawnTrail(Player player) {
		player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 1, 0, 0, 0, 0);
	}

}
