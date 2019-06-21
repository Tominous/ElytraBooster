package com.github.zamponimarco.elytrabooster.commands.factory;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.commands.AbstractCommand;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public interface CommandFactory {

	public AbstractCommand buildCommand(ElytraBooster plugin, CommandSender sender, String subCommand,
			String[] arguments, boolean isSenderPlayer);

}
