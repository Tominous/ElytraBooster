package com.github.zamponimarco.elytrabooster.portals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.events.PlayerBoostEvent;

/**
 * Generic portal class
 * 
 * @author Marco
 *
 */
public abstract class AbstractPortal {

	// Instance variables area ---

	protected ElytraBooster plugin;
	protected String id;
	protected boolean isBlock;
	protected Location center;
	protected char axis;
	protected double initialVelocity;
	protected double finalVelocity;
	protected int boostDuration;
	protected String outlineType;
	protected List<AbstractPortal> portalsUnion;
	protected boolean hasSuperior;

	protected int taskNumber;
	protected List<Location> points;

	// ---

	// TODO rewatch code, add other things
	public AbstractPortal(ElytraBooster plugin, String id, boolean isBlock, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, String outlineType,
			List<AbstractPortal> portalsUnion, boolean hasSuperior) {
		super();
		this.plugin = plugin;
		this.id = id;
		this.isBlock = isBlock;
		this.center = center;
		this.axis = axis;
		this.initialVelocity = initialVelocity;
		this.finalVelocity = finalVelocity;
		this.boostDuration = boostDuration;
		this.outlineType = outlineType;
		this.portalsUnion = portalsUnion;
		this.hasSuperior = hasSuperior;
	}

	// Abstract methods area ---

	/**
	 * Depends on portal shape -> abstract method
	 * 
	 * @return The sets of point of the portal
	 */
	protected abstract List<Location> getPoints();

	/**
	 * Depends on portal shape -> abstract method
	 * 
	 * @param location
	 * @param epsilon
	 * @return true if the location is in the portal area
	 */
	protected abstract boolean isInPortalArea(Location location, double epsilon);

	// ---

	protected boolean isUnion() {
		return !portalsUnion.isEmpty();
	}

	/**
	 * Runs the timer task that checks for users inside the portal and draws the
	 * outline
	 */
	protected void runPortalTask() {
		if (!hasSuperior) {
			points = isUnion() ? getUnionPoints() : getPoints();
			taskNumber = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
				checkPlayersPassing();
				drawOutline(outlineType);
			}, 1, 1).getTaskId();
		}
	}

	/**
	 * Doesn't depend on portal shape -> not abstract method
	 * 
	 * Checks if a player passes in the portal area
	 */
	protected void checkPlayersPassing() {
		if (!isUnion()) {
			plugin.getStatusMap().keySet().forEach(player -> {
				if (isInPortalArea(player.getLocation(), 0) && !plugin.getStatusMap().get(player))
					Bukkit.getPluginManager().callEvent(new PlayerBoostEvent(plugin, player, this));
			});
		} else {
			plugin.getStatusMap().keySet().forEach(player -> {
				if (isInUnionPortalArea(player.getLocation(), 0) && !plugin.getStatusMap().get(player))
					Bukkit.getPluginManager().callEvent(new PlayerBoostEvent(plugin, player, this));
			});
		}
	}

	/**
	 * Doesn't depend on portal shape -> not abstract method
	 * 
	 * Draws the outline of the portal
	 */
	protected void drawOutline(String outlineType) {
		if (isBlock) {
			Material m = Material.valueOf(outlineType);
			points.forEach(point -> point.getBlock().setType(m));
		} else {
			Particle p = Particle.valueOf(outlineType);
			points.forEach(point -> point.getWorld().spawnParticle(p, point, 0));
		}
	}

	/**
	 * Stops the portal task
	 */
	public void stopPortalTask() {
		if (isBlock && !hasSuperior) {
			drawOutline("AIR");
		}
		plugin.getServer().getScheduler().cancelTask(taskNumber);
	}

	protected boolean isInUnionPortalArea(Location location, double epsilon) {
		if (isInPortalArea(location, epsilon)) {
			return true;
		}
		for (AbstractPortal portal : portalsUnion) {
			if (portal.isInPortalArea(location, epsilon)) {
				return true;
			}
		}
		return false;
	}

	protected List<Location> getUnionPoints() {
		List<Location> unionPoints = new ArrayList<Location>();
		double epsilon = isBlock ? 1.0 : 0.05;
		unionPoints.addAll(getPoints());
		portalsUnion.forEach(portal -> unionPoints.addAll(portal.getPoints()));
		return unionPoints.stream().filter(point -> !isInUnionPortalArea(point, epsilon)).collect(Collectors.toList());
	}

	// Getters and setters area ---

	/**
	 * @return the initialVelocity
	 */
	public double getInitialVelocity() {
		return initialVelocity;
	}

	/**
	 * @return the finalVelocity
	 */
	public double getFinalVelocity() {
		return finalVelocity;
	}

	/**
	 * @return the boostDuration
	 */
	public int getBoostDuration() {
		return boostDuration;
	}

	/**
	 * @return the hasSuperior
	 */
	public boolean hasSuperior() {
		return hasSuperior;
	}

	// ---

}
