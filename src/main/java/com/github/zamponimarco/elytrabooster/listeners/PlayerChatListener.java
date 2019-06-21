package com.github.zamponimarco.elytrabooster.listeners;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.settings.PortalStringSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.SpawnerStringSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.PortalManager;
import com.github.zamponimarco.elytrabooster.managers.SpawnerManager;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class PlayerChatListener implements Listener {

	private ElytraBooster plugin;

	public PlayerChatListener(ElytraBooster plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Map<HumanEntity, Map<AbstractPortal, String>> portalSettingsMap = PortalStringSettingInventoryHolder
				.getSettingsMap();
		Map<HumanEntity, Map<AbstractSpawner, String>> spawnerSettingsMap = SpawnerStringSettingInventoryHolder
				.getSettingsMap();
		Player p = e.getPlayer();
		if (portalSettingsMap != null && portalSettingsMap.containsKey(p)) {
			runPortalSyncTask(p, e.getMessage(), portalSettingsMap);
			e.setCancelled(true);
		} else if (spawnerSettingsMap != null && spawnerSettingsMap.containsKey(p)) {
			runSpawnerSyncTask(p, e.getMessage(), spawnerSettingsMap);
			e.setCancelled(true);
		}
	}

	private void runSpawnerSyncTask(Player p, String value,
			Map<HumanEntity, Map<AbstractSpawner, String>> spawnerSettingsMap) {
		plugin.getServer().getScheduler().runTask(plugin, () -> {
			if (!value.equalsIgnoreCase("exit")) {
				SpawnerManager spawnerManager = plugin.getSpawnerManager();
				AbstractSpawner spawner = spawnerSettingsMap.get(p).keySet().stream().findFirst().get();
				String key = spawnerSettingsMap.get(p).get(spawner);
				spawnerManager.setParam(spawner.getId(), key, value);
				spawnerManager.reloadSpawner(spawner);
				p.sendMessage(MessagesUtil
						.color("&aPortal modified, &6ID: &a" + spawner.getId() + ", &6" + key + ": &a" + value));
			} else {
				p.sendMessage(MessagesUtil.color("&aThe value &6&lhasn't&a been modified."));
			}
			spawnerSettingsMap.remove(p);
		});
	}

	private void runPortalSyncTask(Player p, String value, Map<HumanEntity, Map<AbstractPortal, String>> settingsMap) {
		plugin.getServer().getScheduler().runTask(plugin, () -> {
			if (!value.equalsIgnoreCase("exit")) {
				PortalManager portalManager = plugin.getPortalManager();
				AbstractPortal portal = settingsMap.get(p).keySet().stream().findFirst().get();
				String key = settingsMap.get(p).get(portal);
				portalManager.setParam(portal.getId(), key, value);
				portalManager.reloadPortal(portal);
				p.sendMessage(MessagesUtil
						.color("&aPortal modified, &6ID: &a" + portal.getId() + ", &6" + key + ": &a" + value));
			} else {
				p.sendMessage(MessagesUtil.color("&aThe value &6&lhasn't&a been modified."));
			}
			settingsMap.remove(p);
		});
	}

}
