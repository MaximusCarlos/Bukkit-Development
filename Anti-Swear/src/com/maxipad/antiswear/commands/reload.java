package com.maxipad.antiswear.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.maxipad.antiswear.Main;

public class reload implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;

	public reload(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}
		Player player = (Player) sender;

		if (!(player.hasPermission("swear.reload"))) {
			player.sendMessage(ChatColor.RED + "Insufficient Permissions.");
			return false;
		}
		Main plugin = (Main) Bukkit.getServer().getPluginManager().getPlugin("MaxAntiSwear");

		plugin.reloadConfig();
		String reloadmsg = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Reload Message"));
		player.sendMessage(reloadmsg);

		return false;
	}

}
