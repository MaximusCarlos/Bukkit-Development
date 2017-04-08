package me.maxipad.bounty.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.maxipad.bounty.Bounty;

public class bountyReset implements CommandExecutor {

	private Bounty plugin;

	public bountyReset(Bounty pl) {
		plugin = pl;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("bounty.reset")) {
			sender.sendMessage(color("Insufficient Permission"));
			return false;
		}
		if (args.length == 0) {
			sender.sendMessage(color(plugin.getConfig().getString("bountyError1")));
			return false;
		}

		if (args.length == 1) {
			OfflinePlayer t = Bukkit.getServer().getOfflinePlayer(args[0]);
			String uuid = t.getUniqueId().toString();

			if (!(plugin.getConfig().contains("Players." + uuid))) {
				sender.sendMessage(color(plugin.getConfig().getString("bountyError2")).replaceAll("%player%", t.getName()));
				return false;
			}

			plugin.getConfig().set("Players." + uuid + ".Bounty", 0);
			sender.sendMessage(color(plugin.getConfig().getString("bountyReset")).replaceAll("%player%", t.getName()).replaceAll("%a%", "'"));

		}

		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
