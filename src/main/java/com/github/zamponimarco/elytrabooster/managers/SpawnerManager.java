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
		dataYaml.getKeys(false).forEach(
				id -> spawners.put(id, SpawnerFactory.buildSpawner(plugin, this, dataYaml.getConfigurationSection(id))));
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
	
	public void removespawner(String id) {
		spawners.remove(id);
	}

	public AbstractSpawner reloadPortal(AbstractSpawner spawner) {
		saveConfig();
//		spawner.stopPortalTask();
//		AbstractSpawner newSpawner = SpawnerFactory.buildSpawner(plugin, this,
//				getDataYaml().getConfigurationSection(spawner.getId()));
//		setPortal(spawner.getId(), newSpawner);
//		return newSpawner;
		return null;
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

}
