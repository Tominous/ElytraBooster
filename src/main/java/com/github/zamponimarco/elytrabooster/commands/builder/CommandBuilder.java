package com.github.zamponimarco.elytrabooster.commands.builder;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.commands.AbstractCommand;
import com.github.zamponimarco.elytrabooster.commands.ElytraBoosterCreateCommand;
import com.github.zamponimarco.elytrabooster.commands.ElytraBoosterDeleteCommand;
import com.github.zamponimarco.elytrabooster.commands.ElytraBoosterHelpCommand;
import com.github.zamponimarco.elytrabooster.commands.ElytraBoosterListCommand;
import com.github.zamponimarco.elytrabooster.commands.ElytraBoosterMoveCommand;
import com.github.zamponimarco.elytrabooster.commands.ElytraBoosterNearCommand;
import com.github.zamponimarco.elytrabooster.commands.ElytraBoosterReloadCommand;
import com.github.zamponimarco.elytrabooster.commands.ElytraBoosterSetCommand;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class CommandBuilder {

	public static AbstractCommand buildCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments, boolean isSenderPlayer) {
		switch(subCommand) {
		case "help":
			return new ElytraBoosterHelpCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
		case "create":
			return new ElytraBoosterCreateCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
		case "set":
			return new ElytraBoosterSetCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
		case "delete":
			return new ElytraBoosterDeleteCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
		case "reload":
			return new ElytraBoosterReloadCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
		case "list":
			return new ElytraBoosterListCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
		case "move":
			return new ElytraBoosterMoveCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
		case "near":
			return new ElytraBoosterNearCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
		default:
			return new ElytraBoosterHelpCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
		}
	}
	
}
