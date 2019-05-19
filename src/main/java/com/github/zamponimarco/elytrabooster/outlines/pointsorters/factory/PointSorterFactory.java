package com.github.zamponimarco.elytrabooster.outlines.pointsorters.factory;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.outlines.pointsorters.ClosingPointSorter;
import com.github.zamponimarco.elytrabooster.outlines.pointsorters.PointSorter;
import com.github.zamponimarco.elytrabooster.outlines.pointsorters.RandomPointSorter;

public class PointSorterFactory {

	public static PointSorter buildPointSorter(String sorter, Location center) {
		switch(sorter) {
		case "random":
			return new RandomPointSorter();
		case "closing":
		default:
			return new ClosingPointSorter(center);
		}
		
	}
	
}
