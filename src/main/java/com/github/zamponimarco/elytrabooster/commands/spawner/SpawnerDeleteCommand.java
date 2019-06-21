package com.github.zamponimarco.elytrabooster.commands.spawner;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class SpawnerDeleteCommand extends SpawnerCommand {

	public SpawnerDeleteCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
