package com.github.zamponimarco.elytrabooster.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class ElytraBoosterNearCommand extends AbstractCommand {

	public ElytraBoosterNearCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
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

		List<AbstractPortal> portals = plugin.getPortalManager().getPortalsMap().values().stream().filter(portal -> {
			return portal.getCenter().distance(player.getLocation()) <= range;
		}).collect(Collectors.toList());
		int count = portals.size();
		int portalsPerPage = 5;
		int numberOfPages = (int) Math.ceil((double) count / portalsPerPage);
		List<String> pages = new ArrayList<String>(numberOfPages);

		int currentPage = 0;
		StringBuilder page = new StringBuilder();
		int check = 0;
		for (int i = 0; i < count; i++) {
			page.append(MessagesUtil.delimiter());
			page.append(portals.get(i).toString());
			check = (check + 1) % portalsPerPage;
			if (check == 0) {
				page.append(MessagesUtil.delimiter());
				page.append(MessagesUtil.footer(++currentPage, numberOfPages));
				page.append(" \n \n");
				pages.add(page.toString());
				page.delete(0, page.capacity());
			}
		}
		page.append(MessagesUtil.delimiter());
		page.append(MessagesUtil.footer(++currentPage, numberOfPages));
		page.append(" \n \n");
		pages.add(page.toString());

		int pageToPrint;
		if (arguments.length >= 2 && StringUtils.isNumeric(arguments[0]) && Integer.valueOf(arguments[0]) > numberOfPages) {
			pageToPrint = Integer.valueOf(arguments[1]) - 1;
		} else {
			pageToPrint = 0;
		}

		sender.sendMessage(MessagesUtil.header(String.format("Portals in %d blocks range", range)));
		sender.sendMessage(pages.get(pageToPrint));

	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

}
