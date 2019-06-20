package com.github.zamponimarco.elytrabooster.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.PortalManager;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class ElytraBoosterSetCommand extends AbstractCommand {

	public ElytraBoosterSetCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {

		PortalManager portalManager = plugin.getPortalManager();
		if (arguments.length < 2) {
			incorrectUsage();
			return;
		}
		String id = arguments[0];
		AbstractPortal portal = portalManager.getPortal(id);

		if (portal == null) {
			invalidPortal();
			return;
		}

		try {
			Arrays.asList(arguments[1].split(",")).forEach(string -> {
				String[] argument = string.split(":");
				portalManager.setParam(id, argument[0], argument[1]);
				sender.sendMessage(MessagesUtil
						.color("&aPortal modified, &6ID: &a" + id + ", &6" + argument[0] + ": &a" + argument[1]));
			});
		} catch (Exception e) {
			incorrectUsage();
			return;
		}
		
		portalManager.reloadPortal(portal);

	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
