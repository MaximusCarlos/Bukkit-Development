package me.maxipad.main.commands;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.maxipad.main.Main;

public class Stats implements CommandExecutor {

	private Main plugin;

	public Stats(Main pl) {
		plugin = pl;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		File counter_data = new File(plugin.getDataFolder() + "/Counter_data.yml");
		FileConfiguration Counter_data = YamlConfiguration.loadConfiguration(counter_data);
		String prefix = plugin.getConfig().getString("Prefix");
		DecimalFormat roundFormat = new DecimalFormat("0.00");
		DecimalFormat roundFormat1 = new DecimalFormat("0");

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return true;
		}

		Player player = (Player) sender;
		if (args.length == 1 && args[0].equalsIgnoreCase("help")) {

			player.sendMessage(color("&8&l&m<--------|&bImperial PvP Help&8&l&m|-------->"));
			player.sendMessage(color("&7- &b/stats <name> &7to check a player's stats."));
			player.sendMessage(color("&7- &b/stats reset <name> &7to reset a player's stats."));
			return false;
		}

		if (args.length == 0) {

			String uuid = player.getUniqueId().toString();

			if (!(Counter_data.contains("Players." + uuid))) {
				player.sendMessage(color("&8&l&m<------|&b&lStats&8&l&m|------>"));
				player.sendMessage(color("&7&lPlease relog to load your stats."));
				player.sendMessage(color("&8&l&m<----------------->"));
				return false;
			}

			double kills = Counter_data.getInt("Players." + uuid + ".Kills");
			double deaths = Counter_data.getInt("Players." + uuid + ".Deaths");
			// double time = Counter_data.getInt("Players." + uuid + ".Time");
			String kdr = roundFormat.format(kills / deaths);

			if (kills == 0 && deaths == 0) {
				kdr = "0";
			}

			if (kills > 0 && deaths == 0) {
				String kdrRound = roundFormat1.format(kills);
				kdr = kdrRound;
			}

			if (kills > 0 && deaths == 1) {
				String kdrRound = roundFormat1.format(kills);
				kdr = kdrRound;
			}

			player.sendMessage(color("&8&l&m<------|&b&lStats&8&l&m|------>"));
			player.sendMessage(color("&b&l" + player.getName() + "&b&l's &7&lstats."));
			player.sendMessage(color("&7&lTotal Kills: &b&l" + roundFormat1.format(kills)));
			player.sendMessage(color("&7&lTotal Deaths: &b&l" + roundFormat1.format(deaths)));
			player.sendMessage(color("&7&lK/D Ratio: &b&l" + kdr));
			player.sendMessage(color("&7&lTime Played: &b&lTBA"));
			player.sendMessage(color("&8&l&m<----------------->"));

			return false;
		}

		if (args[0].equalsIgnoreCase("reset") && args.length == 2) {

			if (!(player.hasPermission("imperialpvp.stats.reset"))) {
				player.sendMessage(color("&4You do not have access to that command."));
				return false;
			}

			OfflinePlayer t = player.getServer().getOfflinePlayer(args[1]);
			String uuid = t.getUniqueId().toString();

			if (!(Counter_data.contains("Players." + uuid))) {
				player.sendMessage(color("&8&l&m<------|&b&lStats&8&l&m|------>"));
				player.sendMessage(color("&b&l" + t.getName() + " &7&lhas never logged \n&7&lonto the server!"));
				player.sendMessage(color("&8&l&m<----------------->"));
				return false;
			}

			Counter_data.set("Players." + uuid, null);
			player.sendMessage(color(prefix + "&b" + t.getName() + "'s &7stats have been reset!"));

			try {
				Counter_data.save(counter_data);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return false;
		}

		if (!(args[0].equalsIgnoreCase("help"))) {
			if (args.length == 1) {
				OfflinePlayer t = player.getServer().getOfflinePlayer(args[0]);
				String uuid = t.getUniqueId().toString();

				if (!(Counter_data.contains("Players." + uuid))) {
					player.sendMessage(color("&8&l&m<------|&b&lStats&8&l&m|------>"));
					player.sendMessage(color("&b&l" + t.getName() + " &7&lhas never logged \n&7&lonto the server!"));
					player.sendMessage(color("&8&l&m<----------------->"));
					return false;
				}

				double kills = Counter_data.getInt("Players." + uuid + ".Kills");
				double deaths = Counter_data.getInt("Players." + uuid + ".Deaths");
				// double time = Counter_data.getInt("Players." + uuid +
				// ".Time");
				String kdr = roundFormat.format(kills / deaths);

				if (kills == 0 && deaths == 0) {
					kdr = "0";
				}

				if (kills > 0 && deaths == 0) {
					String kdrRound = roundFormat1.format(kills);
					kdr = kdrRound;
				}

				if (kills > 0 && deaths == 1) {
					String kdrRound = roundFormat1.format(kills);
					kdr = kdrRound;
				}

				player.sendMessage(color("&8&l&m<------|&b&lStats&8&l&m|------>"));
				player.sendMessage(color("&b&l" + t.getName() + "&b&l's &7&lstats."));
				player.sendMessage(color("&7&lTotal Kills: &b&l" + roundFormat1.format(kills)));
				player.sendMessage(color("&7&lTotal Deaths: &b&l" + roundFormat1.format(deaths)));
				player.sendMessage(color("&7&lK/D Ratio: &b&l" + kdr));
				player.sendMessage(color("&7&lTime Played: &b&lTBA"));
				player.sendMessage(color("&8&l&m<----------------->"));

				return true;
			}
		}
		return true;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
