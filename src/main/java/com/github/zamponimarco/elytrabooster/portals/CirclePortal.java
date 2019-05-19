
package com.github.zamponimarco.elytrabooster.portals;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.outlines.BlockPortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.PortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.pointsorters.PointSorter;
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

	public CirclePortal(ElytraBooster plugin, String id, Location center, char axis, double initialVelocity,
			double finalVelocity, int boostDuration, PortalOutline outline, List<UnionPortal> portalsUnion,
			BoostTrail trail, int cooldown, PointSorter sorter, String measures) {
		super(plugin, id, center, axis, initialVelocity, finalVelocity, boostDuration, outline, portalsUnion, trail,
				cooldown, sorter, measures);
		try {
			initMeasures(measures);
			points = getUnionPoints();
			super.runPortalTask();
		} catch (Exception e) {
			Bukkit.getLogger().warning(warnMessage());
		}
	}

	@Override
	protected void initMeasures(String measures) {
			radius = Double.valueOf(measures);
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
