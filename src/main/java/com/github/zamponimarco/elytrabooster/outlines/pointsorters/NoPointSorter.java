package com.github.zamponimarco.elytrabooster.outlines.pointsorters;

import java.util.List;

import org.bukkit.Location;

public class NoPointSorter implements PointSorter {

	@Override
	public void sort(List<Location> points) {
	}

	@Override
	public String getName() {
		return "none";
	}

}
