package com.github.zamponimarco.elytrabooster.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class SpawnersListInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final int SPAWNER_NUMBER = 50;
	private static final String ARROW_LEFT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY5N2MyNDg5MmNmYzAzYzcyOGZmYWVhYmYzNGJkZmI5MmQ0NTExNDdiMjZkMjAzZGNhZmE5M2U0MWZmOSJ9fX0=";
	private static final String ARROW_RIGHT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZlMTQ1ZTcxMjk1YmNjMDQ4OGU5YmI3ZTZkNjg5NWI3Zjk2OWEzYjViYjdlYjM0YTUyZTkzMmJjODRkZjViIn19fQ===";
	private static final String SPAWNER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQ3ZTJlNWQ1NWI2ZDA0OTQzNTE5YmVkMjU1N2M2MzI5ZTMzYjYwYjkwOWRlZTg5MjNjZDg4YjExNTIxMCJ9fX0====";

	private String title;
	private List<AbstractSpawner> spawners;
	private List<AbstractSpawner> toList;
	private int page;

	public SpawnersListInventoryHolder(ElytraBooster plugin, String title, List<AbstractSpawner> spawners, int page) {
		super(plugin);
		this.title = title;
		this.spawners = spawners;
		this.toList = spawners.stream().filter(spawner -> spawners.indexOf(spawner) >= (page - 1) * SPAWNER_NUMBER
				&& spawners.indexOf(spawner) <= page * SPAWNER_NUMBER - 1).collect(Collectors.toList());
		this.page = page;
		
		initializeInventory();
	}

	@Override
	protected void initializeInventory() {
		int maxPage = (int) Math.ceil((spawners.size() > 0 ? spawners.size() : 1) / (double) SPAWNER_NUMBER);

		this.inventory = Bukkit.createInventory(this, 54, title);
		toList.forEach(spawner -> registerClickConsumer(toList.indexOf(spawner), getSpawnerItem(spawner),
				getSpawnerConsumer(toList.indexOf(spawner))));
		if (page != maxPage) {
			registerClickConsumer(53, HeadsUtil.skullFromValue(ARROW_LEFT_HEAD), e -> e.getWhoClicked()
					.openInventory(new SpawnersListInventoryHolder(plugin, title, spawners, page + 1).getInventory()));
		}
		if (page != 1) {
			registerClickConsumer(52, HeadsUtil.skullFromValue(ARROW_RIGHT_HEAD), e -> e.getWhoClicked()
					.openInventory(new SpawnersListInventoryHolder(plugin, title, spawners, page - 1).getInventory()));
		}
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private Consumer<InventoryClickEvent> getSpawnerConsumer(int slot) {
		return e -> {
			AbstractSpawner spawner = toList.get(slot);
			if (e.getClick().equals(ClickType.LEFT)) {
				e.getWhoClicked().openInventory(new SpawnerSettingsInventoryHolder(plugin, spawner).getInventory());
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				e.getWhoClicked().teleport(spawner.getCenter(), TeleportCause.PLUGIN);
			}
		};
	}

	private ItemStack getSpawnerItem(AbstractSpawner spawner) {
		ItemStack spawnerItem = HeadsUtil.skullFromValue(SPAWNER_HEAD);
		ItemMeta meta = spawnerItem.getItemMeta();
		meta.setDisplayName(MessagesUtil.color("&a&l" + spawner.getId()));
		meta.setLore(getSpawnerItemLore(spawner));
		spawnerItem.setItemMeta(meta);
		return spawnerItem;
	}

	private List<String> getSpawnerItemLore(AbstractSpawner spawner) {
		List<String> lore = new ArrayList<String>();
		lore.add(MessagesUtil.color(String.format("&6&l- x/y/z: &a%.2f&6&l/&a%.2f&6&l/&a%.2f",
				spawner.getCenter().getX(), spawner.getCenter().getY(), spawner.getCenter().getZ())));
		lore.add(MessagesUtil.color("&6&l- &e&lLeft click &6to open settings"));
		lore.add(MessagesUtil.color("&6&l- &e&lRight click &6to teleport"));
		return lore;
	}

}
