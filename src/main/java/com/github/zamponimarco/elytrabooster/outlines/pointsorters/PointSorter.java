package com.github.zamponimarco.elytrabooster.outlines.pointsorters;

import java.util.List;

import org.bukkit.Location;

public interface PointSorter {

	public void sort(List<Location> points); 
	
	public String getName();
	
}
