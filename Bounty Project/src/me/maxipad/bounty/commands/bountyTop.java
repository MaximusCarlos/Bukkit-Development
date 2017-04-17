package me.maxipad.bounty.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import me.maxipad.bounty.Bounty;

public class bountyTop implements CommandExecutor {

	private int i = 0;
	private Bounty plugin;

	public bountyTop(Bounty pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		i = 0;

		Sql2o sql2o = new Sql2o(
				"jdbc:mysql://" + plugin.getConfig().getString("Host") + ":" + plugin.getConfig().getString("Port")
						+ "/" + plugin.getConfig().getString("Database"),
				plugin.getConfig().getString("User"), plugin.getConfig().getString("Password"));

		sender.sendMessage(color(plugin.getConfig().getString("Top Bounty Header")
				.replaceAll("%amount%", plugin.getConfig().getString("BountyTopAmount"))));

		try (Connection connection = sql2o.open()) {
			connection.createQuery("SELECT * FROM " + plugin.getConfig().getString("Table") + " ORDER BY CurrentBounty DESC LIMIT " + plugin.getConfig().getString("BountyTopAmount"))
					.executeAndFetch(SQLReport.class).forEach(report -> {
						i++;
						sender.sendMessage(color(plugin.getConfig().getString("Top Bounties")).replaceAll("%ID%", Integer.toString(i))
								.replaceAll("%player%", report.getPlayerName())
								.replaceAll("%bounty%", Integer.toString(report.getCurrentBounty())));
					});
		}
		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}