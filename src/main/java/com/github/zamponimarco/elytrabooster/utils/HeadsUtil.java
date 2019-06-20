package com.github.zamponimarco.elytrabooster.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class HeadsUtil {

	public static ItemStack skullFromValue(String value) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		UUID id = new UUID(value.hashCode(), value.hashCode());
		return Bukkit.getUnsafe().modifyItemStack(item,
				"{SkullOwner:{Id:\"" + id + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}");
	}
	
}
