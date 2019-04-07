package com.github.zamponimarco.elytrabooster.portals;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.events.PlayerBoostEvent;

/**
 * Generic portal class
 * 
 * @author Marco
 *
 */
public abstract class AbstractPortal {

	protected ElytraBooster plugin;
	protected String id;
	protected boolean isBlock;
	protected Location center;
	protected char axis;
	protected double initialVelocity;
	protected double finalVelocity;
	protected int boostDuration;
	protected String outlineType;

	protected int taskNumber;
	protected List<Location> points;

	// TODO rewatch code, add other things
	public AbstractPortal(ElytraBooster plugin, String id, boolean isBlock, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, String outlineType) {
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
	}

	/**
	 * Depends on portal shape -> abstract method
	 * 
	 * @return The sets of point of the portal
	 */
	protected abstract List<Location> getPoints();

	/**
	 * Depends on portal shape -> abstract method
	 * 
	 * Checks if the player is in the portal area
	 * 
	 * @param player
	 * @return true if the player is in the portal area
	 */
	protected abstract boolean isInPortalArea(Player player);

	/**
	 * Doesn't depend on portal shape -> not abstract method
	 * 
	 * Checks if a player passes in the portal area
	 */
	protected void checkPlayersPassing() {
		plugin.getStatusMap().keySet().forEach(player -> {
			if (isInPortalArea(player) && !plugin.getStatusMap().get(player)) {
				Bukkit.getPluginManager().callEvent(new PlayerBoostEvent(plugin, player, this));
			}
		});
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
	 * Runs the timer task that checks for users inside the portal and draws the
	 * outline
	 */
	protected void runPortalTask() {
		taskNumber = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
			checkPlayersPassing();
			drawOutline(outlineType);
		}, 1, 1).getTaskId();
	}

	public void stopPortalTask() {
		if (isBlock) {
			drawOutline("AIR");
		}
		plugin.getServer().getScheduler().cancelTask(taskNumber);
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

}
