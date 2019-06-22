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
import com.github.zamponimarco.elytrabooster.boosters.factory.SpawnerFactory;
import com.github.zamponimarco.elytrabooster.boosters.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class SpawnerManager implements BoosterManager<AbstractSpawner> {

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
		dataYaml.getKeys(false).forEach(id -> addBooster(id));
	}

	public void saveConfig() {
		try {
			dataYaml.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ConfigurationSection createDefaultBoosterConfiguration(Player creator, String id) {
		ConfigurationSection newSpawner = dataYaml.createSection(id);
		newSpawner.set("world", creator.getWorld().getName());
		newSpawner.set("x", creator.getLocation().getBlockX());
		newSpawner.set("y", creator.getLocation().getBlockY());
		newSpawner.set("z", creator.getLocation().getBlockZ());
		saveConfig();
		return newSpawner;
	}

	@Override
	public void addBooster(String id) {
		spawners.put(id, SpawnerFactory.buildBooster(plugin, dataYaml.getConfigurationSection(id)));
	}

	public AbstractSpawner getBooster(String id) {
		Objects.requireNonNull(id);
		return spawners.get(id);
	}

	public void setBooster(String id, AbstractSpawner spawner) {
		Objects.requireNonNull(id);
		Objects.requireNonNull(spawner);
		spawners.put(id, spawner);
	}

	public void removeBooster(String id) {
		spawners.remove(id);
	}

	public AbstractSpawner reloadBooster(Booster booster) {
		saveConfig();
		booster.stopBoosterTask();
		AbstractSpawner newSpawner = SpawnerFactory.buildBooster(plugin,
				getDataYaml().getConfigurationSection(booster.getId()));
		setBooster(booster.getId(), newSpawner);
		return newSpawner;
	}

	public Map<String, AbstractSpawner> getBoostersMap() {
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
		case "entity":
			spawner.set(param, value);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Class<? extends BoosterFactory> getFactory() {
		return SpawnerFactory.class;
	}

}
