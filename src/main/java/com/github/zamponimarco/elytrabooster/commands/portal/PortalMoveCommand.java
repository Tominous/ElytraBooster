package com.github.zamponimarco.elytrabooster.commands.portal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.github.zamponimarco.elytrabooster.boosters.factory.PortalFactory;
import com.github.zamponimarco.elytrabooster.boosters.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.boosters.PortalManager;

public class PortalMoveCommand extends PortalCommand {

	public PortalMoveCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {

		Player player = (Player) sender;

		PortalManager portalManager = plugin.getPortalManager();
		if (arguments.length < 1) {
			incorrectUsage();
			return;
		}
		String id = arguments[0];
		AbstractPortal portal = portalManager.getBooster(id);
		if (portal == null) {
			invalidPortal();
			return;
		}

		ConfigurationSection section = (ConfigurationSection) portalManager.getDataYaml().get(id);
		World world = player.getLocation().getWorld();
		Double x = arguments.length == 4 ? Double.valueOf(arguments[1]) : player.getLocation().getBlockX();
		Double y = arguments.length == 4 ? Double.valueOf(arguments[2]) : player.getLocation().getBlockY();
		Double z = arguments.length == 4 ? Double.valueOf(arguments[3]) : player.getLocation().getBlockZ();
		section.set("world", world.getName());
		section.set("x", x);
		section.set("y", y);
		section.set("z", z);

		Location oldLocation = portal.getCenter();
		Location newLocation = new Location(world, x, y, z);
		Vector movement = newLocation.clone().subtract(oldLocation.clone()).toVector();

		List<String> portalsUnion = new ArrayList<String>();

		portal.getPortalsUnion().forEach(unionPortal -> {
			unionPortal.setCenter(unionPortal.getCenter().clone().add(movement));
			portalsUnion.add(unionPortal.toString());
		});

		if (!portalsUnion.isEmpty()) {
			section.set("portalsUnion", portalsUnion);
		}

		portal.stopBoosterTask();
		portalManager.saveConfig();
		portalManager.setBooster(id, PortalFactory.buildBooster(plugin,
				portalManager.getDataYaml().getConfigurationSection(id)));
	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

}
