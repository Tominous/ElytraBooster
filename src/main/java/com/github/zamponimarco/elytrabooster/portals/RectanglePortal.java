package com.github.zamponimarco.elytrabooster.portals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

/**
 * Square shaped portal class
 * 
 * @author Marco
 *
 */
public class RectanglePortal extends AbstractPortal {

	double halfLength;
	double halfHeight;

	public RectanglePortal(ElytraBooster plugin, String id, boolean isBlock, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, String outlineType,
			List<AbstractPortal> portalsUnion, boolean hasSuperior, String measures) {
		super(plugin, id, isBlock, center, axis, initialVelocity, finalVelocity, boostDuration, outlineType,
				portalsUnion, hasSuperior);
		String[] measuresArray = measures.split(";");
		this.halfLength = Double.valueOf(measuresArray[0]);
		this.halfHeight = Double.valueOf(measuresArray[1]);

		super.runPortalTask();
	}

	@Override
	protected List<Location> getPoints() {
		return getSquare();
	}

	@Override
	protected boolean isInPortalArea(Location location, double epsilon) {
		Location distance = location.clone().subtract(center);
		double distanceX = Math.abs(distance.getX());
		double distanceY = Math.abs(distance.getY());
		double distanceZ = Math.abs(distance.getZ());

		boolean isInX = axis == 'x' ? distanceX <= 1 : distanceX < halfLength;
		boolean isInY = axis == 'y' ? distanceY <= 1 : distanceY < halfLength;
		isInY = axis == 'z' ? distanceY < halfHeight : isInY;
		boolean isInZ = axis == 'z' ? distanceZ <= 1 : distanceZ < halfHeight;

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

	private List<Location> splitLine(Location[] line) {
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
