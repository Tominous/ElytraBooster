package com.github.zamponimarco.elytrabooster.portals;

import java.util.List;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.portals.utils.PortalUtils;

public class UnionPortal extends AbstractPortal {

	private String shape;
	private String measures;
	private boolean intersecate;

	public UnionPortal(ElytraBooster plugin, String id, boolean isBlock, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, String outlineType,
			List<UnionPortal> portalsUnion, String shape, String measures, boolean intersecate) {
		super(plugin, id, isBlock, center, axis, initialVelocity, finalVelocity, boostDuration, outlineType,
				portalsUnion);
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
		}
		return false;
	}

	public boolean isIntersecate() {
		return intersecate;
	}

}
