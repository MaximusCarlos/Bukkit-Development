package me.maxipad.bounty.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.maxipad.bounty.Bounty;

public class bountyReload implements CommandExecutor {

	private Bounty plugin;

	public bountyReload(Bounty pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender.hasPermission("bounty.reload"))) {
			plugin.reloadConfig();
			sender.sendMessage(color(plugin.getConfig().getString("Reload Message")));

		} else {
			sender.sendMessage(color(plugin.getConfig().getString("Insufficient Permission")));
		}
		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
