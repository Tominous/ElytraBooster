package com.github.zamponimarco.elytrabooster.portals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

/**
 * Square shaped portal class
 * 
 * @author Marco
 *
 */
public class SquarePortal extends AbstractPortal {

	double halfWidth;

	public SquarePortal(ElytraBooster plugin, String id, boolean isBlock, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, String outlineType, double halfWidth) {
		super(plugin, id, isBlock, center, axis, initialVelocity, finalVelocity, boostDuration, outlineType);
		this.halfWidth = halfWidth;
		this.points = getPoints();

		super.runPortalTask();
	}

	@Override
	protected List<Location> getPoints() {
		return getSquare();
	}

	@Override
	protected boolean isInPortalArea(Player player) {
		Location distance = player.getLocation().subtract(center);
		double distanceX = Math.abs(distance.getX());
		double distanceY = Math.abs(distance.getY());
		double distanceZ = Math.abs(distance.getZ());

		boolean isInX = axis == 'x' ? distanceX <= 1 : distanceX <= halfWidth / 2;
		boolean isInY = axis == 'y' ? distanceY <= 1 : distanceY <= halfWidth / 2;
		boolean isInZ = axis == 'z' ? distanceZ <= 1 : distanceZ <= halfWidth / 2;

		return isInX && isInY && isInZ;
	}

	private List<Location> getSquare() {
		List<Location> locations = new ArrayList<Location>();
		getLines().forEach(line -> locations.addAll(splitLine(line)));
		return locations;
	}

	private Set<Location[]> getLines() {
		Set<Location[]> linesSet = new HashSet<Location[]>();
		Location point1 = null;
		Location point2 = null;
		Location point3 = null;
		Location point4 = null;
		switch (axis) {
		case 'x':
			point1 = center.clone().add(0, -halfWidth, -halfWidth);
			point2 = center.clone().add(0, halfWidth, -halfWidth);
			point3 = center.clone().add(0, halfWidth, halfWidth);
			point4 = center.clone().add(0, -halfWidth, halfWidth);
			break;
		case 'y':
			point1 = center.clone().add(-halfWidth, 0, -halfWidth);
			point2 = center.clone().add(halfWidth, 0, -halfWidth);
			point3 = center.clone().add(halfWidth, 0, halfWidth);
			point4 = center.clone().add(-halfWidth, 0, halfWidth);
			break;
		case 'z':
			point1 = center.clone().add(-halfWidth, -halfWidth, 0);
			point2 = center.clone().add(halfWidth, -halfWidth, 0);
			point3 = center.clone().add(halfWidth, halfWidth, 0);
			point4 = center.clone().add(-halfWidth, halfWidth, 0);
			break;
		}
		linesSet.add(new Location[] { point1, point2 });
		linesSet.add(new Location[] { point2, point3 });
		linesSet.add(new Location[] { point3, point4 });
		linesSet.add(new Location[] { point4, point1 });
		return linesSet;
	}

	private List<Location> splitLine(Location[] line) {
		int amount = isBlock ? (int) halfWidth : 2 * (int) halfWidth;
		Location decrement = line[0].clone().subtract(line[1]).multiply(1.0 / amount);
		List<Location> linePoints = new ArrayList<Location>();
		Location loc = line[0].clone();
		for (int i = 0; i < amount; i++) {
			linePoints.add(loc);
			loc = loc.subtract(decrement).clone();
		}

		return linePoints;
	}

}
