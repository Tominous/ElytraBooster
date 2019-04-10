
package com.github.zamponimarco.elytrabooster.portals;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

/**
 * Circle shaped portal class
 * 
 * @author Marco
 *
 */
public class CirclePortal extends AbstractPortal {

	double radius;

	public CirclePortal(ElytraBooster plugin, String id, boolean isBlock, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, String outlineType,
			List<AbstractPortal> portalsUnion, boolean hasSuperior, double radius) {
		super(plugin, id, isBlock, center, axis, initialVelocity, finalVelocity, boostDuration, outlineType,
				portalsUnion, hasSuperior);
		this.radius = isBlock ? radius + 0.1 : radius;

		super.runPortalTask();
	}

	@Override
	protected List<Location> getPoints() {
		return getCircle();
	}

	@Override
	protected boolean isInPortalArea(Location location, double epsilon) {
		Location distance = location.clone().subtract(center);
		double distanceX = Math.abs(distance.getX());
		double distanceY = Math.abs(distance.getY());
		double distanceZ = Math.abs(distance.getZ());

		switch (axis) {
		case 'x':
			return distanceX <= 1 && Math.hypot(distanceZ, distanceY) < radius - epsilon;
		case 'y':
			return distanceY <= 1 && Math.hypot(distanceX, distanceZ) < radius - epsilon;
		case 'z':
			return distanceZ <= 1 && Math.hypot(distanceY, distanceX) < radius - epsilon;
		}
		return false;
	}

	// TODO improve code readability
	private List<Location> getCircle() {
		World world = center.getWorld();
		int amount = isBlock ? 50 * (int) radius : (int) Math.floor(2 * Math.PI * radius);
		List<Location> locations = new ArrayList<Location>();
		double increment = (2 * Math.PI) / amount;

		for (int i = 0; i < amount; i++) {
			double angle = i * increment;

			double newX = axis == 'x' ? center.getX() : center.getX() + (radius * Math.cos(angle));
			double newY = axis == 'y' ? center.getY() : center.getY() + (radius * Math.cos(angle));
			double newZ = axis == 'z' ? center.getZ() : center.getZ() + (radius * Math.sin(angle));
			if (axis == 'z') {
				newY = center.getY() + (radius * Math.sin(angle));
			}

			Location newLocation = isBlock ? new Location(world, newX, newY, newZ).getBlock().getLocation()
					: new Location(world, newX, newY, newZ);
			if (!locations.contains(newLocation)) {
				locations.add(newLocation);
			}
		}

		return locations;
	}

}
