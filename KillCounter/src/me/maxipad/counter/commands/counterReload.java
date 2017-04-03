package me.maxipad.counter.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.maxipad.counter.KillCounter;

public class counterReload implements CommandExecutor {

	private KillCounter plugin;

	public counterReload(KillCounter pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender.hasPermission("killcounter.reload"))) {
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