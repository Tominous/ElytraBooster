package com.github.zamponimarco.elytrabooster.portals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.events.PlayerBoostEvent;
import com.github.zamponimarco.elytrabooster.outline.BlockPortalOutline;
import com.github.zamponimarco.elytrabooster.outline.PortalOutline;
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
	protected Location center;
	protected char axis;
	protected double initialVelocity;
	protected double finalVelocity;
	protected int boostDuration;
	protected PortalOutline outline;
	protected List<UnionPortal> portalsUnion;
	protected BoostTrail trail;
	protected int cooldown;

	protected int outlineTaskNumber;
	protected int checkTaskNumber;
	protected List<Location> points;
	protected int currCooldown;

	private int outlineInterval;
	private int checkInterval;

	// ---

	public AbstractPortal(ElytraBooster plugin, String id, Location center, char axis, double initialVelocity,
			double finalVelocity, int boostDuration, PortalOutline outline, List<UnionPortal> portalsUnion,
			BoostTrail trail, int cooldown) {
		super();
		this.plugin = plugin;
		this.id = id;
		this.center = center;
		this.axis = axis;
		this.initialVelocity = initialVelocity;
		this.finalVelocity = finalVelocity;
		this.boostDuration = boostDuration;
		this.outline = outline;
		this.portalsUnion = portalsUnion;
		this.trail = trail;
		this.cooldown = cooldown;

		currCooldown = 0;

		this.outlineInterval = Integer
				.valueOf(plugin.getSettingsManager().getSetting(Settings.PORTAL_OUTLINE_INTERVAL));
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

	/**
	 * Stops the portal task
	 */
	public void stopPortalTask() {
		outline.eraseOutline(points);
		plugin.getServer().getScheduler().cancelTask(outlineTaskNumber);
		plugin.getServer().getScheduler().cancelTask(checkTaskNumber);
	}

	protected void checkPlayersPassing() {
		plugin.getStatusMap().keySet().forEach(player -> {
			if (isInUnionPortalArea(player.getLocation(), 0) && !plugin.getStatusMap().get(player) && !onCooldown()) {
				Bukkit.getPluginManager().callEvent(new PlayerBoostEvent(plugin, player, this));
				cooldown();
			}
		});
	}

	protected void drawOutline() {
		outline.drawOutline(points);
	}

	protected void cooldown() {
		currCooldown = cooldown;
		BukkitRunnable cooldownProcess = new BukkitRunnable() {
			@Override
			public void run() {
				if (currCooldown > 0) {
					currCooldown--;
				} else {
					this.cancel();
				}
			}
		};
		cooldownProcess.runTaskTimer(plugin, 0, 1);
	}

	protected boolean onCooldown() {
		return currCooldown > 0;
	}

	protected boolean isUnion() {
		return !portalsUnion.isEmpty();
	}

	protected boolean isInUnionPortalArea(Location location, double epsilon) {
		boolean test = isInPortalArea(location, epsilon);
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
		double epsilon = outline instanceof BlockPortalOutline ? 1 : 0.05;
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
