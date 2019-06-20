package com.github.zamponimarco.elytrabooster.outlines.pointsorters;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Location;

public class ClosingPointSorter implements PointSorter {

	private Location center;

	private Comparator<Location> locationComparator = new Comparator<Location>() {
		@Override
		public int compare(Location p1, Location p2) {
			double d1 = Math.abs(p1.clone().toVector().dot(center.toVector()));
			double d2 = Math.abs(p2.clone().toVector().dot(center.toVector()));
			return (int) (d1 - d2);
		}
	};

	public ClosingPointSorter(Location center) {
		this.center = center;
	}

	@Override
	public void sort(List<Location> points) {
		Collections.sort(points, locationComparator);
	}

	@Override
	public String getName() {
		return "closing";
	}

}
