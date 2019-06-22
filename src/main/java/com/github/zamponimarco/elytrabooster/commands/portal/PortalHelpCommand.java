package com.github.zamponimarco.elytrabooster.commands.portal;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class PortalHelpCommand extends PortalCommand {

	public PortalHelpCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
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
		page.append(MessagesUtil.color(String.format("&2/eb portal help &c[page] &7Print the portals help message.\n"
				+ "&2/eb portal list &7List portals.\n"
				+ "&2/eb portal create &c[id] &7Create a new portal.\n" + "&2/eb portal delete &c[id] &7Deletes the given portal.\n"
				+ "&2/eb portal move &c[id] <x> <y> <z> &7Move the portal to your location or to the given coords, if present.\n"
				+ "&2/eb portal set &c[id] [param:value,...] &7Sets the params to the values given in input.\n"
				+ "&2/eb portal near &c[radius] &7List all the portals within [radius] blocks.\n"
				+ "&2/eb portal disable &c[id] &7Disable the portal named [id]\n"
				+ "&2/eb portal enable &c[id] &7Enable the portal named [id]\n")));
		page.append(MessagesUtil.footer(1, 1));
		pages.add(page.toString());
	}

}
