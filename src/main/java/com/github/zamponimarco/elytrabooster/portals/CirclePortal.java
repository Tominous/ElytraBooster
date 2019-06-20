
package com.github.zamponimarco.elytrabooster.portals;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.boosts.Boost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.outlines.BlockPortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.PortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.pointsorters.PointSorter;
import com.github.zamponimarco.elytrabooster.portals.utils.PortalUtils;

/**
 * Circle shaped portal class
 * 
 * @author Marco
 *
 */
public class CirclePortal extends AbstractPortal {

	double radius;

	public CirclePortal(ElytraBooster plugin, String id, Location center, char axis, Boost boost, PortalOutline outline,
			List<UnionPortal> portalsUnion, int cooldown, PointSorter sorter, String measures) {
		super(plugin, id, center, axis, boost, outline, portalsUnion, cooldown, sorter, measures);
		try {
			initMeasures();
			points = getUnionPoints();
			super.runPortalTask();
		} catch (Exception e) {
			Bukkit.getLogger().warning(warnMessage());
		}
	}

	@Override
	protected void initMeasures() {
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

	@Override
	public String getShape() {
		return "circle";
	}

}
