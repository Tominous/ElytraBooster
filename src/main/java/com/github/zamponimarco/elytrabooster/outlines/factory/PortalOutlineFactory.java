package com.github.zamponimarco.elytrabooster.outlines.factory;

import com.github.zamponimarco.elytrabooster.outlines.BlockPortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.NoOutlinePortal;
import com.github.zamponimarco.elytrabooster.outlines.ParticlePortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.PortalOutline;

public class PortalOutlineFactory {

	public static PortalOutline buildPortalOutline(boolean isBlock, String outlineType, String cooldownType) {
		if (!isBlock) {
			return new ParticlePortalOutline(outlineType, cooldownType);
		} else {
			return outlineType=="AIR"?new NoOutlinePortal():new BlockPortalOutline(outlineType, cooldownType);
		}
	}

}
