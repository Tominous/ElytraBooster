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
import com.github.zamponimarco.elytrabooster.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.spawners.factory.SpawnerFactory;

public class SpawnerManager implements DataManager {

	private final static String FILENAME = "spawners.yml";

	private ElytraBooster plugin;

	private File dataFile;
	private YamlConfiguration dataYaml;
	private Map<String, AbstractSpawner> spawners;

	public SpawnerManager(ElytraBooster plugin) {
		this.plugin = plugin;
		loadDataFile();
		loadDataYaml();
		loadData();
	}

	@Override
	public void loadDataFile() {
		dataFile = new File(plugin.getDataFolder(), FILENAME);
		if (!dataFile.exists()) {
			plugin.saveResource(FILENAME, false);
		}
	}

	@Override
	public void loadDataYaml() {
		dataYaml = YamlConfiguration.loadConfiguration(dataFile);
	}

	@Override
	public void loadData() {
		spawners = new HashMap<String, AbstractSpawner>();
		dataYaml.getKeys(false).forEach(id -> spawners.put(id,
				SpawnerFactory.buildSpawner(plugin, this, dataYaml.getConfigurationSection(id))));
	}

	public void saveConfig() {
		try {
			dataYaml.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ConfigurationSection createDefaultSpawnerConfiguration(Player creator, String id) {
		ConfigurationSection newSpawner = dataYaml.createSection(id);
		newSpawner.set("world", creator.getWorld().getName());
		newSpawner.set("x", creator.getLocation().getBlockX());
		newSpawner.set("y", creator.getLocation().getBlockY());
		newSpawner.set("z", creator.getLocation().getBlockZ());
		saveConfig();
		return newSpawner;
	}

	public AbstractSpawner getSpawner(String id) {
		Objects.requireNonNull(id);
		return spawners.get(id);
	}

	public void setSpawner(String id, AbstractSpawner spawner) {
		Objects.requireNonNull(id);
		Objects.requireNonNull(spawner);
		spawners.put(id, spawner);
	}

	public void removeSpawner(String id) {
		spawners.remove(id);
	}

	public AbstractSpawner reloadSpawner(AbstractSpawner spawner) {
		saveConfig();
		spawner.stopSpawnerTask();
		AbstractSpawner newSpawner = SpawnerFactory.buildSpawner(plugin, this,
				getDataYaml().getConfigurationSection(spawner.getId()));
		setSpawner(spawner.getId(), newSpawner);
		return newSpawner;
	}

	public Map<String, AbstractSpawner> getSpawnersMap() {
		return spawners;
	}

	public File getDataFile() {
		return dataFile;
	}

	public YamlConfiguration getDataYaml() {
		return dataYaml;
	}

	public void setParam(String id, String param, String value) {
		ConfigurationSection spawner = getDataYaml().getConfigurationSection(id);
		switch (param) {
		case "initialVelocity":
		case "finalVelocity":
		case "minRadius":
		case "maxRadius":
			spawner.set(param, Double.valueOf(value));
			break;
		case "boostDuration":
		case "cooldown":
		case "maxEntities":
			spawner.set(param, Integer.valueOf(value));
			break;
		case "trail":
			spawner.set(param, value);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

}
