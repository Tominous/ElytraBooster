package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.PortalManager;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class ElytraBoosterReloadCommand extends AbstractCommand {

	public ElytraBoosterReloadCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		PortalManager portalManager = plugin.getPortalManager();
		sender.sendMessage(MessagesUtil.color("&cReloading &6ElytraBooster"));
		
		portalManager.getPortalsMap().forEach((id, portal) -> portal.stopPortalTask());
		plugin.getSettingsManager().reloadData();
		plugin.getPortalManager().reloadData();
		
		sender.sendMessage(MessagesUtil.color("&aReloaded &6ElytraBooster"));
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
