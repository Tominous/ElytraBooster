package com.github.zamponimarco.elytrabooster.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.github.zamponimarco.elytrabooster.gui.ElytraBoosterInventoryHolder;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {

		if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR))) {
			return;
		}

		if (e.getClickedInventory().getHolder() instanceof ElytraBoosterInventoryHolder) {
			ElytraBoosterInventoryHolder holder = (ElytraBoosterInventoryHolder) e.getClickedInventory().getHolder();
			holder.handleClickEvent(e);
		}
	}

}
