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

import me.maxipad.bounty.Bounty;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class bountyAdd implements CommandExecutor {

	private Bounty plugin;

	public bountyAdd(Bounty pl) {
		plugin = pl;
	}

	public Economy econ = null;

	private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>(); // stores
																		// cool
																		// down
																		// times
																		// for
																		// individual
																		// players
	// without a hash map only one player could properly use a cool down despite
	// the scheduled task.

	@SuppressWarnings("deprecation")
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
			String stringBounty = args[1];

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

			int bounty = plugin.getConfig().getInt("Players." + uuid + ".Bounty");
			int addbounty = Integer.parseInt(stringBounty);

			EconomyResponse r = econ.withdrawPlayer(sender.getName(), addbounty);
			if (r.transactionSuccess()) {
				plugin.getConfig().set("Players." + uuid + ".Bounty", bounty + addbounty);
				plugin.saveConfig();
				sender.sendMessage(color(plugin.getConfig().getString("BountyAddSuccess"))
						.replaceAll("%player%", t.getName()).replaceAll("%bounty%", stringBounty));

				if (plugin.getConfig().getString("Broadcast Add Bounty").equalsIgnoreCase("Yes")) {
					Bukkit.broadcastMessage(color(plugin.getConfig().getString("Broadcast Add Bounty Message"))
							.replaceAll("%player%", player.getName()).replaceAll("%bounty%", stringBounty)
							.replaceAll("%target%", t.getName()));
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
