package com.github.zamponimarco.elytrabooster.boosts;

import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

public class SimpleBoost extends Boost {

	public SimpleBoost(int boostDuration, double initialVelocity, double finalVelocity, BoostTrail trail) {
		super(boostDuration, initialVelocity, finalVelocity, trail);
	}

}
