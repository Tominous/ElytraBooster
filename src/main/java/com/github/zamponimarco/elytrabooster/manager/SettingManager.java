package com.github.zamponimarco.elytrabooster.manager;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.settings.Settings;

public class SettingManager implements DataManager {

	private final static String FILENAME = "config.yml";

	private ElytraBooster plugin;

	private File dataFile;
	private FileConfiguration dataYaml;
	private Map<Settings, String> settings = new EnumMap<Settings, String>(Settings.class);

	public SettingManager(ElytraBooster plugin) {
		this.plugin = plugin;
		
		loadDataFile();
		loadDataYaml();
		loadData();
	}

	@Override
	public void loadDataFile() {
		dataFile = new File(plugin.getDataFolder(), FILENAME);
		if (!dataFile.exists()) {
			plugin.saveDefaultConfig();
		}
	}

	@Override
	public void loadDataYaml() {
		dataYaml = plugin.getConfig();
	}

	@Override
	public void loadData() {
		settings.put(Settings.PORTAL_OUTLINE_INTERVAL, dataYaml.getString("portalOutlineInterval"));
		settings.put(Settings.PORTAL_CHECK_INTERVAL, dataYaml.getString("portalCheckInterval"));
	}

	public File getDataFile() {
		return dataFile;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	public FileConfiguration getDataYaml() {
		return dataYaml;
	}

	public void setDataYaml(FileConfiguration dataYaml) {
		this.dataYaml = dataYaml;
	}

	public Map<Settings, String> getSettings() {
		return settings;
	}
	
	public String getSetting(Settings setting) {
		return settings.get(setting);
	}

}
