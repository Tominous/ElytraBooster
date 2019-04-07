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
		AbstractPortal portal = portalManager.getPortal(arguments[0]);
		portal.stopPortalTask();
		portalManager.getPortalsMap().remove(arguments[0]);
		portalManager.getDataYaml().set(arguments[0], null);
		portalManager.saveConfig();
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
