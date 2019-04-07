package com.github.zamponimarco.elytrabooster.utils;

import org.bukkit.ChatColor;

public class MessagesUtil {

	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static String repeat(int count, String with) {
	    return new String(new char[count]).replace("\0", with);
	}
	
}
