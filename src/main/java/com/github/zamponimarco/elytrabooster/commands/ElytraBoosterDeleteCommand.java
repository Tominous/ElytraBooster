package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.manager.PortalManager;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;

public class ElytraBoosterDeleteCommand extends AbstractCommand {

	public ElytraBoosterDeleteCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	// TODO everything
	@Override
	protected void commandExecution() {
		PortalManager portalManager = plugin.getPortalManager();
		String id = arguments[0];
		AbstractPortal portal = portalManager.getPortal(id);
		portal.stopPortalTask();
		portalManager.removePortal(id);
		portalManager.getDataYaml().set(id, null);
		portalManager.saveConfig();
	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

}
