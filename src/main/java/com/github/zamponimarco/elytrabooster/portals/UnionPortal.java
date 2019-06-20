package com.github.zamponimarco.elytrabooster.portals;

import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.outlines.BlockPortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.PortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.pointsorters.PointSorter;
import com.github.zamponimarco.elytrabooster.portals.utils.PortalUtils;
import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

public class UnionPortal extends AbstractPortal {

	private String shape;
	private String measures;
	private Object measures1;
	private Object measures2;
	private boolean intersecate;

	public UnionPortal(ElytraBooster plugin, String id, Location center, char axis, double initialVelocity,
			double finalVelocity, int boostDuration, PortalOutline outline, List<UnionPortal> portalsUnion,
			BoostTrail trail, String shape, int cooldown, String measures, PointSorter sorter, boolean intersecate) {
		super(plugin, id, center, axis, initialVelocity, finalVelocity, boostDuration, outline, portalsUnion, trail,
				cooldown, sorter, measures);
		this.shape = shape;
		this.measures = measures;
		this.intersecate = intersecate;
		try {
			initMeasures();
			points = getPoints();
		} catch (Exception e) {
			Bukkit.getLogger().warning(warnMessage());
		}
	}

	@Override
	protected void initMeasures() throws IllegalArgumentException {
		switch (shape) {
		case "circle":
			this.measures1 = Double.valueOf(measures);
			break;
		case "square":
			this.measures1 = Double.valueOf(measures);
			break;
		case "rectangle":
			String[] measuresArray = measures.split(";");
			measures1 = Double.valueOf(measuresArray[0]);
			measures2 = Double.valueOf(measuresArray[1]);
			break;
		case "triangle":
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
				point2 = new Location(center.getWorld(), Double.valueOf(measuresTriangleArray[0]),
						Double.valueOf(measuresTriangleArray[1]), center.getZ());
				point3 = new Location(center.getWorld(), Double.valueOf(measuresTriangleArray[2]),
						Double.valueOf(measuresTriangleArray[3]), center.getZ());
				break;
			}
			this.measures1 = point2;
			this.measures2 = point3;
			break;
		case "default":
			break;
		}
	}

	@Override
	protected List<Location> getPoints() {
		switch (shape) {
		case "circle":
			return PortalUtils.getCircle(center, outline instanceof BlockPortalOutline, (double) measures1, axis);
		case "square":
			return PortalUtils.getRectangle(center, axis, (double) measures1, (double) measures1);
		case "rectangle":
			return PortalUtils.getRectangle(center, axis, (double) measures1, (double) measures2);
		case "triangle":
			return PortalUtils.getTriangle(center, (Location) measures1, (Location) measures2, axis);
		}
		return points;
	}

	@Override
	protected boolean isInPortalArea(Location location, double epsilon) {
		switch (shape) {
		case "circle":
			return PortalUtils.isInCirclePortalArea(location, center, (double) measures1, axis, epsilon);
		case "square":
			return PortalUtils.isInRectanglePortalArea(center, (double) measures1, (double) measures1, axis, location,
					epsilon);
		case "rectangle":
			return PortalUtils.isInRectanglePortalArea(center, (double) measures1, (double) measures2, axis, location,
					epsilon);
		case "triangle":
			return PortalUtils.isInTrianglePortalArea(location, center, (Location) measures1, (Location) measures2,
					axis, epsilon);
		}
		return false;
	}

	public boolean isIntersecate() {
		return intersecate;
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "%s:%.1f:%.1f:%.1f:%s:%b", shape, center.getX(), center.getY(), center.getZ(),
				measures, intersecate);
	}

	@Override
	public String getShape() {
		return null;
	}
}