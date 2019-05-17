package com.github.zamponimarco.elytrabooster.trails;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

/**
 * spawns rainbow trail
 * 
 * @author Marco
 *
 */
public class RainbowBoostTrail implements BoostTrail {

	private enum RainbowColor {

		VIOLET(148, 0, 211), INDOIGO(75, 0, 130), BLUE(0, 0, 255), GREEN(0, 255, 0), YELLOW(255, 255, 0),
		ORANGE(255, 127, 0), RED(255, 0, 0);

		private Color color;

		RainbowColor(int r, int g, int b) {
			color = Color.fromRGB(r, g, b);
		}

		public Color getColor() {
			return color;
		}
	}

	int currentColor;

	@Override
	public void spawnTrail(Player player) {
		currentColor = (currentColor + 1) % 7;
		player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 1,
				new Particle.DustOptions(RainbowColor.values()[currentColor].getColor(), 3));
	}

}
