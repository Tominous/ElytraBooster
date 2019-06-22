package com.github.zamponimarco.elytrabooster.listeners;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.settings.StringSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class PlayerChatListener implements Listener {

	private ElytraBooster plugin;

	public PlayerChatListener(ElytraBooster plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Map<HumanEntity, Map<Booster, String>> settingsMap = StringSettingInventoryHolder.getSettingsMap();
		Player p = e.getPlayer();
		boolean isThereBooster = !settingsMap.get(p).keySet().contains(null);
		if (settingsMap != null && settingsMap.containsKey(p) && isThereBooster) {
			runModifySyncTask(p, e.getMessage(), settingsMap);
			e.setCancelled(true);
		} else if (settingsMap != null && settingsMap.containsKey(p) && !isThereBooster) {
			runCreateSyncTask(p, e.getMessage(), settingsMap);
			e.setCancelled(true);
		}
	}

	private void runCreateSyncTask(Player p, String value, Map<HumanEntity, Map<Booster, String>> settingsMap) {
		String key = settingsMap.get(p).get(null);
		BoosterManager<?> boosterManager = null;
		switch (key) {
		case "portal":
			boosterManager = plugin.getPortalManager();
			break;
		case "spawner":
			boosterManager = plugin.getSpawnerManager();
			break;
		}

		if (!value.equalsIgnoreCase("exit")) {
			if (!boosterManager.getBoostersMap().containsKey(value)) {
				boosterManager.createDefaultBoosterConfiguration(p, value);
				boosterManager.addBooster(value);
				p.sendMessage(MessagesUtil.color("&aBooster created, &6ID: &a" + value));
			} else {
				p.sendMessage((MessagesUtil.color("&cBooster passed in input is invalid")));
			} 
		} else {
			p.sendMessage(MessagesUtil.color("&aBooster creation &6&lcancelled"));
		}
	}

	private void runModifySyncTask(Player p, String value, Map<HumanEntity, Map<Booster, String>> settingsMap) {
		plugin.getServer().getScheduler().runTask(plugin, () -> {
			if (!value.equalsIgnoreCase("exit")) {
				Booster booster = settingsMap.get(p).keySet().stream().findFirst().get();
				BoosterManager<?> boosterManager = booster.getDataManager();
				String key = settingsMap.get(p).get(booster);
				boosterManager.setParam(booster.getId(), key, value);
				boosterManager.reloadBooster(booster);
				p.sendMessage(MessagesUtil
						.color("&aBooster modified, &6ID: &a" + booster.getId() + ", &6" + key + ": &a" + value));
			} else {
				p.sendMessage(MessagesUtil.color("&aThe value &6&lhasn't&a been modified."));
			}
			settingsMap.remove(p);
		});
	}

}
