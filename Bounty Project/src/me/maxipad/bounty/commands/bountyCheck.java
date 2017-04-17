package me.maxipad.bounty.commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import me.maxipad.bounty.Bounty;

public class bountyCheck implements CommandExecutor {

	private Bounty plugin;

	public bountyCheck(Bounty pl) {
		plugin = pl;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command! :(");
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			sender.sendMessage(color("&8&l&m<----------&8[&6BountyHelp&8]&8&l&m---------->"));
			sender.sendMessage(color("&6/bounty <name> &8- Displays players bounty!"));
			sender.sendMessage(color("&6/bountyadd <name> <value> &8- Add a bounty!"));
			sender.sendMessage(color("&6/bountyreload &8- Reloads Plugin!"));
			if (plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("SQL")) {
				sender.sendMessage(color("&6/bountytop &8- Displays the players with highest bounties!"));
			}

		}
		if (args.length == 1) {
			OfflinePlayer t = player.getServer().getOfflinePlayer(args[0]);
			String uuid = t.getUniqueId().toString();

			if (plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("FILE")) {
				int bounty = plugin.getConfig().getInt("Players." + uuid + ".Bounty");
				String stringBounty = Integer.toString(bounty);

				if (!(plugin.getConfig().contains("Players." + uuid))) {
					sender.sendMessage(color(plugin.getConfig().getString("Invalid Player")).replaceAll("%player%",
							t.getName().replaceAll("%bounty%", stringBounty)));

					return false;
				}

				player.sendMessage(
						color(plugin.getConfig().getString("Bounty Info")).replaceAll("%player%", t.getName())
								.replaceAll("%bounty%", stringBounty).replaceAll("%a%", "'"));
			}

			if (plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("SQL")) {
				Sql2o sql2o = new Sql2o(
						"jdbc:mysql://" + plugin.getConfig().getString("Host") + ":"
								+ plugin.getConfig().getString("Port") + "/" + plugin.getConfig().getString("Database"),
						plugin.getConfig().getString("User"), plugin.getConfig().getString("Password"));

				try (Connection connection = sql2o.open()) {
					connection
							.createQuery("SELECT * FROM " + plugin.getConfig().getString("Table")
									+ " WHERE PlayerUUID = '" + t.getUniqueId().toString() + "'")
							.executeAndFetch(SQLReport.class).forEach(report -> {
								sender.sendMessage(color(
										plugin.getConfig().getString("Bounty Info").replaceAll("%player%", t.getName())
												.replaceAll("%bounty%", Integer.toString(report.getCurrentBounty()))
												.replaceAll("%a%", "'")));
							});

				}
			}

			return false;
		}

		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
