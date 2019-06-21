package com.github.zamponimarco.elytrabooster.core;

import com.github.zamponimarco.elytrabooster.managers.BoosterManager;

public interface Booster {

	public BoosterManager<?> getDataManager();
	
	public String getId();
	
	public void stopBoosterTask();
	
}
