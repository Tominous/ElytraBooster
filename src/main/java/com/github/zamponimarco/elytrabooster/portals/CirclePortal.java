
package com.github.zamponimarco.elytrabooster.portals;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

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
			double initialVelocity, double finalVelocity, int boostDuration, String outlineType, double radius) {
		super(plugin, id, isBlock, center, axis, initialVelocity, finalVelocity, boostDuration, outlineType);
		this.radius = radius;
		this.points = getPoints();

		super.runPortalTask();
	}

	@Override
	protected List<Location> getPoints() {
		return getCircle();
	}

	@Override
	protected boolean isInPortalArea(Player player) {
		Location distance = player.getLocation().subtract(center);
		double distanceX = Math.abs(distance.getX());
		double distanceY = Math.abs(distance.getY());
		double distanceZ = Math.abs(distance.getZ());

		switch (axis) {
		case 'x':
			return distanceX <= 1 && Math.hypot(distanceZ, distanceY) <= radius;
		case 'y':
			return distanceY <= 1 && Math.hypot(distanceX, distanceZ) <= radius;
		case 'z':
			return distanceZ <= 1 && Math.hypot(distanceY, distanceX) <= radius;
		}
		return false;
	}

	// TODO improve code readability
	private List<Location> getCircle() {
		World world = center.getWorld();
		int amount = isBlock ? 50 * (int) radius : 10 * (int) radius;
		double increment = (2 * Math.PI) / amount;
		List<Location> locations = new ArrayList<Location>();
		
		for (int i = 0; i < amount; i++) {
			double angle = i * increment;
			double newX = axis == 'x' ? center.getX() : center.getX() + (radius * Math.cos(angle));
			double newY = axis == 'y' ? center.getY() : center.getY() + (radius * Math.cos(angle));
			double newZ = axis == 'z' ? center.getZ() : center.getZ() + (radius * Math.sin(angle));
			if (axis == 'z') {
				newY = center.getY() + (radius * Math.sin(angle));
			}
			Location newLocation = new Location(world, newX, newY, newZ);
			newLocation = isBlock?newLocation.getBlock().getLocation():newLocation;
			if(!locations.contains(newLocation)) {				
				locations.add(newLocation);
			}
		}
		System.out.println(locations.size());
		return locations;
	}

}
