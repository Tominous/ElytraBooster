package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.manager.PortalManager;
import com.github.zamponimarco.elytrabooster.portals.factory.PortalFactory;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class ElytraBoosterCreateCommand extends AbstractCommand {

	public ElytraBoosterCreateCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	// TODO everything
	@Override
	protected void commandExecution() {
		Player player = (Player) sender;
		PortalManager portalManager = plugin.getPortalManager();
		String id = arguments[0];

		if (!portalManager.getPortalsMap().containsKey(id)) {
			portalManager.setPortal(id, PortalFactory.buildPortal(plugin, portalManager,
					portalManager.createDefaultPortalConfiguration(player, id)));
			player.sendMessage(MessagesUtil.color("&aPortal created, &6ID: &a" + id));
		} else {
			player.sendMessage(MessagesUtil.color("&4Portal creation failed"));
		}
	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

}
