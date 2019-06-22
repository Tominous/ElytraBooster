package com.github.zamponimarco.elytrabooster.boosters.portals;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.boosters.portals.utils.PortalUtils;
import com.github.zamponimarco.elytrabooster.boosts.Boost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.outlines.PortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.pointsorters.PointSorter;

/**
 * Square shaped portal class
 * 
 * @author Marco
 *
 */
public class RectanglePortal extends AbstractPortal {

	double halfLength;
	double halfHeight;

	public RectanglePortal(ElytraBooster plugin, String id, Location center, char axis, Boost boost, PortalOutline outline,
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

	@Override
	public String getShape() {
		return halfHeight == halfHeight? "square" : "rectangle";
	}

}
