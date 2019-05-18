package com.github.zamponimarco.elytrabooster.outlines;

import java.util.List;
import java.util.stream.IntStream;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticlePortalOutline implements PortalOutline {

	private Particle outlineType;
	private Particle cooldownType;
	
	public ParticlePortalOutline(String outlineType, String cooldownType) {
		this.outlineType = Particle.valueOf(outlineType);
		this.cooldownType = Particle.valueOf(cooldownType);
	}
	
	@Override
	public void drawOutline(List<Location> points) {
		points.forEach(point -> {
			point.getWorld().spawnParticle(outlineType, point, 0);
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
			point.getWorld().spawnParticle(cooldownType, point, 0);
		});
		IntStream.range(cooldownBlocks, points.size()).forEach(i -> {
			Location point = points.get(i);
			point.getWorld().spawnParticle(outlineType, point, 0);
		});
	}

}
