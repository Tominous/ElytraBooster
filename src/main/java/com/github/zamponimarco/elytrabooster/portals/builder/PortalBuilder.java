package com.github.zamponimarco.elytrabooster.portals.builder;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.portals.CirclePortal;
import com.github.zamponimarco.elytrabooster.portals.SquarePortal;

/**
 * Manages the creation of portals from a ConfigurationSection of portals.yml
 * 
 * @author Marco
 *
 */
public class PortalBuilder {

	// TODO yeah, make some checks
	public static AbstractPortal buildPortal(ElytraBooster plugin, ConfigurationSection portalConfiguration) {
		String id = portalConfiguration.getName();
		boolean isBlock = portalConfiguration.getBoolean("isBlockOutline");
		World world = plugin.getServer().getWorld(portalConfiguration.getString("world"));
		double x = portalConfiguration.getDouble("x");
		double y = portalConfiguration.getDouble("y");
		double z = portalConfiguration.getDouble("z");
		Location center = new Location(world, x, y, z);
		char axis = portalConfiguration.getString("axis").charAt(0);
		double initialVelocity = portalConfiguration.getDouble("initialVelocity");
		double finalVelocity = portalConfiguration.getDouble("finalVelocity");
		int boostDuration = portalConfiguration.getInt("boostDuration");
		String outlineType = portalConfiguration.getString("outlineType");
		String shape = portalConfiguration.getString("shape");
		String measures = portalConfiguration.getString("measures");
		switch (shape) {
		case "circle":
			return new CirclePortal(plugin, id, isBlock, center, axis, initialVelocity, finalVelocity, boostDuration,
					outlineType, Double.valueOf(measures));
		case "square":
			return new SquarePortal(plugin, id, isBlock, center, axis, initialVelocity, finalVelocity, boostDuration,
					outlineType, Double.valueOf(measures));
		}
		throw new NullPointerException("Portal creation failed, id: " + id);
	}
}
