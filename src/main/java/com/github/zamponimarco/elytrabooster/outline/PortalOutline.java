package com.github.zamponimarco.elytrabooster.outline;

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

}
