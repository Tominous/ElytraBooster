package com.github.zamponimarco.elytrabooster.commands;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.manager.PortalManager;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.portals.factory.PortalFactory;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class ElytraBoosterSetCommand extends AbstractCommand {

	public ElytraBoosterSetCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	// TODO urgh
	@Override
	protected void commandExecution() {

		PortalManager portalManager = plugin.getPortalManager();
		String id = arguments[0];
		AbstractPortal portal = portalManager.getPortal(id);

		if (portal == null) {
			throw new IllegalArgumentException("Portal passed in input is invalid");
		}

		Arrays.asList(arguments[1].split(",")).forEach(string -> {
			String[] argument = string.split(":");
			setParam(id, argument[0], argument[1]);
		});
		portalManager.saveConfig();

		portal.stopPortalTask();
		portalManager.setPortal(id, PortalFactory.buildPortal(plugin, portalManager,
				portalManager.getDataYaml().getConfigurationSection(id)));

	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

	private void setParam(String id, String param, String value) {
		ConfigurationSection portal = plugin.getPortalManager().getDataYaml().getConfigurationSection(id);
		switch (param) {
		case "x":
		case "y":
		case "z":
		case "initialVelocity":
		case "finalVelocity":
			portal.set(param, Double.valueOf(value));
			break;
		case "boostDuration":
		case "cooldown":
			portal.set(param, Integer.valueOf(value));
			break;
		case "world":
		case "axis":
		case "outlineType":
		case "cooldownType":
		case "shape":
		case "measures":
		case "trail":
			portal.set(param, value);
			break;
		case "isBlockOutline":
			boolean isBlock = Boolean.valueOf(value);
			portal.set(param, isBlock);
			Runnable cons = isBlock ? () -> setParam(id, "outlineType", "STONE")
					: () -> setParam(id, "outlineType", "FLAME");
			cons.run();
			setParam(id, "cooldownType", null);
			break;
		default:
			sender.sendMessage(MessagesUtil.color("&cUnknown parameter"));
			return;
		}
		sender.sendMessage(MessagesUtil.color("&aPortal modified, &6ID: &a" + id + ", &6" + param + ": &a" + value));
	}

}
