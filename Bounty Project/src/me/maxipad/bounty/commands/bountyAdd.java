package me.maxipad.bounty.commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import me.maxipad.bounty.Bounty;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class bountyAdd implements CommandExecutor {

	private Bounty plugin;

	public bountyAdd(Bounty pl) {
		plugin = pl;
	}

	public Economy econ = null;
	private int DBBounty = 0;
	private int id = 0;
	private int bounty = 0;

	private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>(); // stores
																		// cool
																		// down
																		// times
																		// for
																		// individual
																		// players
	// without a hash map only one player could properly use a cool down despite
	// the scheduled task.

	@SuppressWarnings({ "deprecation", "unused" })
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage(color(plugin.getConfig().getString("BountyAddCorrectUsage2")));
			return false;
		}

		if (args.length == 1) {
			sender.sendMessage(color(plugin.getConfig().getString("BountyAddCorrectUsage2")));
			return false;
		}

		if (args.length == 2) {

			Player player = (Player) sender;

			if (cooldown.containsKey(player.getUniqueId())
					&& cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
				long remainingTime = cooldown.get(player.getUniqueId()) - System.currentTimeMillis();
				player.sendMessage(color(plugin.getConfig().getString("CooldownMessage")).replaceAll("%cooldown%",
						Long.toString(remainingTime / 1000)));
				return true;
			}

			setupEconomy();
			OfflinePlayer t = player.getServer().getOfflinePlayer(args[0]);
			String uuid = t.getUniqueId().toString();
			String stringBounty = "";
			if(plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("FILE")){
				stringBounty = args[1];
			} else {
				stringBounty = "0";
			}
			

			if (sender == t) {
				sender.sendMessage(color(plugin.getConfig().getString("CantAddToYou")));
				return false;
			}

			if (!(plugin.getConfig().contains("Players." + uuid))) {
				sender.sendMessage(
						color(plugin.getConfig().getString("BountAddNotExist")).replaceAll("%player%", player.getName())
								.replaceAll("%bounty%", stringBounty).replaceAll("%target%", t.getName()));
				return false;
			} else if (!t.isOnline()) {
				sender.sendMessage(color(plugin.getConfig().getString("Player not on"))
						.replaceAll("%player%", t.getName()).replaceAll("%bounty%", stringBounty));
				return false;
			}
			
			if (plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("FILE")) {
				int bounty = plugin.getConfig().getInt("Players." + uuid + ".Bounty");
				int addbounty = Integer.parseInt(stringBounty);
				
			}
			
			if(plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("SQL")){
				Sql2o sql2o = new Sql2o("jdbc:mysql://" + plugin.getConfig().getString("Host") + ":"
						+ plugin.getConfig().getString("Port") + "/" + plugin.getConfig().getString("Database"),
						plugin.getConfig().getString("User"), plugin.getConfig().getString("Password"));

				try (Connection connection = sql2o.open()) {
					connection
							.createQuery("SELECT * FROM " + plugin.getConfig().getString("Table")
									+ " WHERE PlayerName = '" + args[0] + "'")
							.executeAndFetch(SQLReport.class).forEach(report -> {
								DBBounty = report.getCurrentBounty();
							});
				}
				
				
			}
			
			EconomyResponse r = econ.withdrawPlayer(sender.getName(), Integer.parseInt(stringBounty));
			EconomyResponse r2 = econ.withdrawPlayer(sender.getName(), Integer.parseInt(args[1]));
			
			if (r.transactionSuccess() || r2.transactionSuccess()) {

				if (plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("FILE")) {
					plugin.getConfig().set("Players." + uuid + ".Bounty", Integer.parseInt(args[1]) + bounty);
					plugin.saveConfig();

				}
				sender.sendMessage(color(plugin.getConfig().getString("BountyAddSuccess"))
						.replaceAll("%player%", t.getName()).replaceAll("%bounty%", args[1]));

				if (plugin.getConfig().getString("Broadcast Add Bounty").equalsIgnoreCase("Yes")) {
					Bukkit.broadcastMessage(color(plugin.getConfig().getString("Broadcast Add Bounty Message"))
							.replaceAll("%player%", player.getName()).replaceAll("%bounty%", args[1])
							.replaceAll("%target%", t.getName()));

					if (plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("SQL")) {

						Sql2o sql2o = new Sql2o("jdbc:mysql://" + plugin.getConfig().getString("Host") + ":"
								+ plugin.getConfig().getString("Port") + "/" + plugin.getConfig().getString("Database"),
								plugin.getConfig().getString("User"), plugin.getConfig().getString("Password"));

						try (Connection connection = sql2o.open()) {

							String[] query = new String[3];

							query[0] = "insert into " + plugin.getConfig().getString("Table")
									+ "(PlayerName, CurrentBounty, PlayerUUID) "
									+ "values (:PlayerName, :CurrentBounty, :PlayerUUID)";
							query[1] = "DELETE FROM " + plugin.getConfig().getString("Table") + " WHERE PlayerUUID = '"
									+ uuid + "'";
							query[2] = "insert into " + plugin.getConfig().getString("Table")
									+ "(PlayerName, CurrentBounty, PlayerUUID) "
									+ "values (:PlayerName, :CurrentBounty, :PlayerUUID)";

							id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

								int i = 0;

								public void run() {
									if (i == 0 || i == 2) {
										connection.createQuery(query[i]).addParameter("PlayerName", t.getName())
												.addParameter("CurrentBounty", Integer.parseInt(args[1]) + DBBounty)
												.addParameter("PlayerUUID", uuid).executeUpdate();
									}

									if (i == 1) {
										connection.createQuery(query[i]).executeUpdate();
									}

									if (i == 3) {
										Bukkit.getScheduler().cancelTask(id);
									}

									i++;
								}
							}, 0, 5);
						}

					}

				}

				cooldown.put(player.getUniqueId(),
						System.currentTimeMillis() + (plugin.getConfig().getInt("Cooldown Length") * 1000)); // set
																												// by
																												// system
																												// time

				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						cooldown.remove(player.getUniqueId()); // removes player
																// from hash map
					}
				}, plugin.getConfig().getInt("Cooldown Length") * 20); // set by
																		// ticks
				return true;
			} else {
				sender.sendMessage(color(plugin.getConfig().getString("Insufficient Funds")));
			}

		}

		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	private boolean setupEconomy() {
		if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

}
