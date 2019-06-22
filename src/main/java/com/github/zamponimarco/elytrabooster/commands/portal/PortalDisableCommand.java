package com.github.zamponimarco.elytrabooster.commands.portal;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.boosters.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.boosters.PortalManager;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class PortalDisableCommand extends PortalCommand {

	public PortalDisableCommand(ElytraBooster plugin, CommandSender sender, String subCommand,
			String[] arguments, boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		PortalManager portalManager = plugin.getPortalManager();
		if(arguments.length < 1) {
			incorrectUsage();
			return;
		}
		String id = arguments[0];
		AbstractPortal portal;
		if (portalManager.getBoostersMap().containsKey(id)) {
			portal = portalManager.getBooster(id);
		} else {
			invalidPortal();
			return;
		}
		portal.stopBoosterTask();
		sender.sendMessage(MessagesUtil.color("&aPortal disabled, &6ID: &a" + id));
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
