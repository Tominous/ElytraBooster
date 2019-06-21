package com.github.zamponimarco.elytrabooster.managers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.Booster;

public interface BoosterManager<T extends Booster> extends DataManager {
	
	public ConfigurationSection createDefaultBoosterConfiguration(Player creator, String id);
	
	public void setParam(String id, String param, String value);
	
	public T getBooster(String id);
	
	public void setBooster(String id, T booster);

	public void removeBooster(String id);
	
	public T reloadBooster(Booster booster);
}
