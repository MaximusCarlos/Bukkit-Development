package me.maxipad.bounty.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import me.maxipad.bounty.Bounty;
import me.maxipad.bounty.backend.SQLReport;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class PlayerDeath implements Listener {

	public Economy econ = null;

	private Bounty plugin;

	public PlayerDeath(Bounty pl) {
		plugin = pl;
	}

	private int id = 0;

	private String DBBounty = "";

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		setupEconomy();
		Player player = e.getEntity();
		if (player.getKiller() instanceof Player) {
			Player killer = player.getKiller();
			String pUUID = player.getUniqueId().toString();

			if (plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("FILE")) {
				int bounty = plugin.getConfig().getInt("Players." + pUUID + ".Bounty");
				String stringBounty = Integer.toString(bounty);
				EconomyResponse r = econ.depositPlayer(killer.getName(), bounty);

				if (bounty <= 0) {
					return;
				}

				if (r.transactionSuccess()) {
					plugin.getConfig().set("Players." + pUUID + ".Bounty", 0);
					plugin.saveConfig();

					player.sendMessage(color(plugin.getConfig().getString("Victim Message")
							.replaceAll("%killer%", killer.getName()).replaceAll("%bounty%", stringBounty))
									.replaceAll("%player%", player.getName()));
					killer.sendMessage(color(plugin.getConfig().getString("Killer Message")
							.replaceAll("%player%", player.getName()).replaceAll("%bounty%", stringBounty))
									.replaceAll("%player%", player.getName()).replaceAll("%a%", "'"));

					if (plugin.getConfig().getString("Broadcast Death").equalsIgnoreCase("Yes")) {
						Bukkit.broadcastMessage(color(plugin.getConfig().getString("Broadcast Death Message"))
								.replaceAll("%player%", player.getName()).replaceAll("%bounty%", stringBounty)
								.replaceAll("%killer%", killer.getName()));
					}
				}
			}

			if (plugin.getConfig().getString("Use SQL or FILE").equalsIgnoreCase("SQL")) {

				id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

					int i = 1;

					public void run() {
						Sql2o sql2o = new Sql2o("jdbc:mysql://" + plugin.getConfig().getString("Host") + ":"
								+ plugin.getConfig().getString("Port") + "/" + plugin.getConfig().getString("Database"),
								plugin.getConfig().getString("User"), plugin.getConfig().getString("Password"));

						try (Connection connection = sql2o.open()) {
							connection
									.createQuery("SELECT * FROM " + plugin.getConfig().getString("Table")
											+ " WHERE PlayerName = '" + player.getName() + "'")
									.executeAndFetch(SQLReport.class).forEach(report -> {
										DBBounty = Integer.toString(report.getCurrentBounty());
									});
						}
						
						EconomyResponse r2 = econ.depositPlayer(killer.getName(), Integer.parseInt(DBBounty));

						if (r2.transactionSuccess()) {
							player.sendMessage(color(plugin.getConfig().getString("Victim Message")
									.replaceAll("%killer%", killer.getName()).replaceAll("%bounty%", DBBounty))
											.replaceAll("%player%", player.getName()));
							killer.sendMessage(color(plugin.getConfig().getString("Killer Message")
									.replaceAll("%player%", player.getName()).replaceAll("%bounty%", DBBounty))
											.replaceAll("%player%", player.getName()));

							if (plugin.getConfig().getString("Broadcast Death").equalsIgnoreCase("Yes")) {
								Bukkit.broadcastMessage(color(plugin.getConfig().getString("Broadcast Death Message"))
										.replaceAll("%player%", player.getName()).replaceAll("%bounty%", DBBounty)
										.replaceAll("%killer%", killer.getName()));
							}
						}
						
						System.out.println(i);
						
						if (i == 1) {
							Bukkit.getScheduler().cancelTask(id);
						}
					}
					
					
				}, 40, 0);

				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

					int i = 1;
					public void run() {
								Sql2o sql2o = new Sql2o("jdbc:mysql://" + plugin.getConfig().getString("Host") + ":"
								+ plugin.getConfig().getString("Port") + "/" + plugin.getConfig().getString("Database"),
								plugin.getConfig().getString("User"), plugin.getConfig().getString("Password"));

						try (Connection connection = sql2o.open()) {
							connection.createQuery("DELETE FROM " + plugin.getConfig().getString("Table")
									+ " WHERE PlayerUUID = '" + pUUID + "'").executeUpdate();

						}

						if (i == 1) {
							Bukkit.getScheduler().cancelAllTasks();
						}
					}
				}, 100, 0);
			}

		}
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

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
