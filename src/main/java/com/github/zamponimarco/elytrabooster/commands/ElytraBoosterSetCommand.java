package com.github.zamponimarco.elytrabooster.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.manager.PortalManager;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.portals.builder.PortalBuilder;

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
		portal.stopPortalTask();

		List<String> toModify = Arrays.asList(arguments[1].split(","));
		toModify.forEach(string -> {
			String[] argument = string.split(":");
			portalManager.getDataYaml().set(id + "." + argument[0], argument[1]);
		});
		portalManager.saveConfig();
		portalManager.setPortal(id,
				PortalBuilder.buildPortal(plugin, portalManager.getDataYaml().getConfigurationSection(id)));

	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
