package com.github.zamponimarco.elytrabooster.commands.spawner;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.SpawnerManager;
import com.github.zamponimarco.elytrabooster.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.spawners.factory.SpawnerFactory;

public class SpawnerMoveCommand extends SpawnerCommand {

	public SpawnerMoveCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {

		Player player = (Player) sender;

		SpawnerManager spawnerManager = plugin.getSpawnerManager();
		if (arguments.length < 1) {
			incorrectUsage();
			return;
		}
		String id = arguments[0];
		AbstractSpawner spawner = spawnerManager.getSpawner(id);
		if (spawner == null) {
			invalidSpawner();
			return;
		}

		ConfigurationSection section = (ConfigurationSection) spawnerManager.getDataYaml().get(id);
		World world = player.getLocation().getWorld();
		Double x = arguments.length == 4 ? Double.valueOf(arguments[1]) : player.getLocation().getBlockX();
		Double y = arguments.length == 4 ? Double.valueOf(arguments[2]) : player.getLocation().getBlockY();
		Double z = arguments.length == 4 ? Double.valueOf(arguments[3]) : player.getLocation().getBlockZ();
		section.set("world", world.getName());
		section.set("x", x);
		section.set("y", y);
		section.set("z", z);
		
		spawner.stopSpawnerTask();
		spawnerManager.saveConfig();
		spawnerManager.setSpawner(id, SpawnerFactory.buildSpawner(plugin, spawnerManager,
				spawnerManager.getDataYaml().getConfigurationSection(id)));

	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
