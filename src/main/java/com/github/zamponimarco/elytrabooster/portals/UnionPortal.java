package com.github.zamponimarco.elytrabooster.portals;

import java.util.List;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.portals.utils.PortalUtils;
import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

public class UnionPortal extends AbstractPortal {

	private String shape;
	private String measures;
	private boolean intersecate;

	public UnionPortal(ElytraBooster plugin, String id, boolean isBlock, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, String outlineType,
			List<UnionPortal> portalsUnion, BoostTrail trail, String shape, String measures, boolean intersecate) {
		super(plugin, id, isBlock, center, axis, initialVelocity, finalVelocity, boostDuration, outlineType,
				portalsUnion, trail);
		this.shape = shape;
		this.measures = measures;
		this.intersecate = intersecate;
		points = getPoints();
	}

	@Override
	protected List<Location> getPoints() {
		switch (shape) {
		case "circle":
			return PortalUtils.getCircle(center, isBlock, Double.valueOf(measures), axis);
		case "square":
			return PortalUtils.getRectangle(center, axis, Double.valueOf(measures), Double.valueOf(measures));
		case "rectangle":
			String[] measuresArray = measures.split(";");
			double halfLength = Double.valueOf(measuresArray[0]);
			double halfHeight = Double.valueOf(measuresArray[1]);
			return PortalUtils.getRectangle(center, axis, halfLength, halfHeight);
		case "triangle":
			Location [] points = initMeasures(measures);
			Location point2 = points[0];
			Location point3 = points[1];
			return PortalUtils.getTriangle(center, point2, point3, axis);
		}
		return points;
	}

	@Override
	protected boolean isInPortalArea(Location location, double epsilon) {
		switch (shape) {
		case "circle":
			return PortalUtils.isInCirclePortalArea(location, center, Double.valueOf(measures), axis, epsilon);
		case "square":
			return PortalUtils.isInRectanglePortalArea(location, Double.valueOf(measures), Double.valueOf(measures), axis, location, epsilon);
		case "rectangle":
			String[] measuresArray = measures.split(";");
			double halfLength = Double.valueOf(measuresArray[0]);
			double halfHeight = Double.valueOf(measuresArray[1]);
			return PortalUtils.isInRectanglePortalArea(location, halfHeight, halfLength, axis, location, epsilon);
		case "triangle":
			Location[] points = initMeasures(measures);
			Location point2 = points[0];
			Location point3 = points[1];
			return PortalUtils.isInTrianglePortalArea(location, center, point2, point3, axis, epsilon);
		}
			return false;
	}

	public boolean isIntersecate() {
		return intersecate;
	}

	private Location[] initMeasures(String measures2) {
		String[] measuresTriangleArray = measures.split(";");
		Location point2 = null;
		Location point3 = null;
		switch (axis) {
		case 'x':
			point2 = new Location(center.getWorld(), center.getX(), Double.valueOf(measuresTriangleArray[0]),
					Double.valueOf(measuresTriangleArray[1]));
			point3 = new Location(center.getWorld(), center.getX(), Double.valueOf(measuresTriangleArray[2]),
					Double.valueOf(measuresTriangleArray[3]));
			break;
		case 'y':
			point2 = new Location(center.getWorld(), Double.valueOf(measuresTriangleArray[0]), center.getY(),
					Double.valueOf(measuresTriangleArray[1]));
			point3 = new Location(center.getWorld(), Double.valueOf(measuresTriangleArray[2]), center.getY(),
					Double.valueOf(measuresTriangleArray[3]));
			break;
		case 'z':
			point2 = new Location(center.getWorld(), Double.valueOf(measuresTriangleArray[0]), Double.valueOf(measuresTriangleArray[1]),
					center.getZ());
			point3 = new Location(center.getWorld(), Double.valueOf(measuresTriangleArray[2]), Double.valueOf(measuresTriangleArray[3]),
					center.getZ());
			break;
		}
		return new Location[]{point2, point3};
	}
}