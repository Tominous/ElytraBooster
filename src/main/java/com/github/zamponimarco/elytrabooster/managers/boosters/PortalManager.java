package com.github.zamponimarco.elytrabooster.managers.boosters;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.boosters.factory.BoosterFactory;
import com.github.zamponimarco.elytrabooster.boosters.factory.PortalFactory;
import com.github.zamponimarco.elytrabooster.boosters.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

/**
 * Handles data of the portals
 * 
 * @author Marco
 *
 */
public class PortalManager implements BoosterManager<AbstractPortal> {

	private final static String FILENAME = "portals.yml";

	private ElytraBooster plugin;

	private File dataFile;
	private YamlConfiguration dataYaml;
	private Map<String, AbstractPortal> portals;

	public PortalManager(ElytraBooster plugin) {
		super();
		this.plugin = plugin;

		loadDataFile();
		loadDataYaml();
		loadData();
	}

	public void loadDataFile() {
		dataFile = new File(plugin.getDataFolder(), FILENAME);
		if (!dataFile.exists()) {
			plugin.saveResource(FILENAME, false);
		}
	}

	public void loadDataYaml() {
		dataYaml = YamlConfiguration.loadConfiguration(dataFile);
	}

	public void loadData() {
		portals = new HashMap<String, AbstractPortal>();
		dataYaml.getKeys(false).forEach(id -> addBooster(id));
	}

	public void saveConfig() {
		try {
			dataYaml.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ConfigurationSection createDefaultBoosterConfiguration(Player creator, String id) {
		ConfigurationSection newPortal = dataYaml.createSection(id);
		newPortal.set("world", creator.getWorld().getName());
		newPortal.set("x", creator.getLocation().getBlockX());
		newPortal.set("y", creator.getLocation().getBlockY());
		newPortal.set("z", creator.getLocation().getBlockZ());
		newPortal.set("axis", 'x');
		newPortal.set("isBlockOutline", true);
		newPortal.set("outlineType", "STONE");
		newPortal.set("shape", "circle");
		newPortal.set("measures", 10);
		saveConfig();
		return newPortal;
	}

	@Override
	public void addBooster(String id) {
		portals.put(id, PortalFactory.buildBooster(plugin, dataYaml.getConfigurationSection(id)));
	}

	@Override
	public void setParam(String id, String param, String value) {
		ConfigurationSection portal = getDataYaml().getConfigurationSection(id);
		switch (param) {
		case "initialVelocity":
		case "finalVelocity":
			portal.set(param, Double.valueOf(value));
			break;
		case "boostDuration":
		case "cooldown":
			portal.set(param, Integer.valueOf(value));
			break;
		case "axis":
		case "outlineType":
		case "cooldownType":
		case "shape":
		case "measures":
		case "trail":
		case "sorter":
			portal.set(param, value);
			break;
		case "isBlockOutline":
			portal.set(param, Boolean.valueOf(value));
			break;
		default:
			throw new IllegalArgumentException();
		}

	}

	@Override
	public AbstractPortal getBooster(String id) {
		Objects.requireNonNull(id);
		return portals.get(id);
	}

	public void setBooster(String id, AbstractPortal portal) {
		Objects.requireNonNull(id);
		Objects.requireNonNull(portal);
		portals.put(id, portal);
	}

	public void removeBooster(String id) {
		portals.remove(id);
	}

	public AbstractPortal reloadBooster(Booster booster) {
		saveConfig();
		booster.stopBoosterTask();
		AbstractPortal newPortal = PortalFactory.buildBooster(plugin,
				getDataYaml().getConfigurationSection(booster.getId()));
		setBooster(booster.getId(), newPortal);
		return newPortal;
	}

	public Map<String, AbstractPortal> getBoostersMap() {
		return portals;
	}

	public File getDataFile() {
		return dataFile;
	}

	public YamlConfiguration getDataYaml() {
		return dataYaml;
	}

	@Override
	public Class<? extends BoosterFactory> getFactory() {
		return PortalFactory.class;
	}

}
