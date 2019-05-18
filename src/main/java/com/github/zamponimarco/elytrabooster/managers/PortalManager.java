package com.github.zamponimarco.elytrabooster.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.portals.factory.PortalFactory;

/**
 * Handles data of the portals
 * 
 * @author Marco
 *
 */
public class PortalManager implements DataManager {

	private final static String FILENAME = "portals.yml";

	private ElytraBooster plugin;

	private File dataFile;
	private YamlConfiguration dataYaml;
	private Map<String, AbstractPortal> portals;

	/**
	 * Creates a PortalManager instance
	 * 
	 * @param plugin
	 * @param settingsManager 
	 */
	public PortalManager(ElytraBooster plugin) {
		super();
		this.plugin = plugin;

		loadDataFile();
		loadDataYaml();
		loadData();
	}

	/**
	 * Loads the data file
	 */
	public void loadDataFile() {
		dataFile = new File(plugin.getDataFolder(), FILENAME);
		if (!dataFile.exists()) {
			plugin.saveResource(FILENAME, false);
		}
	}

	/**
	 * Loads the data yaml configuration
	 */
	public void loadDataYaml() {
		dataYaml = YamlConfiguration.loadConfiguration(dataFile);
	}

	/**
	 * Loads the portals in the map
	 */
	public void loadData() {
		portals = new HashMap<String, AbstractPortal>();
		dataYaml.getKeys(false).forEach(
				id -> portals.put(id, PortalFactory.buildPortal(plugin, this, dataYaml.getConfigurationSection(id))));
	}

	/**
	 * Saves the config
	 */
	public void saveConfig() {
		try {
			dataYaml.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the configuration for a default portal
	 * 
	 * @param creator
	 * @param id
	 * @return configuration for a default portal
	 */
	public ConfigurationSection createDefaultPortalConfiguration(Player creator, String id) {
		ConfigurationSection newPortal = dataYaml.createSection(id);
		newPortal.set("world", creator.getWorld().getName());
		newPortal.set("x", creator.getLocation().getBlockX());
		newPortal.set("y", creator.getLocation().getBlockY());
		newPortal.set("z", creator.getLocation().getBlockZ());
		newPortal.set("axis", 'x');
		newPortal.set("shape", "circle");
		newPortal.set("measures", 10);
		newPortal.set("isBlockOutline", true);
		newPortal.set("outlineType", "STONE");
		saveConfig();
		return newPortal;
	}

	/**
	 * Returns the portal that has the given id
	 * 
	 * @param id
	 * @return portal named id
	 */
	public AbstractPortal getPortal(String id) {
		Objects.requireNonNull(id);
		return portals.get(id);
	}

	/**
	 * Puts in the map the given portal with name id, replaces id named portals that
	 * were in the map
	 * 
	 * @param id
	 * @param portal
	 */
	public void setPortal(String id, AbstractPortal portal) {
		Objects.requireNonNull(id);
		Objects.requireNonNull(portal);
		portals.put(id, portal);
	}

	/**
	 * Remove portal from the portals map
	 * 
	 * @param id
	 */
	public void removePortal(String id) {
		portals.remove(id);
	}

	/**
	 * Returns the map of portals
	 * 
	 * @return portalsMap
	 */
	public Map<String, AbstractPortal> getPortalsMap() {
		return portals;
	}

	/**
	 * @return the dataFile
	 */
	public File getDataFile() {
		return dataFile;
	}

	/**
	 * @return the dataYaml
	 */
	public YamlConfiguration getDataYaml() {
		return dataYaml;
	}

}
