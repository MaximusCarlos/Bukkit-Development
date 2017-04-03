package me.maxipad.main.commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.maxipad.main.Main;

public class ClearInventory implements CommandExecutor, Listener {

	private Main plugin;

	public ClearInventory(Main pl) {
		plugin = pl;
	}

	private HashMap<UUID, Long> tempPlayerTime = new HashMap<UUID, Long>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// Clear Inventory Command
		if (args.length == 0) {
			if (!(sender.hasPermission("clearinventory.use"))) {
				sender.sendMessage(color("&4You do not have access to that command."));
				return false;
			}
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to use this command.");
				return false;
			}

			Player player = (Player) sender;

			if (tempPlayerTime.containsKey(player.getUniqueId()) && tempPlayerTime.get(player.getUniqueId()) > System.currentTimeMillis()) {

				player.getInventory().clear();
				player.sendMessage(color(plugin.getConfig().getString("(CI) InventoryCleared")));
				tempPlayerTime.remove(player.getUniqueId());
			} else {
				int time = Integer.parseInt(plugin.getConfig().getString("(CI) ConfirmTime"));
				tempPlayerTime.put(player.getUniqueId(), System.currentTimeMillis() + (time * 1000));

				if (plugin.getConfig().getString("(CI) Bumper").equalsIgnoreCase("True")) {
					player.sendMessage(" ");
				}

				player.sendMessage(color(plugin.getConfig().getString("(CI) ConfirmMessage").replaceAll("%n%", "\n")).replaceAll("%time%",
						Integer.toString(time)));

				if (plugin.getConfig().getString("(CI) Bumper").equalsIgnoreCase("True")) {
					player.sendMessage(" ");
				}
			}
			return true;
		}

		// Clear Inventory Bumper Set Command
		if (args.length > 1 && args[0].equalsIgnoreCase("bumper")) {
			if (!(sender.hasPermission("clearinventory.admin"))) {
				return false;
			}

			String choice = "";
			if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {
				choice = args[1];
			} else {
				sender.sendMessage(color("&6Please specify: &bTrue &6/ &bFalse"));
				return false;
			}

			plugin.getConfig().set("(CI) Bumper", choice);
			sender.sendMessage(color("&6Bumper set to: &b" + choice));
			plugin.saveConfig();
		}

		// Clear Inventory Help Command
		if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
			if (!(sender.hasPermission("clearinventory.admin"))) {
				return false;
			}
			sender.sendMessage(color("&8&l&m-----|&8[&6Clear Inventory Prompt Help&8]&8&l&m-----"));
			sender.sendMessage(color("&b/ci &6to clear inventory."));
			sender.sendMessage(color("&b/ci bumper <true|false> &6to add/remove bumper"));
			sender.sendMessage(color("&b/ci prompt <message|default> &6%n% = new line | %time% display time."));
			sender.sendMessage(color("&b/ci ptime <time/seconds>"));
		}

		// Clear Inventory Prompt Set Command
		if (args.length > 1 && args[0].equalsIgnoreCase("prompt")) {
			if (!(sender.hasPermission("clearinventory.admin"))) {
				return false;
			}

			StringBuilder str = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				str.append(args[i] + " ");
			}
			String message = str.toString();

			if (!(message.equalsIgnoreCase("default "))) {
				plugin.getConfig().set("(CI) ConfirmMessage", message);
				plugin.saveConfig();
				sender.sendMessage(color("&bMessage set."));
			} else {
				plugin.getConfig().set("(CI) ConfirmMessage",
						"&6Are you sure you want to clear your inventory?%n%Type &b/ci &6again to confirm! &6Expires in &b%time%");
				sender.sendMessage(color("&6Prompt set to &bdefault."));
				plugin.saveConfig();
			}

		}

		if (args[0].equalsIgnoreCase("ptime") && args.length == 1) {
			if (!(sender.hasPermission("clearinventory.admin"))) {
				return false;
			}
			String time = args[1];
			plugin.getConfig().set("(CI) ConfirmTime", time);
			plugin.saveConfig();
			sender.sendMessage(color("&6Prompt time set to: &b" + time));
		}

		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
