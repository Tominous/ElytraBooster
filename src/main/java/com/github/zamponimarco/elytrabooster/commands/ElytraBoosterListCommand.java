package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class ElytraBoosterListCommand extends AbstractCommand {

	public ElytraBoosterListCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	// TODO everything
	@Override
	protected void execute() {
		
		plugin.getPortalManager().getPortalsMap().forEach((id, portal) -> sender.sendMessage(portal.toString()));

	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
