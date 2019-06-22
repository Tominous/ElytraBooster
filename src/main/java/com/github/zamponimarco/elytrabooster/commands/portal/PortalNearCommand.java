package com.github.zamponimarco.elytrabooster.commands.portal;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.boosters.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.PortalsListInventoryHolder;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class PortalNearCommand extends PortalCommand {

	public PortalNearCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		Player player = (Player) sender;
		int range;
		if (arguments.length >= 1 && StringUtils.isNumeric(arguments[0])) {
			range = Integer.valueOf(arguments[0]);
		} else {
			return;
		}

		List<AbstractPortal> portals = plugin.getPortalManager().getBoostersMap().values().stream().filter(portal -> {
			return portal.getCenter().distance(player.getLocation()) <= range;
		}).collect(Collectors.toList());
		portals.sort((p1, p2) -> (int) (p1.getCenter().distance(player.getLocation())
				- p2.getCenter().distance(player.getLocation())));

		player.openInventory(new PortalsListInventoryHolder(plugin,
				MessagesUtil.color(String.format("&1&lPortals within %d blocks", range)), portals, 1).getInventory());

	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

}
