package com.github.zamponimarco.elytrabooster.gui;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.boosters.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.settings.DoubleSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.IntegerSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.SettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.EntityEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.TrailEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;
import com.google.common.collect.Lists;

public class SpawnerSettingsInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final String INITIAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJjZGM2Zjg4Yzg1M2U4MzE0OTVhMTc0NmViMjdhYTYxYjlkYWMyZTg2YTQ0Yjk1MjJlM2UyYjdkYzUifX19=";
	private static final String FINAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI5MjBjMzgxNWI5YzQ1OTJlNjQwOGUzMjIzZjMxMzUxZmM1NzhmMzU1OTFiYzdmOWJlYmQyMWVmZDhhMDk3In19fQ===";
	private static final String BOOST_DURATION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q1MWM4M2NjMWViY2E1YTFiNmU2Nzk0N2UyMGI0YTJhNmM5ZWZlYTBjZjQ2OTI5NDQ4ZTBlMzc0MTZkNTgzMyJ9fX0====";
	private static final String COOLDOWN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMyYzYxNzE1MzJhMmE4N2YwZWViMjhlZGQwMTA4MzNmMzNmMGFlNjg0MWE1MjRlMWI1MjAwYTM1ZDM4NTA1MCJ9fX0=";
	private static final String TRAIL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzYxOWJmNjI4NjNlYzExNTc3ZDZlZjY1ZWZkYzNmOWRlNGRmNDE0MjAyZWQxZmYxZGU5ZWM3NmI2MWEzZjY2NyJ9fX0========";
	private static final String MIN_RADIUS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M4ZTdkNDZkNjkzMzQxZjkxZDI4NjcyNmYyNTU1ZWYxNTUxNGUzNDYwYjI3NWU5NzQ3ODQyYmM5ZTUzZGYifX19========";
	private static final String MAX_RADIUS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTVhNzQwMzJhMjlmNzk0ZmQ1NjY0Yzg2N2VjYjQ0ZGE4MjE1ZDE2MGJmYzgwZDJiOTMzZTRiNTNjMWU5OWNhIn19fQ==========";
	private static final String MAX_ENTITIES_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTIxZDJjY2U5NTViZmZjZGE0Y2MwMzY3Yzg4NjQ0NDg4YjU5NWYyN2ZjZTE2N2I0MzRjYTViOGNkNDQ4ZCJ9fX0===========";
	private static final String ENTITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ=============";

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
				getSpawnerSetting(HeadsUtil.skullFromValue(MIN_RADIUS_HEAD), "minRadius", spawner.getMinRadius()),
				getSettingConsumer("minRadius", spawner.getMinRadius(), DoubleSettingInventoryHolder.class));
		registerClickConsumer(3,
				getSpawnerSetting(HeadsUtil.skullFromValue(MAX_RADIUS_HEAD), "maxRadius", spawner.getMaxRadius()),
				getSettingConsumer("maxRadius", spawner.getMaxRadius(), DoubleSettingInventoryHolder.class));
		registerClickConsumer(5,
				getSpawnerSetting(HeadsUtil.skullFromValue(COOLDOWN_HEAD), "cooldown", spawner.getCooldown()),
				getSettingConsumer("cooldown", spawner.getCooldown(), IntegerSettingInventoryHolder.class));
		registerClickConsumer(6,
				getSpawnerSetting(HeadsUtil.skullFromValue(MAX_ENTITIES_HEAD), "maxEntities",
						spawner.getHolder().getMaxEntities()),
				getSettingConsumer("maxEntities", spawner.getHolder().getMaxEntities(),
						IntegerSettingInventoryHolder.class));
		registerClickConsumer(12,
				getSpawnerSetting(HeadsUtil.skullFromValue(INITIAL_VELOCITY_HEAD), "initialVelocity",
						spawner.getHolder().getBoost().getInitialVelocity()),
				getSettingConsumer("initialVelocity", spawner.getHolder().getBoost().getInitialVelocity(),
						DoubleSettingInventoryHolder.class));
		registerClickConsumer(13,
				getSpawnerSetting(HeadsUtil.skullFromValue(FINAL_VELOCITY_HEAD), "finalVelocity",
						spawner.getHolder().getBoost().getFinalVelocity()),
				getSettingConsumer("finalVelocity", spawner.getHolder().getBoost().getFinalVelocity(),
						DoubleSettingInventoryHolder.class));
		registerClickConsumer(14,
				getSpawnerSetting(HeadsUtil.skullFromValue(BOOST_DURATION_HEAD), "boostDuration",
						spawner.getHolder().getBoost().getBoostDuration()),
				getSettingConsumer("boostDuration", spawner.getHolder().getBoost().getBoostDuration(),
						IntegerSettingInventoryHolder.class));
		registerClickConsumer(17,
				getSpawnerSetting(HeadsUtil.skullFromValue(ENTITY_HEAD), "entity", spawner.getHolder().getEntity()),
				getSettingConsumer("entity", spawner.getHolder().getEntity(), EntityEnumSettingInventoryHolder.class));
		registerClickConsumer(26,
				getSpawnerSetting(HeadsUtil.skullFromValue(TRAIL_HEAD), "trail",
						spawner.getHolder().getBoost().getTrail().getName()),
				getSettingConsumer("trail", spawner.getHolder().getBoost().getTrail().getName(),
						TrailEnumSettingInventoryHolder.class));
		registerClickConsumer(18, getDeleteItem(), getDeleteConsumer(spawner));
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private Consumer<InventoryClickEvent> getSettingConsumer(String key, Object value,
			Class<? extends SettingInventoryHolder> clazz) {
		return e -> {
			if (e.getClick().equals(ClickType.LEFT)) {
				HumanEntity p = e.getWhoClicked();
				try {
					p.closeInventory();
					p.openInventory(
							clazz.getConstructor(ElytraBooster.class, String.class, Booster.class, HumanEntity.class,
									Object.class).newInstance(plugin, key, spawner, p, value).getInventory());
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
