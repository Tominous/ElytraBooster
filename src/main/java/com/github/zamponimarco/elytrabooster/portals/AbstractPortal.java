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
import com.github.zamponimarco.elytrabooster.settings.Settings;
import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

/**
 * Handles portal boost process
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
	protected List<UnionPortal> portalsUnion;
	protected BoostTrail trail;

	protected int outlineTaskNumber;
	protected int checkTaskNumber;
	protected List<Location> points;

	private int outlineInterval;
	private int checkInterval;

	// ---

	public AbstractPortal(ElytraBooster plugin, String id, boolean isBlock, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, String outlineType,
			List<UnionPortal> portalsUnion, BoostTrail trail) {
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
		this.trail = trail;

		this.outlineInterval = Integer.valueOf(plugin.getSettingsManager().getSetting(Settings.PORTAL_OUTLINE_INTERVAL));
		this.checkInterval = Integer.valueOf(plugin.getSettingsManager().getSetting(Settings.PORTAL_CHECK_INTERVAL));
	}

	// Abstract methods area ---

	/**
	 * Depends on portal shape -> abstract method
	 * 
	 * @return The sets of point of the portal
	 */
	protected abstract List<Location> getPoints();

	// ---

	/**
	 * Depends on portal shape -> abstract method
	 * 
	 * @param location
	 * @param epsilon
	 * @return true if the location is in the portal area
	 */
	protected abstract boolean isInPortalArea(Location location, double epsilon);

	// ---

	/**
	 * Runs the timer task that checks for users inside the portal and draws the
	 * outline
	 */
	protected void runPortalTask() {
		points = isUnion() ? getUnionPoints() : getPoints();
		outlineTaskNumber = plugin.getServer().getScheduler()
				.runTaskTimer(plugin, () -> drawOutline(), 1, outlineInterval).getTaskId();
		checkTaskNumber = plugin.getServer().getScheduler()
				.runTaskTimer(plugin, () -> checkPlayersPassing(), 1, checkInterval).getTaskId();
	}

	// Getters and setters area ---

	/**
	 * Stops the portal task
	 */
	public void stopPortalTask() {
		if (isBlock) {
			drawOutline("AIR");
		}
		plugin.getServer().getScheduler().cancelTask(outlineTaskNumber);
		plugin.getServer().getScheduler().cancelTask(checkTaskNumber);
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

	protected void drawOutline() {
		drawOutline(outlineType);
	}

	// ---

	protected boolean isUnion() {
		return !portalsUnion.isEmpty();
	}

	// Getters and setters area ---

	protected boolean isInUnionPortalArea(Location location, double epsilon) {
		boolean test = false;
		if (isInPortalArea(location, epsilon)) {
			test = true;
		}
		for (UnionPortal p : portalsUnion) {
			if (p.isIntersecate()) {
				test = test && p.isInPortalArea(location, epsilon);
			} else {
				test = test || p.isInPortalArea(location, epsilon);
			}
		}
		return test;
	}

	protected List<Location> getUnionPoints() {
		List<Location> unionPoints = new ArrayList<Location>();
		double epsilon = isBlock ? 1 : 0.05;
		unionPoints.addAll(getPoints());
		for (UnionPortal portal : portalsUnion) {
			unionPoints.addAll(portal.getPoints());
			if (portal.isIntersecate()) {
				unionPoints = unionPoints.stream().filter(point -> isInUnionPortalArea(point, -epsilon))
						.collect(Collectors.toList());
			} else {
				unionPoints = unionPoints.stream().filter(point -> !isInUnionPortalArea(point, epsilon))
						.collect(Collectors.toList());
			}
		}
		return unionPoints;
	}

	/**
	 * Generates a string that represents the portal
	 */
	@Override
	public String toString() {
		return id + "\n";
	}

	// Getters and setters area ---

	/**
	 * 
	 * @return the center of the portal
	 */
	public Location getCenter() {
		return center;
	}

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

	public BoostTrail getTrail() {
		return trail;
	}

	// ---

}
