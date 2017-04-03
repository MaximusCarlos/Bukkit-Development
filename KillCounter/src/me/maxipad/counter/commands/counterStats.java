package me.maxipad.counter.commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.maxipad.counter.KillCounter;

public class counterStats implements CommandExecutor {

	private KillCounter plugin;

	public counterStats(KillCounter pl) {
		plugin = pl;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return true;
		}

		Player player = (Player) sender;
		
		if(args.length == 0){
			sender.sendMessage(color(plugin.getConfig().getString("StatsUsage1")));
		}
		
		if (args.length == 1) {
			OfflinePlayer t = player.getServer().getOfflinePlayer(args[0]);
			String uuid = t.getUniqueId().toString();

			if (!(plugin.getConfig().contains("Players." + uuid))) {
				player.sendMessage(color("&8&l&m<------|&bStats&8&l&m|------>"));
				player.sendMessage(color("&b" + t.getName() + " &7has never logged \n&7onto the server!"));
				player.sendMessage(color("&8&l&m<----------------->"));
				return false;
			}

			int kills = plugin.getConfig().getInt("Players." + uuid + ".Kills");
			int deaths = plugin.getConfig().getInt("Players." + uuid + ".Deaths");
			int points = plugin.getConfig().getInt("Players." + uuid + ".Points");
			double kdr = 0;

			if (kills > 0 && deaths == 0) {
				kdr = kills;
			} else if (kills == 0 && deaths == 0) {
				kdr = 0;
			} else if (kills % 2 == 0 && deaths % 2 == 0) {
				kdr = kills / deaths;
			} else if (kills == deaths) {
				kdr = kills / deaths;
			} else {
				kdr = kills / deaths + 0.5;
			}

			player.sendMessage(color("&8&l&m<------|&bStats&8&l&m|------>"));
			player.sendMessage(color("&b" + t.getName() + "&b's &7stats."));
			player.sendMessage(color("&7Total Kills: &b" + kills));
			player.sendMessage(color("&7Total Deaths: &b" + deaths));

			if (kdr <= 0) {
				player.sendMessage(color("&7K/D Ratio: &bZero or below"));
				player.sendMessage(color("&7Total Points: &b" + points));
				player.sendMessage(color("&8&l&m<----------------->"));
				return false;
			}

			player.sendMessage(color("&7K/D Ratio: &b" + kdr));
			player.sendMessage(color("&7Total Points: &b" + points));
			player.sendMessage(color("&8&l&m<----------------->"));

			return true;
		}
		return true;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
