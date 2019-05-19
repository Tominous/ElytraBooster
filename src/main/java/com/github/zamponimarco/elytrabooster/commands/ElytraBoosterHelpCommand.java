package com.github.zamponimarco.elytrabooster.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class ElytraBoosterHelpCommand extends AbstractCommand {

	public ElytraBoosterHelpCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	private List<String> pages;

	@Override
	protected void execute() {
		pages = new ArrayList<String>();
		setUpPages();
		int numberOfPages = 1;

		int pageToPrint;
		if (arguments.length >= 1 && StringUtils.isNumeric(arguments[0]) && Integer.valueOf(arguments[0]) > numberOfPages) {
			pageToPrint = Integer.valueOf(arguments[0]) - 1;
		} else {
			pageToPrint = 0;
		}

		sender.sendMessage(pages.get(pageToPrint));

	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

	private void setUpPages() {
		StringBuilder page = new StringBuilder();
		page.append(MessagesUtil.header("ElytraBooster Help"));
		page.append(MessagesUtil.color(String.format("&2/eb help &c[page] &7Print the help message.\n"
				+ "&2/eb list &c[page] &7List portals, use argument [page] to print other pages.\n"
				+ "&2/eb create &c[id] &7Create a new portal.\n" + "&2/eb delete &c[id] &7Deletes the given portal.\n"
				+ "&2/eb move &c[id] <x> <y> <z> &7Move the portal to your location or to the given coords.\n"
				+ "&2/eb set &c[param:value,...] &7Sets the params to the values given in input.\n"
				+ "&2/eb near &c[radius] &7List all the portals within [radius] blocks.\n"
				+ "&2/eb reload &7Reloads ElytraBooster configs.\n")));
		page.append(MessagesUtil.footer(1, 1));
		pages.add(page.toString());
	}

}
