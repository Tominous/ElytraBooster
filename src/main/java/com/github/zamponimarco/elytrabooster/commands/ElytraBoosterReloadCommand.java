package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.manager.PortalManager;

public class ElytraBoosterReloadCommand extends AbstractCommand {

	public ElytraBoosterReloadCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	// TODO pretty ok but rewatch
	@Override
	protected void commandExecution() {
		
		PortalManager portalManager = plugin.getPortalManager();
		
		portalManager.getPortalsMap().forEach((id, portal) -> portal.stopPortalTask());
		plugin.getPortalManager().reloadData();

	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
