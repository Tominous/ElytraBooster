package com.github.zamponimarco.elytrabooster.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class PlayerGlideListener implements Listener {

	private ElytraBooster plugin;

	public PlayerGlideListener(ElytraBooster plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerGlideEvent(EntityToggleGlideEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.isGliding()) {
				plugin.getStatusMap().put(p, false);
			} else {
				plugin.getStatusMap().remove(p);
			}
		}
	}

}
