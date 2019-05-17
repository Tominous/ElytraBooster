package com.github.zamponimarco.elytrabooster.trails;


import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class HelixBoostTrail implements BoostTrail {

	int i = 0;

	@Override
	public void spawnTrail(Player player) {
		int amount = 10;
		double increment = (2 * Math.PI) / amount;
		i = (i + 1) % amount;
		Vector direction = player.getLocation().getDirection();
		Vector toRotate = new Vector(0.5,0.5,0.5);
		Location toSpawn1 = player.getLocation().clone().add(toRotate.rotateAroundAxis(direction, i*increment));
		player.getWorld().spawnParticle(Particle.FLAME, toSpawn1, 5, 0.1, 0.1, 0.1, 0.1);

	}

}