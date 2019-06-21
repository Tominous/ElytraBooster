package com.github.zamponimarco.elytrabooster.portals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.zamponimarco.elytrabooster.boosts.Boost;
import com.github.zamponimarco.elytrabooster.core.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.events.FinishedCooldownEvent;
import com.github.zamponimarco.elytrabooster.events.PlayerBoostEvent;
import com.github.zamponimarco.elytrabooster.managers.BoosterManager;
import com.github.zamponimarco.elytrabooster.outlines.BlockPortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.PortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.pointsorters.PointSorter;
import com.github.zamponimarco.elytrabooster.settings.Settings;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

import net.md_5.bungee.api.ChatColor;

/**
 * Handles portal boost process
 * 
 * @author Marco
 *
 */
public abstract class AbstractPortal implements Booster{

	// Instance variables area ---

	protected ElytraBooster plugin;
	protected AbstractPortal portal;
	protected String id;
	protected Location center;
	protected char axis;
	protected Boost boost;
	protected PortalOutline outline;
	protected List<UnionPortal> portalsUnion;
	protected int cooldown;
	protected PointSorter sorter;
	protected String measures;

	protected int outlineTaskNumber;
	protected int checkTaskNumber;
	protected List<Location> points;
	protected int currCooldown;

	private int outlineInterval;
	private int checkInterval;

	// ---

	/**
	 * Creates a new abstract portal
	 * 
	 * @param plugin
	 * @param id
	 * @param center
	 * @param axis
	 * @param boost
	 * @param outline
	 * @param portalsUnion
	 * @param cooldown
	 * @param sorter
	 * @param measures
	 */
	public AbstractPortal(ElytraBooster plugin, String id, Location center, char axis, Boost boost,
			PortalOutline outline, List<UnionPortal> portalsUnion, int cooldown, PointSorter sorter, String measures) {
		super();
		this.plugin = plugin;
		this.portal = this;
		this.id = id;
		this.center = center;
		this.axis = axis >= 120 && axis <= 122 ? axis : 'x';
		this.boost = boost;
		this.outline = outline;
		this.portalsUnion = portalsUnion;
		this.cooldown = cooldown;
		this.sorter = sorter;
		this.measures = measures;

		currCooldown = 0;

		this.outlineInterval = Integer
				.valueOf(plugin.getSettingsManager().getSetting(Settings.PORTAL_OUTLINE_INTERVAL));
		this.checkInterval = Integer.valueOf(plugin.getSettingsManager().getSetting(Settings.PORTAL_CHECK_INTERVAL));
	}

	/**
	 * Initialize measures
	 * 
	 * @throws Exception
	 */
	protected abstract void initMeasures();

	/**
	 * Generates the list of points that represent the outline of a portal
	 * 
	 * @return The list of point of the portal outline
	 */
	protected abstract List<Location> getPoints();

	public BoosterManager<?> getDataManager() {
		return plugin.getPortalManager();
	}
	
	// ---

	/**
	 * Tests if a location is in the portal area
	 * 
	 * @param location
	 * @param epsilon
	 * @return true if the location is in the portal area
	 */
	protected abstract boolean isInPortalArea(Location location, double epsilon);

	/**
	 * Runs the timer task that checks for users inside the portal and draws the
	 * outline
	 */
	public void runPortalTask() {
		if (!isActive()) {
			outlineTaskNumber = plugin.getServer().getScheduler()
					.runTaskTimer(plugin, () -> drawOutline(), 1, outlineInterval).getTaskId();
			checkTaskNumber = plugin.getServer().getScheduler()
					.runTaskTimer(plugin, () -> checkPlayersPassing(), 0, checkInterval).getTaskId();
		}
	}

	/**
	 * Stops the portal task
	 */
	public void stopBoosterTask() {
		if (isActive()) {
			outline.eraseOutline(points);
			plugin.getServer().getScheduler().cancelTask(outlineTaskNumber);
			plugin.getServer().getScheduler().cancelTask(checkTaskNumber);
			outlineTaskNumber = 0;
			checkTaskNumber = 0;
		}
	}

