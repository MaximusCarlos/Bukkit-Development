package me.maxipad.bounty.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

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
				sender.sendMessage(
						color(plugin.getConfig().getString("bountyError2")).replaceAll("%player%", t.getName()));
				return false;
			}

			if (plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("FILE")) {
				plugin.getConfig().set("Players." + uuid + ".Bounty", 0);
				plugin.saveConfig();
			}

			sender.sendMessage(color(plugin.getConfig().getString("bountyReset")).replaceAll("%player%", t.getName())
					.replaceAll("%a%", "'"));

			if (plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("SQL")) {
				Sql2o sql2o = new Sql2o(
						"jdbc:mysql://" + plugin.getConfig().getString("Host") + ":"
								+ plugin.getConfig().getString("Port") + "/" + plugin.getConfig().getString("Database"),
						plugin.getConfig().getString("User"), plugin.getConfig().getString("Password"));

				try (Connection connection = sql2o.open()) {
					connection.createQuery("DELETE FROM " + plugin.getConfig().getString("Table")
							+ " WHERE PlayerName = '" + t.getName() + "'").executeUpdate();

				}
			}

		}

		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
