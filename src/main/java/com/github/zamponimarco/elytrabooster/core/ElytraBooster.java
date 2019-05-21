package com.github.zamponimarco.elytrabooster.core;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.zamponimarco.elytrabooster.commands.executor.ElytraBoosterCommandExecutor;
import com.github.zamponimarco.elytrabooster.listeners.PlayerGlideListener;
import com.github.zamponimarco.elytrabooster.managers.PortalManager;
import com.github.zamponimarco.elytrabooster.managers.SettingsManager;
import com.github.zamponimarco.elytrabooster.settings.Settings;

public class ElytraBooster extends JavaPlugin {

	private Map<Player, Boolean> statusMap;
	private SettingsManager settingsManager;
	private PortalManager portalManager;

	public void onEnable() {
		setUpFolder();
		startupTasks();
		getLogger().info("Enabled ElytraBooster v" + getDescription().getVersion());
	}

	public void onDisable() {
		portalManager.getPortalsMap().forEach((id, portal) -> portal.stopPortalTask());
	}

	private void setUpFolder() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
	}

	private void startupTasks() {
		settingsManager = new SettingsManager(this);
		if (Boolean.valueOf(settingsManager.getSetting(Settings.METRICS))) {
			new Metrics(this);
		}
		portalManager = new PortalManager(this);
		statusMap = new HashMap<Player, Boolean>();
		getCommand("eb").setExecutor(new ElytraBoosterCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new PlayerGlideListener(this), this);
	}

	/**
	 * @return the portalManager of the plugin
	 */
	public PortalManager getPortalManager() {
		return portalManager;
	}

	/**
	 * @return the statusMap
	 */
	public Map<Player, Boolean> getStatusMap() {
		return statusMap;
	}

	public SettingsManager getSettingsManager() {
		return settingsManager;
	}

	public void setSettingsManager(SettingsManager settingsManager) {
		this.settingsManager = settingsManager;
	}

}
