package xyz.maximuscarlos.thanatosreports.backend;

import org.bukkit.ChatColor;

public class StripColor {
		
	public String getStripColor(String itemName) {
		return ChatColor.stripColor(itemName);
	}

	
}
