package com.github.zamponimarco.elytrabooster.gui;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.settings.SpawnerDoubleSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.SpawnerIntegerSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.SpawnerSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.SpawnerStringSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;
import com.google.common.collect.Lists;

public class SpawnerSettingsInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final String INITIAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJjZGM2Zjg4Yzg1M2U4MzE0OTVhMTc0NmViMjdhYTYxYjlkYWMyZTg2YTQ0Yjk1MjJlM2UyYjdkYzUifX19=";
	private static final String FINAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI5MjBjMzgxNWI5YzQ1OTJlNjQwOGUzMjIzZjMxMzUxZmM1NzhmMzU1OTFiYzdmOWJlYmQyMWVmZDhhMDk3In19fQ===";
	private static final String BOOST_DURATION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q1MWM4M2NjMWViY2E1YTFiNmU2Nzk0N2UyMGI0YTJhNmM5ZWZlYTBjZjQ2OTI5NDQ4ZTBlMzc0MTZkNTgzMyJ9fX0====";

	private AbstractSpawner spawner;

	public SpawnerSettingsInventoryHolder(ElytraBooster plugin, AbstractSpawner spawner) {
		super(plugin);
		this.spawner = spawner;
		initializeInventory();
	}

	@Override
	protected void initializeInventory() {
		this.inventory = Bukkit.createInventory(this, 27,
				MessagesUtil.color(String.format("&6&lSpawner: &1&l%s", spawner.getId())));
		registerClickConsumer(2,
				getSpawnerSetting(HeadsUtil.skullFromValue(INITIAL_VELOCITY_HEAD), "minRadius",
						spawner.getMinRadius()),
				getSettingConsumer("minRadius", spawner.getMinRadius(),
						SpawnerDoubleSettingInventoryHolder.class));
		registerClickConsumer(3,
				getSpawnerSetting(HeadsUtil.skullFromValue(FINAL_VELOCITY_HEAD), "maxRadius",
						spawner.getMaxRadius()),
				getSettingConsumer("maxRadius", spawner.getMaxRadius(),
						SpawnerDoubleSettingInventoryHolder.class));
		registerClickConsumer(5,
				getSpawnerSetting(HeadsUtil.skullFromValue(BOOST_DURATION_HEAD), "cooldown",
						spawner.getCooldown()),
				getSettingConsumer("cooldown", spawner.getCooldown(),
						SpawnerIntegerSettingInventoryHolder.class));
		registerClickConsumer(6,
				getSpawnerSetting(HeadsUtil.skullFromValue(BOOST_DURATION_HEAD), "maxEntities",
						spawner.getHolder().getMaxEntities()),
				getSettingConsumer("maxEntities", spawner.getHolder().getMaxEntities(),
						SpawnerIntegerSettingInventoryHolder.class));
		registerClickConsumer(12,
				getSpawnerSetting(HeadsUtil.skullFromValue(INITIAL_VELOCITY_HEAD), "initialVelocity",
						spawner.getHolder().getBoost().getInitialVelocity()),
				getSettingConsumer("initialVelocity", spawner.getHolder().getBoost().getInitialVelocity(),
						SpawnerDoubleSettingInventoryHolder.class));
		registerClickConsumer(13,
				getSpawnerSetting(HeadsUtil.skullFromValue(FINAL_VELOCITY_HEAD), "finalVelocity",
						spawner.getHolder().getBoost().getFinalVelocity()),
				getSettingConsumer("finalVelocity", spawner.getHolder().getBoost().getFinalVelocity(),
						SpawnerDoubleSettingInventoryHolder.class));
		registerClickConsumer(14,
				getSpawnerSetting(HeadsUtil.skullFromValue(BOOST_DURATION_HEAD), "boostDuration",
						spawner.getHolder().getBoost().getBoostDuration()),
				getSettingConsumer("boostDuration", spawner.getHolder().getBoost().getBoostDuration(),
						SpawnerIntegerSettingInventoryHolder.class));
		registerClickConsumer(26,
				getSpawnerSetting(HeadsUtil.skullFromValue(BOOST_DURATION_HEAD), "trail",
						spawner.getHolder().getBoost().getTrail().getName()),
				getSettingConsumer("trail", spawner.getHolder().getBoost().getTrail().getName(),
						SpawnerStringSettingInventoryHolder.class));
	}

	private Consumer<InventoryClickEvent> getSettingConsumer(String key, Object value,
			Class<? extends SpawnerSettingInventoryHolder> clazz) {
		return e -> {
			if (e.getClick().equals(ClickType.LEFT)) {
				HumanEntity p = e.getWhoClicked();
				try {
					p.closeInventory();
					p.openInventory(clazz.getConstructor(ElytraBooster.class, String.class, AbstractSpawner.class,
							HumanEntity.class, Object.class).newInstance(plugin, key, spawner, p, value)
							.getInventory());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	private List<String> getDefaultLore() {
		return Lists.newArrayList(MessagesUtil.color("&6&l- &e&lLeft click &6to modify"));
	}

	private ItemStack getSpawnerSetting(ItemStack item, String key, Object value) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color(String.format("&6&l%s = &e&l%s", key, value.toString())));
		meta.setLore(getDefaultLore());
		item.setItemMeta(meta);
		return item;
	}

}
