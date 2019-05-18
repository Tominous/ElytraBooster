package com.github.zamponimarco.elytrabooster.trails.factory;

import com.github.zamponimarco.elytrabooster.trails.BoostTrail;
import com.github.zamponimarco.elytrabooster.trails.HelixBoostTrail;
import com.github.zamponimarco.elytrabooster.trails.SimpleBoostTrail;
import com.github.zamponimarco.elytrabooster.trails.NoBoostTrail;
import com.github.zamponimarco.elytrabooster.trails.RainbowBoostTrail;

public class BoostTrailFactory {

	public static BoostTrail buildBoostTrail(String trailString) {
		
		if(trailString == null) {
			trailString = "";
		}
		
		String[] trailArray = trailString.split(":");
		String trailType = trailArray[0];
		String trailParticle = trailArray.length >= 2? trailArray[1]:"";
		
		switch(trailType) {
		case "none":
			return new NoBoostTrail();
		case "rainbow":
			return new RainbowBoostTrail();
		case "helix":
			return new HelixBoostTrail(trailParticle);
		case "simple":
		default:
			return new SimpleBoostTrail(trailParticle);
		}
	}

}
