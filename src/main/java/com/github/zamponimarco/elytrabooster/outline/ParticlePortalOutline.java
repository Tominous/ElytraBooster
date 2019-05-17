package com.github.zamponimarco.elytrabooster.outline;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticlePortalOutline implements PortalOutline {

	private Particle outline;
	
	public ParticlePortalOutline(String outlineType) {
		outline = Particle.valueOf(outlineType);
	}
	
	@Override
	public void drawOutline(List<Location> points) {
		points.forEach(point -> {
			point.getWorld().spawnParticle(outline, point, 0);
		});
	}

	@Override
	public void eraseOutline(List<Location> points) {
	}

}
