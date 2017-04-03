package me.maxipad.main.events;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.maxipad.main.Main;

public class PlayerDeath implements Listener {

	private Main plugin;

	public PlayerDeath(Main pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {

		File counter_data = new File(plugin.getDataFolder() + "/Counter_data.yml");
		FileConfiguration Counter_data = YamlConfiguration.loadConfiguration(counter_data);

		Player player = event.getEntity();
		event.getDrops().clear();
		if (player.getKiller() instanceof Player) {
			Player killer = player.getKiller();
			String pUUID = player.getUniqueId().toString();
			String kUUID = killer.getUniqueId().toString();
			int kills = Counter_data.getInt("Players." + kUUID + ".Kills");
			int deaths = Counter_data.getInt("Players." + pUUID + ".Deaths");

			Counter_data.set("Players." + kUUID + ".Kills", kills + 1);
			Counter_data.set("Players." + pUUID + ".Deaths", deaths + 1);

			try {
				Counter_data.save(counter_data);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (kills == 10) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Noob user remove " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Killer user add " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "bc &7Congrats &b" + killer + " &7for reaching the &aKiller &7rank!");
			}
			if (kills == 25) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Killer user remove " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Butcher user add " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "bc &7Congrats &b" + killer + " &7for reaching the &6Butcher &7rank!");
			}
			if (kills == 50) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Butcher user remove " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Murderer user add " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "bc &7Congrats &b" + killer + " &7for reaching the &2Murderer &7rank!");
			}
			if (kills == 100) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Murderer user remove " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Assassin user add " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "bc &7Congrats &b" + killer + " &7for reaching the &2Assassin &7rank!");
			}
			if (kills == 250) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Assassin user remove " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Warlord user add " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "bc &7Congrats &b" + killer + " &7for reaching the &bWarlord &7rank!");
			}
			if (kills == 500) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Warlord user remove " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Jesus user add " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "bc &7Congrats &b" + killer + " &7for reaching the &dJesus &7rank!");

			}
			if (kills == 1000) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Jesus user remove " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group God user add " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "bc &7Congrats &b" + player.getName() + " &7for reaching the &5God &7rank!");
			}
			if (kills == 5000) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group God user remove " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Unkillable user add " + kUUID);
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "bc &7Congrats &b&l" + player.getName() + " &7for reaching the &0&l&kAA&f&lUnkillable&0&l&kAA&r &7rank!");

			}
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex reload");
		}		
	}

	public void addPrefix(int kills, String uuid) {

		System.out.println(uuid + " " + kills);

	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
