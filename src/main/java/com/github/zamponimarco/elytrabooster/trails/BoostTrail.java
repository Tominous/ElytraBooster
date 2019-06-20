package com.github.zamponimarco.elytrabooster.trails;

import org.bukkit.entity.Player;

/**
 * Interface that manages the creatin of boost trails
 * 
 * @author Marco
 *
 */
public interface BoostTrail {

	/**
	 * Spawns a trail behind a player
	 * 
	 * @param player
	 */
	public void spawnTrail(Player player);
	
	public String getName();
	
}
