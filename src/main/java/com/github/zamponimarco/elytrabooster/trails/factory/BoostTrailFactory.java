package com.github.zamponimarco.elytrabooster.trails.factory;

import com.github.zamponimarco.elytrabooster.trails.BoostTrail;
import com.github.zamponimarco.elytrabooster.trails.HelixBoostTrail;
import com.github.zamponimarco.elytrabooster.trails.FireworkBoostTrail;
import com.github.zamponimarco.elytrabooster.trails.NoBoostTrail;
import com.github.zamponimarco.elytrabooster.trails.RainbowBoostTrail;

public class BoostTrailFactory {

	public static BoostTrail buildBoostTrail(String trailString) {
		
		if(trailString == null) {
			trailString = "";
		}
		
		switch(trailString) {
		case "none":
			return new NoBoostTrail();
		case "rainbow":
			return new RainbowBoostTrail();
		case "helix":
			return new HelixBoostTrail();
		case "firework":
		default:
			return new FireworkBoostTrail();
		}
	}

}
