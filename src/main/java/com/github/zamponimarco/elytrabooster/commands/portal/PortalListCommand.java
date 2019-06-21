package com.github.zamponimarco.elytrabooster.commands.portal;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.PortalsListInventoryHolder;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;
import com.google.common.collect.Lists;

public class PortalListCommand extends PortalCommand {

	public PortalListCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		Player player = (Player) sender;
		List<AbstractPortal> portals = Lists.newArrayList(plugin.getPortalManager().getPortalsMap().values());
		portals.sort((p1, p2) -> (int) (p1.getCenter().distance(player.getLocation())
				- p2.getCenter().distance(player.getLocation())));

		player.openInventory(new PortalsListInventoryHolder(plugin, MessagesUtil.color("&1&lPortals list"), portals, 1).getInventory());
	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

}
