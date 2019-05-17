package com.github.zamponimarco.elytrabooster.portals;

import java.util.List;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.portals.utils.PortalUtils;
import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

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
			List<UnionPortal> portalsUnion, BoostTrail trail, String measures) {
		super(plugin, id, isBlock, center, axis, initialVelocity, finalVelocity, boostDuration, outlineType,
				portalsUnion, trail);
		String[] measuresArray = measures.split(";");
		this.halfLength = Double.valueOf(measuresArray[0]);
		this.halfHeight = Double.valueOf(measuresArray[1]);

		super.runPortalTask();
	}

	@Override
	protected List<Location> getPoints() {
		return getRectangle();
	}

	@Override
	protected boolean isInPortalArea(Location location, double epsilon) {
		return PortalUtils.isInRectanglePortalArea(center, halfHeight, halfLength, axis, location, epsilon);
	}

	private List<Location> getRectangle() {
		return PortalUtils.getRectangle(center, axis, halfLength, halfHeight);
	}

}
