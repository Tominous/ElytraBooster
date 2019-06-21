package com.github.zamponimarco.elytrabooster.commands.factory;

import org.apache.commons.lang.WordUtils;
import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.commands.AbstractCommand;
import com.github.zamponimarco.elytrabooster.commands.spawner.SpawnerHelpCommand;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class SpawnerCommandFactory implements CommandFactory {

	@Override
	public AbstractCommand buildCommand(ElytraBooster plugin, CommandSender sender, String subCommand,
			String[] arguments, boolean isSenderPlayer) {
		try {
			return (AbstractCommand) Class
					.forName("com.github.zamponimarco.elytrabooster.commands.spawner.Spawner"
							+ WordUtils.capitalize(subCommand) + "Command")
					.getConstructor(ElytraBooster.class, CommandSender.class, String.class, String[].class,
							boolean.class)
					.newInstance(plugin, sender, subCommand, arguments, isSenderPlayer);
		} catch (Exception e) {
		}
		return new SpawnerHelpCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

}
