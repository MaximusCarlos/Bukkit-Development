package com.maxipad.antiswear.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.maxipad.antiswear.Main;

public class swearlist implements CommandExecutor {

	private Main plugin;

	public swearlist(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}
		Player player = (Player) sender;

		List<String> blockedWords = plugin.getConfig().getStringList("BlockedWords");
		List<String> discouragedWords = plugin.getConfig().getStringList("DiscouragedWords");
		List<String> kickableWords = plugin.getConfig().getStringList("KickableWords");

		player.sendMessage(ChatColor.AQUA + "Loading words...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The blocked words are: ");
		for (String blocked : blockedWords) {
			player.sendMessage(blocked);
		}
		player.sendMessage(ChatColor.GREEN + "---------------");
		player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "The discouraged words are: ");
		for (String discouraged : discouragedWords) {
			player.sendMessage(discouraged);
		}
		player.sendMessage(ChatColor.GOLD + "---------------");
		player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The kickable words are: ");
		for (String kickable : kickableWords) {
			player.sendMessage(kickable);
		}
		player.sendMessage(ChatColor.RED + "---------------");

		return false;
	}

}
