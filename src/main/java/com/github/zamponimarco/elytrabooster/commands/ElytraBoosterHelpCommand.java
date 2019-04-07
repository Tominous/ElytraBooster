package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class ElytraBoosterHelpCommand extends AbstractCommand {

	public ElytraBoosterHelpCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	// TODO literally everything
	@Override
	protected void commandExecution() {

		sender.sendMessage("help");

	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
