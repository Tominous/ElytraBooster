package com.github.zamponimarco.elytrabooster.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public abstract class ElytraBoosterInventoryHolder implements InventoryHolder {

	protected ElytraBooster plugin;
	protected Inventory inventory;
	protected Map<Integer, Consumer<InventoryClickEvent>> clickMap;

	public ElytraBoosterInventoryHolder(ElytraBooster plugin) {
		this.plugin = plugin;
		this.clickMap = new HashMap<>();
	}

	protected abstract void initializeInventory();

	public void handleClickEvent(InventoryClickEvent e) {
		clickMap.get(e.getSlot()).accept(e);
		e.setCancelled(true);
	}

	public void registerClickConsumer(int slot, ItemStack item, Consumer<InventoryClickEvent> clickConsumer) {
		inventory.setItem(slot, item);
		clickMap.put(slot, clickConsumer);
	}
	
	public void fillInventoryWith(Material material) {
		for (int i = 0; i < inventory.getSize(); i++) {
			if (inventory.getItem(i) == null) {
				registerClickConsumer(i, getPlaceholderItem(material), e -> {});
			}
		}
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	private ItemStack getPlaceholderItem(Material material) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}

}
