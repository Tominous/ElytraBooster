package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public abstract class AbstractCommand {

	protected ElytraBooster plugin;
	protected CommandSender sender;
	protected String subCommand;
	protected String[] arguments;
	protected boolean isSenderPlayer;

	public AbstractCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		this.plugin = plugin;
		this.sender = sender;
		this.subCommand = subCommand;
		this.arguments = arguments;
		this.isSenderPlayer = isSenderPlayer;
	}

	protected boolean canSenderTypeExecute() {
		return !isOnlyPlayer() || isSenderPlayer;
	}
	
	protected boolean hasPermission() {
		 return sender.hasPermission("eb.admin." + subCommand.toLowerCase());
	}

	// TODO better messages
	public void execute() {
		String errorMessage = "";
		errorMessage = !hasPermission()?"You don't have the permission":errorMessage;
		errorMessage = !canSenderTypeExecute()?"This command can be used only by a player":errorMessage;
		if (canSenderTypeExecute() && hasPermission()) {
			commandExecution();
		} else {
			sender.sendMessage(errorMessage);
		}
	}

	protected abstract void commandExecution();

	protected abstract boolean isOnlyPlayer();

}
