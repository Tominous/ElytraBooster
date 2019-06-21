package com.github.zamponimarco.elytrabooster.commands.spawner;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.SpawnersListInventoryHolder;
import com.github.zamponimarco.elytrabooster.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;
import com.google.common.collect.Lists;

public class SpawnerListCommand extends SpawnerCommand {

	public SpawnerListCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		Player player = (Player) sender;
		List<AbstractSpawner> spawners = Lists.newArrayList(plugin.getSpawnerManager().getSpawnersMap().values());
		spawners.sort((p1, p2) -> (int) (p1.getCenter().distance(player.getLocation())
				- p2.getCenter().distance(player.getLocation())));

		player.openInventory(new SpawnersListInventoryHolder(plugin, MessagesUtil.color("&1&lPortals list"), spawners, 1).getInventory());

	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
