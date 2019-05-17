package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.manager.PortalManager;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.portals.factory.PortalFactory;

public class ElytraBoosterMoveCommand extends AbstractCommand {

	public ElytraBoosterMoveCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	// TODO move unionportals
	@Override
	protected void commandExecution() {

		Player player = (Player) sender;

		PortalManager portalManager = plugin.getPortalManager();
		String id = arguments[0];
		AbstractPortal portal = portalManager.getPortal(id);
		portal.stopPortalTask();
		
		ConfigurationSection section = (ConfigurationSection) portalManager.getDataYaml().get(id);
		String world = arguments.length == 4 ? player.getLocation().getWorld().getName() : section.getString("world");
		Double x = arguments.length == 4 ? Double.valueOf(arguments[1]) : player.getLocation().getBlockX();
		Double y = arguments.length == 4 ? Double.valueOf(arguments[2]) : player.getLocation().getBlockY();
		Double z = arguments.length == 4 ? Double.valueOf(arguments[3]) : player.getLocation().getBlockZ();
		section.set("world", world);
		section.set("x", x);
		section.set("y", y);
		section.set("z", z);

		portalManager.saveConfig();
		portalManager.setPortal(id,
				PortalFactory.buildPortal(plugin, portalManager, portalManager.getDataYaml().getConfigurationSection(id)));

	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

}
