package com.github.zamponimarco.elytrabooster.outline.factory;

import com.github.zamponimarco.elytrabooster.outline.BlockPortalOutline;
import com.github.zamponimarco.elytrabooster.outline.ParticlePortalOutline;
import com.github.zamponimarco.elytrabooster.outline.PortalOutline;

public class PortalOutlineFactory {

	public static PortalOutline buildPortalOutline(boolean isBlock, String outlineType) {
		return isBlock ? new BlockPortalOutline(outlineType) : new ParticlePortalOutline(outlineType);
	}

}
