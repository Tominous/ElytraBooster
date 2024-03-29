package com.github.zamponimarco.elytrabooster.boosters.portals;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.boosters.portals.utils.PortalUtils;
import com.github.zamponimarco.elytrabooster.boosts.Boost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.outlines.PortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.pointsorters.PointSorter;

public class TrianglePortal extends AbstractPortal {

	private Location point2;
	private Location point3;

	public TrianglePortal(ElytraBooster plugin, String id, Location center, char axis, Boost boost, PortalOutline outline,
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
		switch (axis) {
		case 'x':
			point2 = new Location(center.getWorld(), center.getX(), Double.valueOf(measuresArray[0]),
					Double.valueOf(measuresArray[1]));
			point3 = new Location(center.getWorld(), center.getX(), Double.valueOf(measuresArray[2]),
					Double.valueOf(measuresArray[3]));
			break;
		case 'y':
			point2 = new Location(center.getWorld(), Double.valueOf(measuresArray[0]), center.getY(),
					Double.valueOf(measuresArray[1]));
			point3 = new Location(center.getWorld(), Double.valueOf(measuresArray[2]), center.getY(),
					Double.valueOf(measuresArray[3]));
			break;
		case 'z':
			point2 = new Location(center.getWorld(), Double.valueOf(measuresArray[0]), Double.valueOf(measuresArray[1]),
					center.getZ());
			point3 = new Location(center.getWorld(), Double.valueOf(measuresArray[2]), Double.valueOf(measuresArray[3]),
					center.getZ());
			break;
		}
	}

	@Override
	protected List<Location> getPoints() {
		return PortalUtils.getTriangle(center, point2, point3, axis);
	}

	@Override
	protected boolean isInPortalArea(Location location, double epsilon) {
		return PortalUtils.isInTrianglePortalArea(location, center, point2, point3, axis, epsilon);
	}

	@Override
	public Location getCenter() {
		double centerX = (center.getX() + point2.getX() + point3.getX()) / 3.0;
		double centerY = (center.getY() + point2.getX() + point3.getY()) / 3.0;
		double centerZ = (center.getZ() + point2.getZ() + point3.getZ()) / 3.0;
		return new Location(center.getWorld(), centerX, centerY, centerZ);
	}

	@Override
	public String getShape() {
		return "triangle";
	}

}
