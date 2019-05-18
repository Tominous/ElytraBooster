package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class ElytraBoosterNearCommand extends AbstractCommand {

	public ElytraBoosterNearCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		Player player = (Player) sender;

		if (arguments.length <= 1) {
			return;
		}

		double range = Double.valueOf(arguments[0]);

		plugin.getPortalManager().getPortalsMap().forEach((id, portal) -> {
			if (portal.getCenter().distance(player.getLocation()) <= range) {
				player.sendMessage(portal.toString());
			}
		});

	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

}
