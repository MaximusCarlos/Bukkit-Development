package me.maxipad.counter.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.maxipad.counter.KillCounter;

public class PlayerDeath implements Listener {

	private KillCounter plugin;

	public PlayerDeath(KillCounter pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (player.getKiller() instanceof Player) {
			Player killer = player.getKiller();
			String pUUID = player.getUniqueId().toString();
			String kUUID = killer.getUniqueId().toString();
			int kills = plugin.getConfig().getInt("Players." + kUUID + ".Kills");
			int deaths = plugin.getConfig().getInt("Players." + pUUID + ".Deaths");
			double points = plugin.getConfig().getDouble("Players." + kUUID + ".Points");

			plugin.getConfig().set("Players." + kUUID + ".Kills", kills + 1);
			plugin.getConfig().set("Players." + pUUID + ".Deaths", deaths + 1);
			plugin.getConfig().set("Players." + kUUID + ".Points", points + 7);
			plugin.saveConfig();

			if (plugin.getConfig().getString("BroadcastOnDeath").equalsIgnoreCase("Yes")) {
				Bukkit.broadcastMessage(color("&b" + killer.getName().toString() + " &7has killed &b" + player.getName().toString() + "&7!"));
			}
		}
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
