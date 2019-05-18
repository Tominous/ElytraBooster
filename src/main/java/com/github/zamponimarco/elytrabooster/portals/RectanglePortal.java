package com.github.zamponimarco.elytrabooster.portals;

import java.util.List;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.outlines.PortalOutline;
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

	public RectanglePortal(ElytraBooster plugin, String id, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, PortalOutline outline,
			List<UnionPortal> portalsUnion, BoostTrail trail, int cooldown, String measures) {
		super(plugin, id, center, axis, initialVelocity, finalVelocity, boostDuration, outline,
				portalsUnion, trail, cooldown);
		initMeasures(measures);

		super.runPortalTask();
	}

	@Override
	protected void initMeasures(String measures) {
		String[] measuresArray = measures.split(";");
		this.halfLength = Double.valueOf(measuresArray[0]);
		this.halfHeight = Double.valueOf(measuresArray[1]);
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
