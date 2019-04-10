package com.github.zamponimarco.elytrabooster.core;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.zamponimarco.elytrabooster.commands.executor.ElytraBoosterCommandExecutor;
import com.github.zamponimarco.elytrabooster.listeners.PlayerGlideListener;
import com.github.zamponimarco.elytrabooster.manager.PortalManager;

public class ElytraBooster extends JavaPlugin {

	private PortalManager portalManager;
	private Map<Player, Boolean> statusMap;
	
	// TODO tidy up
	public void onEnable() {
		statusMap = new HashMap<Player, Boolean>();
		portalManager = new PortalManager(this);
		getCommand("eb").setExecutor(new ElytraBoosterCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new PlayerGlideListener(this), this);
	}

	public void onDisable() {
		portalManager.getPortalsMap().forEach((id, portal) -> portal.stopPortalTask());
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
	
}
