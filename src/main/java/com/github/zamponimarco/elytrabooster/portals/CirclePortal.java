
package com.github.zamponimarco.elytrabooster.portals;

import java.util.List;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.outline.BlockPortalOutline;
import com.github.zamponimarco.elytrabooster.outline.PortalOutline;
import com.github.zamponimarco.elytrabooster.portals.utils.PortalUtils;
import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

/**
 * Circle shaped portal class
 * 
 * @author Marco
 *
 */
public class CirclePortal extends AbstractPortal {

	double radius;

	public CirclePortal(ElytraBooster plugin, String id, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, PortalOutline outline,
			List<UnionPortal> portalsUnion, BoostTrail trail, int cooldown, double radius) {
		super(plugin, id, center, axis, initialVelocity, finalVelocity, boostDuration, outline,
				portalsUnion, trail, cooldown);
		this.radius = radius;

		super.runPortalTask();
	}

	@Override
	protected List<Location> getPoints() {
		return PortalUtils.getCircle(center, outline instanceof BlockPortalOutline, radius, axis);
	}

	@Override
	protected boolean isInPortalArea(Location location, double epsilon) {
		return PortalUtils.isInCirclePortalArea(location, center, radius, axis, epsilon);
	}

}
