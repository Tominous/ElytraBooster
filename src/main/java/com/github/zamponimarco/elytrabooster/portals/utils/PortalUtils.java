package com.github.zamponimarco.elytrabooster.portals.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Handles calculations for portals
 * 
 * @author Marco
 *
 */
public class PortalUtils {

	/**
	 * Algorithm to find the points of a circle
	 * 
	 * @param center
	 * @param isBlock
	 * @param radius
	 * @param axis
	 * @return list of circle points
	 */
	public static List<Location> getCircle(Location center, boolean isBlock, double radius, char axis) {
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

			Location newLocation = isBlock
					? world.getBlockAt((int) Math.round(newX), (int) Math.round(newY), (int) Math.round(newZ))
							.getLocation()
					: new Location(world, newX, newY, newZ);
			if (!locations.contains(newLocation)) {
				locations.add(newLocation);
			}
		}

		return locations;
	}

	/**
	 * AAlorithm to determine if a location is in the portal area
	 * 
	 * @param location
	 * @param center
	 * @param radius
	 * @param axis
	 * @param epsilon
	 * @return if a location is in the portal area
	 */
	public static boolean isInCirclePortalArea(Location location, Location center, double radius, char axis,
			double epsilon) {
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

	/**
	 * Algorithm to determine rectangle points
	 * 
	 * @param center
	 * @param axis
	 * @param halfLength
	 * @param halfHeight
	 * @return list of rectangle points
	 */
	public static List<Location> getRectangle(Location center, char axis, double halfLength, double halfHeight) {
		List<Location> locations = new ArrayList<Location>();
		getSquareLines(center, axis, halfLength, halfHeight).forEach(line -> locations.addAll(splitLine(line)));
		return locations;
	}

	public static boolean isInRectanglePortalArea(Location center, double halfHeight, double halfLength, char axis,
			Location location, double epsilon) {
		Location distance = location.clone().subtract(center);
		double distanceX = Math.abs(distance.getX());
		double distanceY = Math.abs(distance.getY());
		double distanceZ = Math.abs(distance.getZ());

		boolean isInX = axis == 'x' ? distanceX <= 1 : distanceX < halfLength - epsilon;
		boolean isInY = axis == 'y' ? distanceY <= 1 : distanceY < halfLength - epsilon;
		isInY = axis == 'z' ? distanceY < halfHeight - epsilon : isInY;
		boolean isInZ = axis == 'z' ? distanceZ <= 1 : distanceZ < halfHeight - epsilon;

		return isInX && isInY && isInZ;
	}

	/**
	 * Algorithm to determine triangle points
	 * 
	 * @param point1
	 * @param point2
	 * @param point3
	 * @param axis
	 * @return list of triangle points
	 */
	public static List<Location> getTriangle(Location point1, Location point2, Location point3, char axis) {
		List<Location> locations = new ArrayList<Location>();
		getTriangleLines(point1, point2, point3, axis).forEach(line -> locations.addAll(splitLine(line)));
		return locations;
	}

	/**
	 * Algoritm that checks if a location is inside a triangle
	 * 
	 * @param location
	 * @param point1
	 * @param point2
	 * @param point3
	 * @param axis
	 * @param epsilon
	 * @return true if the location is in the triangle
	 */
	public static boolean isInTrianglePortalArea(Location location, Location point1, Location point2, Location point3,
			char axis, double epsilon) {
		double axisDistance = 0;
		switch (axis) {
		case 'x':
			axisDistance = Math.abs((location.getX() - point1.getX()));
			break;
		case 'y':
			axisDistance = Math.abs((location.getY() - point1.getY()));
			break;
		case 'z':
			axisDistance = Math.abs((location.getZ() - point1.getZ()));
			break;
		}

		boolean d12 = sign(location, point1, point2, axis, epsilon);
		boolean d23 = sign(location, point2, point3, axis, epsilon);
		boolean d31 = sign(location, point3, point1, axis, epsilon);
		boolean isInArea = (d12 && d23 && d31) || (!d12 && !d23 && !d31);
		return isInArea && axisDistance <= 1;
	}

	private static boolean sign(Location p1, Location p2, Location p3, char axis, double epsilon) {
		double x1 = axis == 'x' ? p1.getZ() : p1.getX();
		double x2 = axis == 'x' ? p2.getZ() : p2.getX();
		double x3 = axis == 'x' ? p3.getZ() : p3.getX();
		double y1 = axis == 'y' ? p1.getX() : p1.getY();
		double y2 = axis == 'y' ? p2.getX() : p2.getY();
		double y3 = axis == 'y' ? p3.getX() : p3.getY();
		if (axis == 'y') {
			y1 = p1.getZ();
			y2 = p2.getZ();
			y3 = p3.getZ();
		}

		return (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3) >= -epsilon;
	}

	private static Set<Location[]> getSquareLines(Location center, char axis, double halfLength, double halfHeight) {
		Set<Location[]> linesSet = new HashSet<Location[]>();
		Location point1 = null;
		Location point2 = null;
		Location point3 = null;
		Location point4 = null;
		switch (axis) {
		case 'x':
			point1 = center.clone().add(0, -halfLength, -halfHeight);
			point2 = center.clone().add(0, halfLength, -halfHeight);
			point3 = center.clone().add(0, halfLength, halfHeight);
			point4 = center.clone().add(0, -halfLength, halfHeight);
			break;
		case 'y':
			point1 = center.clone().add(-halfLength, 0, -halfHeight);
			point2 = center.clone().add(halfLength, 0, -halfHeight);
			point3 = center.clone().add(halfLength, 0, halfHeight);
			point4 = center.clone().add(-halfLength, 0, halfHeight);
			break;
		case 'z':
			point1 = center.clone().add(-halfLength, -halfHeight, 0);
			point2 = center.clone().add(halfLength, -halfHeight, 0);
			point3 = center.clone().add(halfLength, halfHeight, 0);
			point4 = center.clone().add(-halfLength, halfHeight, 0);
			break;
		}
		linesSet.add(new Location[] { point1, point2 });
		linesSet.add(new Location[] { point2, point3 });
		linesSet.add(new Location[] { point3, point4 });
		linesSet.add(new Location[] { point4, point1 });
		return linesSet;
	}

	private static Set<Location[]> getTriangleLines(Location point1, Location point2, Location point3, char axis) {
		Set<Location[]> linesSet = new HashSet<Location[]>();
		linesSet.add(new Location[] { point1, point2 });
		linesSet.add(new Location[] { point2, point3 });
		linesSet.add(new Location[] { point3, point1 });
		return linesSet;

	}

	private static List<Location> splitLine(Location[] line) {
		int amount = (int) line[0].distance(line[1]);
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
