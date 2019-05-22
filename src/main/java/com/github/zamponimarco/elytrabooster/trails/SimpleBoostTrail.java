package com.github.zamponimarco.elytrabooster.trails;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SimpleBoostTrail implements BoostTrail {

	Particle particle;
	
	public SimpleBoostTrail(String particle) {
		try {
			if (particle == null) {
				particle = "FIREWORKS_SPARK";
			}
			this.particle = Particle.valueOf(particle.toUpperCase());
		} catch (IllegalArgumentException e) {
			Bukkit.getLogger().warning(ChatColor.RED + particle + " is not a particle, check portals.yml");
			this.particle = Particle.FIREWORKS_SPARK;
		}
	}

	@Override
	public void spawnTrail(Player player) {
		player.getWorld().spawnParticle(particle, player.getLocation(), 3, 0.1, 0.1, 0.1, 0.1);
	}

}
