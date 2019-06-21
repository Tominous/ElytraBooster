package com.github.zamponimarco.elytrabooster.commands.factory;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.commands.AbstractCommand;
import com.github.zamponimarco.elytrabooster.commands.ElytraBoosterHelpCommand;
import com.github.zamponimarco.elytrabooster.commands.ElytraBoosterReloadCommand;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class ElytraBoosterCommandFactory implements CommandFactory {

	@Override
	public AbstractCommand buildCommand(ElytraBooster plugin, CommandSender sender, String subCommand,
			String[] arguments, boolean isSenderPlayer) {
		switch (subCommand) {
		case "reload":
			return new ElytraBoosterReloadCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
		default:
			return new ElytraBoosterHelpCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
		}
	}

}
