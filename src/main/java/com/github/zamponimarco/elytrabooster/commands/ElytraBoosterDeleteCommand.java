package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.PortalManager;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class ElytraBoosterDeleteCommand extends AbstractCommand {

	public ElytraBoosterDeleteCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		PortalManager portalManager = plugin.getPortalManager();
		String id = arguments[0];

		AbstractPortal portal;
		if (portalManager.getPortalsMap().containsKey(id)) {
			portal = portalManager.getPortal(id);
		} else {
			sender.sendMessage(MessagesUtil.color("&4Portal deletion failed"));
			return;
		}
		portal.stopPortalTask();
		portalManager.removePortal(id);
		portalManager.getDataYaml().set(id, null);
		portalManager.saveConfig();
		sender.sendMessage(MessagesUtil.color("&aPortal deleted, &6ID: &a" + id));
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
