package com.github.zamponimarco.elytrabooster.commands.spawner;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.boosters.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.boosters.SpawnerManager;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class SpawnerSetCommand extends SpawnerCommand {

	public SpawnerSetCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {

		SpawnerManager spawnerManager = plugin.getSpawnerManager();
		if (arguments.length < 2) {
			incorrectUsage();
			return;
		}
		String id = arguments[0];
		AbstractSpawner spawner = spawnerManager.getBooster(id);

		if (spawner == null) {
			invalidSpawner();
			return;
		}

		try {
			Arrays.asList(arguments[1].split(",")).forEach(string -> {
				String[] argument = string.split(":");
				spawnerManager.setParam(id, argument[0], argument[1]);
				sender.sendMessage(MessagesUtil
						.color("&aSpawner modified, &6ID: &a" + id + ", &6" + argument[0] + ": &a" + argument[1]));
			});
		} catch (Exception e) {
			incorrectUsage();
			return;
		}

		spawnerManager.reloadBooster(spawner);

	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
