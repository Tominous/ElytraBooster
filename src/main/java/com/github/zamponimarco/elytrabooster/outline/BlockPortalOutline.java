package com.github.zamponimarco.elytrabooster.outline;

import java.util.List;
import java.util.stream.IntStream;

import org.bukkit.Location;
import org.bukkit.Material;

public class BlockPortalOutline implements PortalOutline {

	private Material outlineType;
	private Material cooldownType;

	public BlockPortalOutline(String outlineType, String cooldownType) {
		this.outlineType = Material.valueOf(outlineType);
		this.cooldownType = Material.valueOf(cooldownType);
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

	@Override
	public void cooldownOutline(List<Location> points, int cooldown, int progress) {
		int cooldownBlocks = (int) ((progress / (double) cooldown) * points.size());
		IntStream.range(0, cooldownBlocks).forEach(i -> {
			points.get(i).getBlock().setType(cooldownType);
		});
		IntStream.range(cooldownBlocks, points.size()).forEach(i -> {
			points.get(i).getBlock().setType(outlineType);
		});
	}

}
