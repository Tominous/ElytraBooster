package com.github.zamponimarco.elytrabooster.listeners;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.zamponimarco.elytrabooster.core.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.settings.StringSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.BoosterManager;
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
		if (settingsMap != null && settingsMap.containsKey(p)) {
			runSyncTask(p, e.getMessage(), settingsMap);
			e.setCancelled(true);
		}
	}

	private void runSyncTask(Player p, String value, Map<HumanEntity, Map<Booster, String>> settingsMap) {
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
