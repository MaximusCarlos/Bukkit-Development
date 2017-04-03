package me.maxipad.main.commands;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.maxipad.main.Main;

public class Ranks implements CommandExecutor {

	private Main plugin;

	public Ranks(Main pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;
		Inventory ranks = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Available Ranks");
		ItemStack score = nameItem(Material.PAPER, ChatColor.RED + "Current Kills");
		ItemStack killer = nameItem(Material.COAL, ChatColor.GREEN + "Killer");
		ItemStack butcher = nameItem(Material.IRON_INGOT, ChatColor.GOLD + "Butcher");
		ItemStack murderer = nameItem(Material.GOLD_INGOT, ChatColor.DARK_GREEN + "Murderer");
		ItemStack assassin = nameItem(Material.LAPIS_ORE, ChatColor.BLUE + "Assassin");
		ItemStack warlord = nameItem(Material.OBSIDIAN, ChatColor.AQUA + "Warlord");
		ItemStack jesus = nameItem(Material.DIAMOND, ChatColor.LIGHT_PURPLE + "Jesus");
		ItemStack god = nameItem(Material.EMERALD, ChatColor.DARK_PURPLE + "God");
		ItemStack unkillable = nameItem(Material.NETHER_STAR, ChatColor.WHITE + "Unkillable");

		File counter_data = new File(plugin.getDataFolder() + "/Counter_data.yml");
		FileConfiguration Counter_data = YamlConfiguration.loadConfiguration(counter_data);

		int kills = Counter_data.getInt("Players." + player.getUniqueId().toString() + ".Kills");

		ArrayList<String> scoreArray = new ArrayList<String>();
		ItemMeta imScore = score.getItemMeta();
		scoreArray.add(color("&b&l" + kills + " &7kills"));
		imScore.setLore(scoreArray);
		score.setItemMeta(imScore);

		int killerLeft = 10 - kills;
		if (killerLeft <= 0) {
			killerLeft = 0;
		}

		int butcherLeft = 25 - kills;
		if (butcherLeft <= 0) {
			butcherLeft = 0;
		}

		int murdererLeft = 50 - kills;
		if (murdererLeft <= 0) {
			murdererLeft = 0;
		}

		int assassinLeft = 100 - kills;
		if (assassinLeft <= 0) {
			assassinLeft = 0;
		}

		int warlordLeft = 250 - kills;
		if (warlordLeft <= 0) {
			warlordLeft = 0;
		}

		int jesusLeft = 500 - kills;
		if (jesusLeft <= 0) {
			jesusLeft = 0;
		}

		int godLeft = 1000 - kills;
		if (godLeft <= 0) {
			godLeft = 0;
		}

		int unkillableLeft = 5000 - kills;
		if (unkillableLeft <= 0) {
			unkillableLeft = 0;
		}
		ArrayList<String> killerArray = new ArrayList<String>();
		ItemMeta imKiller = killer.getItemMeta();
		killerArray.add(color("&710 Kills"));
		killerArray.add(color("&b" + killerLeft + " &7to go!"));
		imKiller.setLore(killerArray);
		killer.setItemMeta(imKiller);

		ArrayList<String> butcherArray = new ArrayList<String>();
		ItemMeta imButcher = butcher.getItemMeta();
		butcherArray.add(color("&725 Kills"));
		butcherArray.add(color("&b" + butcherLeft + " &7to go!"));
		imButcher.setLore(butcherArray);
		butcher.setItemMeta(imButcher);

		ArrayList<String> murdererArray = new ArrayList<String>();
		ItemMeta imMurderer = murderer.getItemMeta();
		murdererArray.add(color("&750 Kills"));
		murdererArray.add(color("&b" + murdererLeft + " &7to go!"));		
		imMurderer.setLore(murdererArray);
		murderer.setItemMeta(imMurderer);

		ArrayList<String> assassinArray = new ArrayList<String>();
		ItemMeta imAssasin = assassin.getItemMeta();
		assassinArray.add(color("&7100 Kills"));
		assassinArray.add(color("&b" + assassinLeft + " &7to go!"));		
		imAssasin.setLore(assassinArray);
		assassin.setItemMeta(imAssasin);

		ArrayList<String> warlordArray = new ArrayList<String>();
		ItemMeta imWarlord = warlord.getItemMeta();
		warlordArray.add(color("&7250 Kills"));
		warlordArray.add(color("&b" + warlordLeft + " &7to go!"));		
		imWarlord.setLore(warlordArray);
		warlord.setItemMeta(imWarlord);

		ArrayList<String> jesusArray = new ArrayList<String>();
		ItemMeta imJesus = jesus.getItemMeta();
		jesusArray.add(color("&7500 Kills"));
		jesusArray.add(color("&b" + jesusLeft + " &7to go!"));		
		imJesus.setLore(jesusArray);
		jesus.setItemMeta(imJesus);

		ArrayList<String> godArray = new ArrayList<String>();
		ItemMeta imGod = god.getItemMeta();
		godArray.add(color("&71000 Kills"));
		godArray.add(color("&b" + godLeft + " &7to go!"));		
		imGod.setLore(godArray);
		god.setItemMeta(imGod);

		ArrayList<String> unkillableArray = new ArrayList<String>();
		ItemMeta imUnkillable = unkillable.getItemMeta();
		unkillableArray.add(color("&75000 Kills"));
		unkillableArray.add(color("&b" + unkillableLeft + " &7to go!"));		
		imUnkillable.setLore(unkillableArray);
		unkillable.setItemMeta(imUnkillable);

		ranks.setItem(0, score);
		ranks.setItem(1, killer);
		ranks.setItem(2, butcher);
		ranks.setItem(3, murderer);
		ranks.setItem(4, assassin);
		ranks.setItem(5, warlord);
		ranks.setItem(6, jesus);
		ranks.setItem(7, god);
		ranks.setItem(8, unkillable);
		player.openInventory(ranks);

		return true;
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
