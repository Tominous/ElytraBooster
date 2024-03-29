package com.github.zamponimarco.elytrabooster.commands.portal;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.boosters.factory.PortalFactory;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.boosters.PortalManager;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class PortalCreateCommand extends PortalCommand {

	public PortalCreateCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
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

		if (!portalManager.getBoostersMap().containsKey(newPortalId)) {
			portalManager.setBooster(newPortalId, PortalFactory.buildBooster(plugin,
					portalManager.createDefaultBoosterConfiguration(player, newPortalId)));
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
