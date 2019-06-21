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
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class PortalsListInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final int PORTALS_NUMBER = 50;
	private static final String ARROW_LEFT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY5N2MyNDg5MmNmYzAzYzcyOGZmYWVhYmYzNGJkZmI5MmQ0NTExNDdiMjZkMjAzZGNhZmE5M2U0MWZmOSJ9fX0=";
	private static final String ARROW_RIGHT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZlMTQ1ZTcxMjk1YmNjMDQ4OGU5YmI3ZTZkNjg5NWI3Zjk2OWEzYjViYjdlYjM0YTUyZTkzMmJjODRkZjViIn19fQ===";
	private static final String ACTIVE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTIxOTI4ZWE2N2QzYThiOTdkMjEyNzU4ZjE1Y2NjYWMxMDI0Mjk1YjE4NWIzMTkyNjQ4NDRmNGM1ZTFlNjFlIn19fQ=====";
	private static final String NOT_ACTIVE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjVlZjY4ZGNiZDU4MjM0YmE3YWVlMmFkOTFjYTZmYTdjZTIzZjlhMzIzNDViNDhkNmU1ZjViODZhNjhiNWIifX19===";

	private String title;
	private List<AbstractPortal> portals;
	private List<AbstractPortal> toList;
	private int page;

	public PortalsListInventoryHolder(ElytraBooster plugin, String title, List<AbstractPortal> portals, int page) {
		super(plugin);
		this.title = title;
		this.portals = portals;
		this.toList = portals.stream().filter(portal -> portals.indexOf(portal) >= (page - 1) * PORTALS_NUMBER
				&& portals.indexOf(portal) <= page * PORTALS_NUMBER - 1).collect(Collectors.toList());
		this.page = page;
		initializeInventory();
	}

	@Override
	protected void initializeInventory() {
		int maxPage = (int) Math.ceil((portals.size() > 0 ? portals.size() : 1) / (double) PORTALS_NUMBER);

		this.inventory = Bukkit.createInventory(this, 54, title);
		toList.forEach(portal -> registerClickConsumer(toList.indexOf(portal), getPortalItem(portal),
				getPortalConsumer(toList.indexOf(portal))));
		if (page != maxPage) {
			registerClickConsumer(53, HeadsUtil.skullFromValue(ARROW_LEFT_HEAD), e -> e.getWhoClicked()
					.openInventory(new PortalsListInventoryHolder(plugin, title, portals, page + 1).getInventory()));
		}
		if (page != 1) {
			registerClickConsumer(52, HeadsUtil.skullFromValue(ARROW_RIGHT_HEAD), e -> e.getWhoClicked()
					.openInventory(new PortalsListInventoryHolder(plugin, title, portals, page - 1).getInventory()));
		}
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private ItemStack getPortalItem(AbstractPortal portal) {
		ItemStack portalItem = portal.isActive() ? HeadsUtil.skullFromValue(ACTIVE_HEAD)
				: HeadsUtil.skullFromValue(NOT_ACTIVE_HEAD);
		ItemMeta meta = portalItem.getItemMeta();
		meta.setDisplayName(MessagesUtil.color("&a&l" + portal.getId()));
		meta.setLore(getPortalItemLore(portal));
		portalItem.setItemMeta(meta);
		return portalItem;
	}

	private List<String> getPortalItemLore(AbstractPortal portal) {
		List<String> lore = new ArrayList<String>();
		lore.add(MessagesUtil.color(String.format("&6&l- x/y/z: &a%.2f&6&l/&a%.2f&6&l/&a%.2f",
				portal.getCenter().getX(), portal.getCenter().getY(), portal.getCenter().getZ())));
		lore.add(MessagesUtil.color("&6&l- &e&lLeft click &6to open settings"));
		lore.add(MessagesUtil.color("&6&l- &e&lRight click &6to teleport"));
		return lore;
	}

	private Consumer<InventoryClickEvent> getPortalConsumer(int slot) {
		return e -> {
			AbstractPortal portal = toList.get(slot);
			if (e.getClick().equals(ClickType.LEFT)) {
				e.getWhoClicked().openInventory(new PortalSettingsInventoryHolder(plugin, portal).getInventory());
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				e.getWhoClicked().teleport(portal.getCenter(), TeleportCause.PLUGIN);
			}
		};
	}

}
