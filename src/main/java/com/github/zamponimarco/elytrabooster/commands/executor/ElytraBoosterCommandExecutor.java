package com.github.zamponimarco.elytrabooster.commands.executor;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.commands.factory.CommandFactory;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class ElytraBoosterCommandExecutor implements CommandExecutor{
	
	private ElytraBooster plugin;
	
	public ElytraBoosterCommandExecutor(ElytraBooster plugin) {
		this.plugin = plugin;
	}

	// TODO improve code and readability
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("eb")) {
			
			boolean isSenderPlayer = sender instanceof Player;
			String subCommand = args.length >= 1 ? args[0] : "";
			String[] arguments = args.length >= 2 ? Arrays.copyOfRange(args, 1, args.length): null;
			
			CommandFactory.buildCommand(plugin, sender, subCommand, arguments, isSenderPlayer).checkExecution();
		}
		return false;
	}

}
