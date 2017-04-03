package me.maxipad.bounty.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.maxipad.bounty.Bounty;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class PlayerDeath implements Listener {

	public Economy econ = null;

	private Bounty plugin;

	public PlayerDeath(Bounty pl) {
		plugin = pl;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		setupEconomy();
		Player player = e.getEntity();
		if (player.getKiller() instanceof Player) {
			Player killer = player.getKiller();
			String pUUID = player.getUniqueId().toString();
			int bounty = plugin.getConfig().getInt("Players." + pUUID + ".Bounty");
			String stringBounty = Integer.toString(bounty);
			System.out.println("Bounty = " + bounty);
			EconomyResponse r = econ.depositPlayer(killer.getName(), bounty);

			if (bounty <= 0) {
				return;
			}

			if (r.transactionSuccess()) {
				plugin.getConfig().set("Players." + pUUID + ".Bounty", 0);
				plugin.saveConfig();

				player.sendMessage(color(plugin.getConfig().getString("Victim Message").replaceAll("%killer%", killer.getName())
						.replaceAll("%bounty%", stringBounty)).replaceAll("%player%", player.getName()));
				killer.sendMessage(color(plugin.getConfig().getString("Killer Message").replaceAll("%player%", player.getName())
						.replaceAll("%bounty%", stringBounty)).replaceAll("%player%", player.getName()));

				if (plugin.getConfig().getString("Broadcast Death").equalsIgnoreCase("Yes")) {
					Bukkit.broadcastMessage(color(plugin.getConfig().getString("Broadcast Death Message")).replaceAll("%player%", player.getName())
							.replaceAll("%bounty%", stringBounty).replaceAll("%killer%", killer.getName()));
				}
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
