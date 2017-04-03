package me.maxipad.colorcodes.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.maxipad.colorcodes.Main;

public class List implements CommandExecutor {

	private Main plugin;

	public List(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command!");
			return false;
		}

		sender.sendMessage(color(plugin.getConfig().getString("Line1")));
		sender.sendMessage(color(plugin.getConfig().getString("Line2")));
		sender.sendMessage(color(plugin.getConfig().getString("Line3")));
		sender.sendMessage(color(plugin.getConfig().getString("Line4")));
		sender.sendMessage(color(plugin.getConfig().getString("Line5")));

		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
