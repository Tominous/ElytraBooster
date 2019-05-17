package com.github.zamponimarco.elytrabooster.core;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.zamponimarco.elytrabooster.commands.executor.ElytraBoosterCommandExecutor;
import com.github.zamponimarco.elytrabooster.listeners.PlayerGlideListener;
import com.github.zamponimarco.elytrabooster.manager.PortalManager;
import com.github.zamponimarco.elytrabooster.manager.SettingManager;

public class ElytraBooster extends JavaPlugin {

	private Map<Player, Boolean> statusMap;
	private SettingManager settingsManager;
	private PortalManager portalManager;
	
	public void onEnable() {
		setUpFolder();
		startupTasks();
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
		new Metrics(this);
		statusMap = new HashMap<Player, Boolean>();
		settingsManager = new SettingManager(this);
		portalManager = new PortalManager(this);
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

	public SettingManager getSettingsManager() {
		return settingsManager;
	}

	public void setSettingsManager(SettingManager settingsManager) {
		this.settingsManager = settingsManager;
	}
	
}
