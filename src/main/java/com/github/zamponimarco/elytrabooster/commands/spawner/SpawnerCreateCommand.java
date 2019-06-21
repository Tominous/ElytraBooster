package com.github.zamponimarco.elytrabooster.commands.spawner;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.SpawnerManager;
import com.github.zamponimarco.elytrabooster.spawners.factory.SpawnerFactory;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class SpawnerCreateCommand extends SpawnerCommand {

	public SpawnerCreateCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		Player player = (Player) sender;
		SpawnerManager spawnerManager = plugin.getSpawnerManager();
		if(arguments.length < 1) {
			incorrectUsage();
			return;
		}
		String newSpawnerId = arguments[0];

		if (!spawnerManager.getSpawnersMap().containsKey(newSpawnerId)) {
			spawnerManager.setBooster(newSpawnerId, SpawnerFactory.buildSpawner(plugin, spawnerManager,
					spawnerManager.createDefaultBoosterConfiguration(player, newSpawnerId)));
			player.sendMessage(MessagesUtil.color("&aPortal created, &6ID: &a" + newSpawnerId));
		} else {
			invalidSpawner();
		}
	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

}
