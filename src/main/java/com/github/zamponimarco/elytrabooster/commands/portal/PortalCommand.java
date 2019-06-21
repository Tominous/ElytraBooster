package com.github.zamponimarco.elytrabooster.commands.portal;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.commands.AbstractCommand;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public abstract class PortalCommand extends AbstractCommand {

	public PortalCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}
	
	
	protected void invalidPortal() {
		sender.sendMessage((MessagesUtil.color("&cPortal passed in input is invalid")));
	}

}