	/**
	 * Task that checks if the players are passing throught the portal
	 */
	protected void checkPlayersPassing() {
		plugin.getStatusMap().keySet().forEach(player -> {
			if (!onCooldown() && !plugin.getStatusMap().get(player) && player.hasPermission("eb.portals.boost")
					&& isInUnionPortalArea(player.getLocation(), 0)) {
				Bukkit.getPluginManager().callEvent(new PlayerBoostEvent(plugin, player, boost));
				cooldown();
			}
		});
	}

	/**
	 * Task that draws the outline of the portal
	 */
	protected void drawOutline() {
		if (!onCooldown()) {
			outline.drawOutline(points);
		} else {
			outline.cooldownOutline(points, cooldown, currCooldown);
		}
	}

	/**
	 * Starts and handles the cooldown process
	 */
	protected void cooldown() {
		currCooldown = cooldown;
		BukkitRunnable cooldownProcess = new BukkitRunnable() {
			@Override
			public void run() {
				if (currCooldown > 0) {
					currCooldown--;
				} else {
					Bukkit.getPluginManager().callEvent(new FinishedCooldownEvent(plugin, portal));
					this.cancel();
				}
			}
		};
		if (cooldown > 0)
			cooldownProcess.runTaskTimer(plugin, 0, 1);
	}

	/**
	 * 
	 * @return true if portal is on cooldown
	 */
	protected boolean onCooldown() {
		return currCooldown > 0;
	}

	/**
	 * Returns true if the portal is active
	 * 
	 * @return true if the portal is active
	 */
	public boolean isActive() {
		return checkTaskNumber != 0 && outlineTaskNumber != 0;
	}

	/**
	 * 
	 * @return true if the portal is a union of portal
	 */
	protected boolean isUnion() {
		return !portalsUnion.isEmpty();
	}

	/**
	 * Checks if a location is in the union portal area
	 * 
	 * @param location
	 * @param epsilon
	 * @return true if location is in the union portal area
	 */
	private boolean isInUnionPortalArea(Location location, double epsilon) {
		boolean test = isInPortalArea(location, epsilon);
		for (UnionPortal p : portalsUnion) {
			test = p.isIntersecate() ? test && p.isInPortalArea(location, epsilon)
					: test || p.isInPortalArea(location, epsilon);
		}
		return test;
	}

	/**
	 * Generates the list of points of the outline of a union portal
	 * 
	 * @return the list of the portal union outline points
	 */
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
		sorter.sort(unionPoints);
		return unionPoints;
	}

	/**
	 * Generates a string that represents the portal
	 */
	@Override
	public String toString() {
		return MessagesUtil.color(String.format("&6Id: &a%s &6enabled: &a%b\n&6x/y/z: &a%.2f&6/&a%.2f&6/&a%.2f\n", id,
				isActive(), center.getX(), center.getY(), center.getZ()));
	}

	public String warnMessage() {
		return ChatColor.RED + "Error with the creation of the portal " + ChatColor.GOLD + id + ChatColor.RED
				+ ". Check portals configuration.";
	}

	public ElytraBooster getPlugin() {
		return plugin;
	}

	public AbstractPortal getPortal() {
		return portal;
	}

	public String getId() {
		return id;
	}

	public Location getCenter() {
		return center;
	}

	public char getAxis() {
		return axis;
	}

	public PortalOutline getOutline() {
		return outline;
	}

	public List<UnionPortal> getPortalsUnion() {
		return portalsUnion;
	}

	public int getCooldown() {
		return cooldown;
	}

	public int getOutlineTaskNumber() {
		return outlineTaskNumber;
	}

	public int getCheckTaskNumber() {
		return checkTaskNumber;
	}

	public void setCenter(Location center) {
		this.center = center;
	}

	public String getMeasures() {
		return measures;
	}

	public PointSorter getSorter() {
		return sorter;
	}

	public Boost getBoost() {
		return boost;
	}

	public abstract String getShape();

}
