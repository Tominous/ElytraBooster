package com.github.zamponimarco.elytrabooster.outline;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

public class BlockPortalOutline implements PortalOutline {

	private Material outlineType;

	public BlockPortalOutline(String outlineType) {
		this.outlineType = Material.valueOf(outlineType);
	}

	@Override
	public void drawOutline(List<Location> points) {
		drawOutline(points, outlineType);
	}

	private void drawOutline(List<Location> points, Material m) {
		points.forEach(point -> point.getBlock().setType(m));
	}

	@Override
	public void eraseOutline(List<Location> points) {
		drawOutline(points, Material.AIR);
	}

}
