package com.github.zamponimarco.elytrabooster.boosts;

import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

public abstract class Boost {

	private int boostDuration;
	private double initialVelocity;
	private double finalVelocity;
	private BoostTrail trail;
	
	public Boost(int boostDuration, double initialVelocity, double finalVelocity, BoostTrail trail) {
		this.boostDuration = boostDuration;
		this.initialVelocity = initialVelocity;
		this.finalVelocity = finalVelocity;
		this.trail = trail;
	}

	public int getBoostDuration() {
		return boostDuration;
	}

	public double getInitialVelocity() {
		return initialVelocity;
	}

	public double getFinalVelocity() {
		return finalVelocity;
	}

	public BoostTrail getTrail() {
		return trail;
	}
	
	
}
