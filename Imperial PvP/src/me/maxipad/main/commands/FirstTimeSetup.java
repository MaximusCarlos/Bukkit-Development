package me.maxipad.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FirstTimeSetup implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 0) {
			return false;
		}
		if (args[0].equalsIgnoreCase("setup")) {
			if (!(sender.hasPermission("imperialpvp.admin"))) {
				return false;
			}

			String Noob = "\"&c&lNoob&r \""; // 0
			String Killer = "\"&a&lKiller&r \""; // 10
			String Butcher = "\"&6&lButcher&r \"";// 25
			String Murderer = "\"&2&lMurderer&r \""; // 50
			String Assassin = "\"&3&lAssassin&r \""; // 100
			String Warlord = "\"&b&l&oWarlord&r \""; // 250
			String Jesus = "\"&5&l&kA&d&lJesus&5&l&kA&r \""; // 500
			String God = "\"&d&l&kAA&5&lGod&d&l&kAA&r \""; // 1000
			String Unkillable = "\"&0&l&kAA&f&lUnkillable&0&l&kAA&r \""; // 5000

			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Noob prefix " + Noob);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Killer prefix " + Killer);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Butcher prefix " + Butcher);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Murderer prefix " + Murderer);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Assassin prefix " + Assassin);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Warlord prefix " + Warlord);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Jesus prefix " + Jesus);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group God prefix " + God);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Unkillable prefix " + Unkillable);

			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Noob parents set default");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Killer parents set Noob");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Butcher parents set Killer");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Murderer parents set Butcher");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Assassin parents set Murderer");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Warlord parents set Assassin");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Jesus parents set Warlord");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group God parents set Jesus");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Unkillable parents set God");

		}

		return false;
	}

}
