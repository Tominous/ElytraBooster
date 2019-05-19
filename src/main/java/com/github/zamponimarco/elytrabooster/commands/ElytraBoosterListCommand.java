package com.github.zamponimarco.elytrabooster.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;
import com.google.common.collect.Lists;

public class ElytraBoosterListCommand extends AbstractCommand {

	public ElytraBoosterListCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {

		List<AbstractPortal> portals = Lists.newArrayList(plugin.getPortalManager().getPortalsMap().values());
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
		if (arguments.length >= 1 && StringUtils.isNumeric(arguments[0]) && Integer.valueOf(arguments[0]) > numberOfPages) {
			pageToPrint = Integer.valueOf(arguments[0]) - 1;
		} else {
			pageToPrint = 0;
		}

		sender.sendMessage(MessagesUtil.header("Portals List"));
		sender.sendMessage(pages.get(pageToPrint));

	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
