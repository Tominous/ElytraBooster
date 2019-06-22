package com.github.zamponimarco.elytrabooster.commands.spawner;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class SpawnerHelpCommand extends SpawnerCommand {

	public SpawnerHelpCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
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
		page.append(MessagesUtil.color(String.format("&2/eb spawner help &c[page] &7Print the spawners help message.\n"
				+ "&2/eb spawner list &7List spawners.\n"
				+ "&2/eb spawner create &c[id] &7Create a new portal.\n" + "&2/eb spawner delete &c[id] &7Deletes the given spawner.\n"
				+ "&2/eb spawner move &c[id] <x> <y> <z> &7Move the spawner to your location or to the given coords, if present.\n"
				+ "&2/eb spawner set &c[id] [param:value,...] &7Sets the params to the values given in input.\n"
				+ "&2/eb spawner near &c[radius] &7List all the spawners within [radius] blocks.\n")));
		page.append(MessagesUtil.footer(1, 1));
		pages.add(page.toString());
	}

}
