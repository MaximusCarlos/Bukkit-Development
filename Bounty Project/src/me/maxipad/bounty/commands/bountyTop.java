package me.maxipad.bounty.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.maxipad.bounty.Bounty;

public class bountyTop implements CommandExecutor {

	public int count = 0;

	private Bounty plugin;

	public bountyTop(Bounty pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		String[] names = new String[Bukkit.getOfflinePlayers().length];
		int[] bounty = new int[Bukkit.getOfflinePlayers().length];
		OfflinePlayer[] OfflinePlayers = Bukkit.getServer().getOfflinePlayers();

		for (int i = 0; i < OfflinePlayers.length; i++) {
			names[i] = OfflinePlayers[i].getName();
			bounty[i] = plugin.getConfig().getInt("Players." + OfflinePlayers[i].getUniqueId() + ".Bounty");
			count++;
		}

		Arrays.sort(bounty);
		sender.sendMessage(color("&8&l&m<---[&6Top Server Bounties&8&l&m]--->"));

		for (int i = 0; i < bounty.length; i++) {
			if (bounty[bounty.length - (i + 1)] == 0) {
				// do nothing
			} else {
				sender.sendMessage(color("&c" + (i + 1) + ". &b" + names[bounty.length - (i + 1)] + "&c, &b"
						+ Integer.toString(bounty[bounty.length - (i + 1)]) + "$"));
			}
		}

		return false;

	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
