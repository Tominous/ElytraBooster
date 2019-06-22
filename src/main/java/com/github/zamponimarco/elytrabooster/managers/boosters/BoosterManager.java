package com.github.zamponimarco.elytrabooster.managers.boosters;

import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.boosters.factory.BoosterFactory;
import com.github.zamponimarco.elytrabooster.managers.DataManager;

public interface BoosterManager<T extends Booster> extends DataManager {
	
	public ConfigurationSection createDefaultBoosterConfiguration(Player creator, String id);
	
	public void setParam(String id, String param, String value);
	
	public T getBooster(String id);
	
	public void addBooster(String id);
	
	public void setBooster(String id, T booster);

	public void removeBooster(String id);
	
	public T reloadBooster(Booster booster);

	public YamlConfiguration getDataYaml();
	
	public void saveConfig();
	
	public Map<String, T> getBoostersMap();
	
	public Class<? extends BoosterFactory> getFactory();
}
