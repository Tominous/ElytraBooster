package com.github.zamponimarco.elytrabooster.utils;

import org.bukkit.ChatColor;

public class MessagesUtil {

	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static String repeat(int count, String with) {
	    return new String(new char[count]).replace("\0", with);
	}
	
	public static String header(String string) {
		return MessagesUtil.color(String.format("&e=--- &c%s &e---=\n", string));
	}
	
	public static String delimiter() {
		return MessagesUtil.color("&e-----------------------------------------------------");
	}
	
	public static String footer(int page, int numberOfPages) {
		return MessagesUtil.color(String.format("&e=--- &cPage &6%d&c/&6%d &e---=", page, numberOfPages));
	}
	
}
