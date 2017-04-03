package me.maxipad.colorcodes.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.maxipad.colorcodes.Main;

public class Reload implements CommandExecutor {

	private Main plugin;

	public Reload(Main pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to execute this command!");
			return false;
		}

		if (!(sender.hasPermission("color.reload"))) {
			sender.sendMessage(ChatColor.RED + "Insufficient Permissions");
			return false;
		}
		plugin.reloadConfig();
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Reload Message")));
		return false;

	}

}
