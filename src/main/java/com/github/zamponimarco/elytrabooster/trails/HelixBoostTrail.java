package com.github.zamponimarco.elytrabooster.trails;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class HelixBoostTrail implements BoostTrail {

	private Particle particle;
	private int i = 0;

	public HelixBoostTrail(String particle) {
		try {
			if (particle == null) {
				particle = "FLAME";
			}
			this.particle = Particle.valueOf(particle.toUpperCase());
		} catch (IllegalArgumentException e) {
			Bukkit.getLogger().warning(ChatColor.RED + particle + " is not a particle, check portals.yml");
			this.particle = Particle.FLAME;
		}
	}

	@Override
	public void spawnTrail(Player player) {
		int amount = 10;
		double increment = (2 * Math.PI) / amount;
		i = (i + 1) % amount;
		Vector direction = player.getLocation().getDirection();
		Vector toRotate = new Vector(0.5, 0.5, 0.5);
		Location toSpawn1 = player.getLocation().clone().add(toRotate.rotateAroundAxis(direction, i * increment));
		player.getWorld().spawnParticle(particle, toSpawn1, 5, 0.1, 0.1, 0.1, 0.1);

	}

}
