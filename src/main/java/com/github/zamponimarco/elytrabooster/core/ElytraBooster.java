package com.github.zamponimarco.elytrabooster.core;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.zamponimarco.elytrabooster.commands.executor.ElytraBoosterCommandExecutor;
import com.github.zamponimarco.elytrabooster.listeners.InventoryClickListener;
import com.github.zamponimarco.elytrabooster.listeners.PlayerChatListener;
import com.github.zamponimarco.elytrabooster.listeners.PlayerGlideListener;
import com.github.zamponimarco.elytrabooster.managers.SettingsManager;
import com.github.zamponimarco.elytrabooster.managers.boosters.PortalManager;
import com.github.zamponimarco.elytrabooster.managers.boosters.SpawnerManager;
import com.github.zamponimarco.elytrabooster.settings.Settings;

public class ElytraBooster extends JavaPlugin {

	private Map<Player, Boolean> statusMap;
	private SettingsManager settingsManager;
	private PortalManager portalManager;
	private SpawnerManager spawnerManager;

	public void onEnable() {
		setUpFolder();
		startupTasks();
		getLogger().info("Enabled ElytraBooster v" + getDescription().getVersion());
	}

	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
		spawnerManager.getBoostersMap().values().forEach(spawner -> spawner.stopBoosterTask());
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
		spawnerManager = new SpawnerManager(this);
		statusMap = new HashMap<Player, Boolean>();
		CommandExecutor executor = new ElytraBoosterCommandExecutor(this);
		getCommand("eb").setExecutor(executor);
		getCommand("eb").setTabCompleter((TabCompleter) executor);
		getServer().getPluginManager().registerEvents(new PlayerGlideListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
	}

	public PortalManager getPortalManager() {
		return portalManager;
	}

	public Map<Player, Boolean> getStatusMap() {
		return statusMap;
	}

	public SettingsManager getSettingsManager() {
		return settingsManager;
	}

	public SpawnerManager getSpawnerManager() {
		return spawnerManager;
	}

}
