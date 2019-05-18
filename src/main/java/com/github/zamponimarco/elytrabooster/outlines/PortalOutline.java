package com.github.zamponimarco.elytrabooster.outlines;

import java.util.List;

import org.bukkit.Location;

/**
 * Manages the creation of portal outline
 * 
 * @author Marco
 *
 */
public interface PortalOutline {

	/**
	 * Draws the outline 
	 * 
	 * @param points
	 */
	public void drawOutline(List<Location> points);

	/**
	 * Erases the outline
	 * 
	 * @param points
	 */
	public void eraseOutline(List<Location> points);
	
	/**
	 * Handle the outline of portals in cooldown
	 * 
	 * @param points
	 */
	public void cooldownOutline(List<Location> points, int cooldown, int progress);

}
