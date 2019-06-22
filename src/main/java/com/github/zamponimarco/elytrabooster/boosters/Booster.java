package com.github.zamponimarco.elytrabooster.boosters;

import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;

public interface Booster {

	public BoosterManager<?> getDataManager();
	
	public String getId();
	
	public void stopBoosterTask();
	
}
