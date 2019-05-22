package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.PortalManager;
import com.github.zamponimarco.elytrabooster.portals.factory.PortalFactory;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class ElytraBoosterCreateCommand extends AbstractCommand {

	public ElytraBoosterCreateCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		Player player = (Player) sender;
		PortalManager portalManager = plugin.getPortalManager();
		if(arguments.length < 1) {
			incorrectUsage();
			return;
		}
		String newPortalId = arguments[0];

		if (!portalManager.getPortalsMap().containsKey(newPortalId)) {
			portalManager.setPortal(newPortalId, PortalFactory.buildPortal(plugin, portalManager,
					portalManager.createDefaultPortalConfiguration(player, newPortalId)));
			player.sendMessage(MessagesUtil.color("&aPortal created, &6ID: &a" + newPortalId));
		} else {
			invalidPortal();
		}
	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

}
