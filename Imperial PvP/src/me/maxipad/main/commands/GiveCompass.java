package me.maxipad.main.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.maxipad.main.Main;

public class GiveCompass implements CommandExecutor {

	private Main plugin;

	public GiveCompass(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		String prefix = plugin.getConfig().getString("Prefix");

		if (args.length != 0) {
			return false;
		}

		if (!(sender instanceof Player)) {
			return false;
		}

		if (!(player.getInventory().contains(Material.COMPASS))) {

			PlayerInventory playerInv = player.getPlayer().getInventory();
			ItemStack compass = nameItem(Material.COMPASS, ChatColor.AQUA + "FFA Arenas");

			ArrayList<String> compassArray = new ArrayList<String>();
			ItemMeta imCompass = compass.getItemMeta();
			compassArray.add(color("&7Left click me!"));
			imCompass.setLore(compassArray);
			compass.setItemMeta(imCompass);

			playerInv.setItem(4, compass);
		} else {
			player.sendMessage(color(prefix + plugin.getConfig().getString("AlreadyHasCompass")));
		}
		return false;
	}

	private ItemStack nameItem(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);

		item.setItemMeta(meta);
		return item;
	}

	private ItemStack nameItem(Material item, String name) {
		return nameItem(new ItemStack(item), name);
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
