package com.github.zamponimarco.elytrabooster.outlines.pointsorters;

import java.util.Collections;
import java.util.List;

import org.bukkit.Location;

public class RandomPointSorter implements PointSorter {

	@Override
	public void sort(List<Location> points) {
		Collections.shuffle(points);
	}

}
