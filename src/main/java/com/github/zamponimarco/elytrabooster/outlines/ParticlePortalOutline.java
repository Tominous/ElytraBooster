package com.github.zamponimarco.elytrabooster.outlines;

import java.util.List;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticlePortalOutline implements PortalOutline {

	private Particle outlineType;
	private Particle cooldownType;
	private double outlineSpeed;
	private double cooldownSpeed;

	public ParticlePortalOutline(String outlineType, String cooldownType) {
		try {
			String[] outlineData = outlineType.split(":");
			String[] cooldownData = cooldownType.split(":");
			this.outlineType = Particle.valueOf(outlineData[0].toUpperCase());
			this.cooldownType = Particle.valueOf(cooldownData[0].toUpperCase());
			this.outlineSpeed = outlineData.length >= 2 ? Double.valueOf(outlineData[1]) : 0;
			this.cooldownSpeed = cooldownData.length >= 2 ? Double.valueOf(cooldownData[1]) : 0;
		} catch (Exception e) {
			this.outlineType = Particle.FLAME;
			this.cooldownType = Particle.FLAME;
			Bukkit.getLogger().warning(
					ChatColor.RED + outlineType + " or " + cooldownType + " is not a particle, check portals.yml");
		}
	}

	@Override
	public void drawOutline(List<Location> points) {
		points.forEach(point -> {
			point.getWorld().spawnParticle(outlineType, point, 1, 0, 0, 0, outlineSpeed, null, true);
		});
	}

	@Override
	public void eraseOutline(List<Location> points) {
	}

	@Override
	public void cooldownOutline(List<Location> points, int cooldown, int progress) {
		int cooldownBlocks = (int) ((progress / (double) cooldown) * points.size());
		IntStream.range(0, cooldownBlocks).forEach(i -> {
			Location point = points.get(i);
			point.getWorld().spawnParticle(cooldownType, point, 1, 0, 0, 0, cooldownSpeed, null, true);
		});
		IntStream.range(cooldownBlocks, points.size()).forEach(i -> {
			Location point = points.get(i);
			point.getWorld().spawnParticle(outlineType, point, 1, 0, 0, 0, outlineSpeed, null, true);
		});
	}

	@Override
	public String getOutlineType() {
		return outlineType.name() + (outlineSpeed == 0 ? "" : ":" + String.valueOf(outlineSpeed));
	}

	@Override
	public String getCooldownType() {
		return cooldownType.name() + (cooldownSpeed == 0 ? "" : ":" + String.valueOf(cooldownSpeed));
	}

}
