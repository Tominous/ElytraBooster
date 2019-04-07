package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public abstract class AbstractCommand {

	protected ElytraBooster plugin;
	protected CommandSender sender;
	protected String subCommand;
	protected String[] arguments;
	protected boolean isSenderPlayer;
	
	public AbstractCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments, boolean isSenderPlayer) {
		this.plugin = plugin;
		this.sender = sender;
		this.subCommand = subCommand;
		this.arguments = arguments;
		this.isSenderPlayer = isSenderPlayer;
	}
	
	protected boolean canExecute() {
		return !isOnlyPlayer() || isSenderPlayer;
	}
	
	// TODO better messages
	public void execute() {
		if (canExecute()) {
			commandExecution();
		} else {
			sender.sendMessage("This command can be used only by a player");
		}
	}

	protected abstract void commandExecution();
	
	protected abstract boolean isOnlyPlayer();
	
}
