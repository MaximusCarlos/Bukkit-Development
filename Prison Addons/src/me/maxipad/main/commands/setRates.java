package me.maxipad.main.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.maxipad.main.Main;

public class setRates implements CommandExecutor {

	private Main plugin;

	public setRates(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender.hasPermission("prisonaddons.admin"))) {
			return false;
		}

		if (args.length == 0) {
			return false;
		}

		if (args.length > 1) {

			if (args[0].equalsIgnoreCase("GUARDet")) {
				plugin.getConfig().set("GUARDet", args[1]);
				sender.sendMessage("Guardians E-Tokens drop set to " + (Integer.parseInt(args[1]) / 10) + "%");
			}

			if (args[0].equalsIgnoreCase("GUARDobby")) {
				plugin.getConfig().set("GUARDobby", args[1]);
				sender.sendMessage("Guardians Obby drop set to " + (Integer.parseInt(args[1]) / 10) + "%");
			}

			if (args[0].equalsIgnoreCase("IGet")) {
				plugin.getConfig().set("IGet", args[1]);
				sender.sendMessage("IG E-Tokens drop set to " + (Integer.parseInt(args[1]) / 10) + "%");
			}

			if (args[0].equalsIgnoreCase("IGobby")) {
				plugin.getConfig().set("IGobby", args[1]);
				sender.sendMessage("IG Obby drop set to " + (Integer.parseInt(args[1]) / 10) + "%");
			}
			plugin.saveConfig();
		}

		return false;
	}

}
