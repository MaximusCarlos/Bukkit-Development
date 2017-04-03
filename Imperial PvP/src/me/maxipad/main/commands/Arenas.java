package me.maxipad.main.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.maxipad.main.Main;

public class Arenas implements CommandExecutor {

	private Main plugin;

	public Arenas(Main pl) {
		plugin = pl;
	}

	public int random = 0;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}

		File arenas_data = new File(plugin.getDataFolder() + "/Arenas_data.yml");
		FileConfiguration Arenas_data = YamlConfiguration.loadConfiguration(arenas_data);

		String prefix = plugin.getConfig().getString("Prefix");

		Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage(color(prefix + plugin.getConfig().getString("Arenas Usage")));
			return true;
		}

		// help command
		if (args.length == 1 && args[0].equalsIgnoreCase("help")) {

			if (!(player.hasPermission("imperialpvp.arenas.admin"))) {
				player.sendMessage(color("&4You do not have access to that command."));
				return false;
			}
			player.sendMessage(color("&8&l&m<--------|&bImperial PvP Help&8&l&m|-------->"));
			player.sendMessage(color("&7- &b/ffa add <arena> &7to create a new arena."));
			player.sendMessage(color("&7- &b/ffa remove <arena> &7to remove an arena."));
			player.sendMessage(color("&7- &b/ffa list &7to list all arenas."));
			player.sendMessage(color("&7- &b/ffa <arena> rtp <1-4> &7to add RTP's to arena."));
			player.sendMessage(color("&7- &b/ffa <arena> &7to  RTP to the arena."));
			player.sendMessage(color("&7- &b/ffa tp <arena> &7to teleport to an arena."));
			player.sendMessage(color("&7- &b/ffaspawn <worldname> &7 set spawn for compass on join."));
			player.sendMessage(color("&7- &b/lc &7will place a compass in your inventory."));
			return true;
		}

		// list command
		if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
			if (!(player.hasPermission("imperialpvp.arenas.admin"))) {
				player.sendMessage(color("&4You do not have access to that command."));
				return false;
			}
			player.sendMessage(color(plugin.getConfig().getString("Imperial PvP Arenas List Title")));

			int i = 0;
			for (String key : plugin.getConfig().getConfigurationSection("Arenas").getKeys(false)) {
				i++;
				player.sendMessage(color("&7-&b " + i + ". &c" + key));
			}

		}

		// add to list command
		if (args.length == 2 && args[0].equalsIgnoreCase("add") && !(plugin.getConfig().contains("Arenas." + args[1]))) {

			if (!(player.hasPermission("imperialpvp.arenas.admin"))) {
				player.sendMessage(color("&4You do not have access to that command."));
				return false;
			}

			plugin.getConfig().set("Arenas." + args[1] + ".world", player.getLocation().getWorld().getName());
			plugin.getConfig().set("Arenas." + args[1] + ".x", player.getLocation().getX());
			plugin.getConfig().set("Arenas." + args[1] + ".y", player.getLocation().getY());
			plugin.getConfig().set("Arenas." + args[1] + ".z", player.getLocation().getZ());

			player.sendMessage(color(prefix + plugin.getConfig().getString("Added").replaceAll("%arena%", args[1])));
			plugin.saveConfig();

			return true;
		} else if (args.length == 2 && args[0].equalsIgnoreCase("add") && plugin.getConfig().contains("Arenas." + args[1])) {
			sender.sendMessage(color(prefix + plugin.getConfig().getString("Duplicate Arena").replaceAll("%arena%", args[1])));
			return true;
		}

		// remove from list command
		if (args.length == 2 && args[0].equalsIgnoreCase("remove") && plugin.getConfig().contains("Arenas." + args[1])) {
			if (!(player.hasPermission("imperialpvp.arenas.admin"))) {
				player.sendMessage(color("&4You do not have access to that command."));
				return false;
			}

			plugin.getConfig().set("Arenas." + args[1], null);
			player.sendMessage(color(prefix + plugin.getConfig().getString("Removed").replaceAll("%arena%", args[1])));
			plugin.saveConfig();
			try {
				Arenas_data.set(args[1], null);
				Arenas_data.save(arenas_data);

			} catch (IOException e) {
				e.printStackTrace();
			}

			return true;
		} else if (args.length == 2 && args[0].equalsIgnoreCase("remove") && !(plugin.getConfig().contains("Arenas." + args[1]))) {

			if (!(player.hasPermission("imperialpvp.arenas.admin"))) {
				player.sendMessage(color("&4You do not have access to that command."));
				return false;
			}

			player.sendMessage(color(prefix + plugin.getConfig().getString("Invalid Arena").replaceAll("%arena%", args[1])));
			return false;
		}

		// teleport to arena command
		if (args.length == 1 && plugin.getConfig().contains("Arenas." + args[0])) {
			if (random < 4) {
				random++;
			} else if (random == 4) {
				random = 1;
			}
			try {
				World w = Bukkit.getServer().getWorld(Arenas_data.getString(args[0] + ".world." + random));
				double x = Arenas_data.getDouble(args[0] + ".x." + random);
				double y = Arenas_data.getDouble(args[0] + ".y." + random);
				double z = Arenas_data.getDouble(args[0] + ".z." + random);
				float yaw = (float) Arenas_data.getDouble(args[0] + ".yaw." + random);
				float pitch = (float) Arenas_data.getDouble(args[0] + ".pitch." + random);
				Location loc = new Location(w, x, y, z);
				loc.setYaw(yaw);
				loc.setPitch(pitch);
				player.teleport(loc);

			} catch (Exception e) {
				sender.sendMessage(color(prefix + plugin.getConfig().getString("Missing4Rtps")).replaceAll("%arena%", args[0]));
				return false;
			}

			player.sendMessage(color(prefix + plugin.getConfig().getString("Teleport").replaceAll("%arena%", args[0])));

			if (args.length == 1 && plugin.getConfig().contains("Arena." + args[0])) {
				player.sendMessage(color(prefix + plugin.getConfig().getString("Invalid Arena").replaceAll("%arena%", args[0])));
			}

			return false;

		}

		// add an rtp to an arena command

		if (plugin.getConfig().contains("Arenas." + args[0]) && args[1].equalsIgnoreCase("rtp") && args.length == 2) {
			if (!(player.hasPermission("imperialpvp.arenas.admin"))) {
				player.sendMessage(color("&4You do not have access to that command."));
				return false;
			}
			player.sendMessage(color(prefix = plugin.getConfig().getString("RTP Usage")));
			return false;
		}

		if (plugin.getConfig().contains("Arenas." + args[0]) && args[1].equalsIgnoreCase("rtp") && args.length > 1) {

			if (!(player.hasPermission("imperialpvp.arenas.admin"))) {
				player.sendMessage(color("&4You do not have access to that command."));
				return false;
			}
			if (Integer.parseInt(args[2]) < 1 || Integer.parseInt(args[2]) > 4) {
				player.sendMessage(color(prefix + plugin.getConfig().getString("RTPValueError")));
				return false;
			}
			Arenas_data.set(args[0] + ".world." + args[2], player.getLocation().getWorld().getName());
			Arenas_data.set(args[0] + ".x." + args[2], player.getLocation().getX());
			Arenas_data.set(args[0] + ".y." + args[2], player.getLocation().getY());
			Arenas_data.set(args[0] + ".z." + args[2], player.getLocation().getZ());
			Arenas_data.set(args[0] + ".yaw." + args[2], player.getLocation().getYaw());
			Arenas_data.set(args[0] + ".pitch." + args[2], player.getLocation().getPitch());
			try {
				Arenas_data.save(arenas_data);
			} catch (IOException e) {
				e.printStackTrace();
			}
			player.sendMessage(
					color(prefix + plugin.getConfig().getString("SetRTP").replaceAll("%arena%", args[0]).replaceAll("%rtpvalue%", args[2])));

			if (!(plugin.getConfig().contains("Arenas." + args[0]))) {
				if (!(player.hasPermission("imperialpvp.arenas.admin"))) {
					player.sendMessage(color("&4You do not have access to that command."));
					player.sendMessage(color(prefix + plugin.getConfig().getString("Invalid Arena").replaceAll("%arena%", args[0])));

					return false;
				}
				if (!(plugin.getConfig().contains("Arenas." + args[0])) && args[1].equalsIgnoreCase("rtp") && args.length > 1) {
					if (!(player.hasPermission("imperialpvp.arenas.admin"))) {
						player.sendMessage(color("&4You do not have access to that command."));
						return false;
					}
				}
			}
		}

		if (args[0].equalsIgnoreCase("tp") && args.length == 1) {
			player.sendMessage(color(prefix + plugin.getConfig().getString("TP Usage")));
			return false;
		}

		if (args[0].equalsIgnoreCase("tp") && plugin.getConfig().contains("Arenas." + args[1])) {

			if (!(player.hasPermission("imperialpvp.arenas.admin"))) {
				player.sendMessage(color("&4You do not have access to that command."));
				return false;
			}

			if (!(plugin.getConfig().contains("Arenas." + args[1]))) {
				player.sendMessage(color(prefix + plugin.getConfig().getString("Invalid Arena")).replaceAll("%arena%", args[1]));
			}
			World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Arenas." + args[1] + ".world"));
			double x = plugin.getConfig().getDouble("Arenas." + args[1] + ".x");
			double y = plugin.getConfig().getDouble("Arenas." + args[1] + ".y");
			double z = plugin.getConfig().getDouble("Arenas." + args[1] + ".z");
			Location loc = new Location(w, x, y, z);
			player.teleport(loc);
			player.sendMessage(color(prefix + plugin.getConfig().getString("Teleport").replaceAll("%arena%", args[1])));
			return true;

		}

		return false;

	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
