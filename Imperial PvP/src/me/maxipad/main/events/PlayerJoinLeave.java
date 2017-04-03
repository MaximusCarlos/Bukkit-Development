package me.maxipad.main.events;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.maxipad.main.Main;

public class PlayerJoinLeave implements Listener {

	private Main plugin;

	public PlayerJoinLeave(Main pl) {
		plugin = pl;
	}

	/**
	 * private HashMap<UUID, String> tempPlayerTime = new HashMap<UUID,
	 * String>(); DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	 * String endTime = ""; String userTime = "";
	 **/

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		File counter_data = new File(plugin.getDataFolder() + "/Counter_data.yml");
		FileConfiguration Counter_data = YamlConfiguration.loadConfiguration(counter_data);

		/**
		 * Date time = new Date(); String startTime = timeFormat.format(time);
		 * this.tempPlayerTime.put(player.getUniqueId(), startTime);
		 **/

		Player p = e.getPlayer();

		String uuid = p.getUniqueId().toString();
		if (!(Counter_data.contains("Players." + uuid))) {
			Counter_data.set("Players." + uuid + ".Kills", 0);
			Counter_data.set("Players." + uuid + ".Deaths", 0);
			// Counter_data.set("Players." + uuid + ".Time", 0);
			try {
				Counter_data.save(counter_data);
			} catch (IOException E) {
				E.printStackTrace();
			}

			int kills = Counter_data.getInt("Players." + uuid + ".Kills");
			if (kills == 0) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex group Noob user add " + uuid);
			}

			PlayerInventory playerInv = e.getPlayer().getInventory();
			ItemStack compass = nameItem(Material.COMPASS, ChatColor.AQUA + "FFA Arenas");

			ArrayList<String> compassArray = new ArrayList<String>();
			ItemMeta imCompass = compass.getItemMeta();
			compassArray.add(color("&7Left click me!"));
			imCompass.setLore(compassArray);
			compass.setItemMeta(imCompass);
			playerInv.setItem(4, compass);
		}

		if (p.getWorld().getName().equals(plugin.getConfig().getString("Spawn World"))) {
			PlayerInventory playerInv = e.getPlayer().getInventory();
			ItemStack compass = nameItem(Material.COMPASS, ChatColor.AQUA + "FFA Arenas");

			ArrayList<String> compassArray = new ArrayList<String>();
			ItemMeta imCompass = compass.getItemMeta();
			compassArray.add(color("&7Left click me!"));
			imCompass.setLore(compassArray);
			compass.setItemMeta(imCompass);
			playerInv.setItem(4, compass);
		} else {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn " + e.getPlayer());
			PlayerInventory playerInv = e.getPlayer().getInventory();
			ItemStack compass = nameItem(Material.COMPASS, ChatColor.AQUA + "FFA Arenas");

			ArrayList<String> compassArray = new ArrayList<String>();
			ItemMeta imCompass = compass.getItemMeta();
			compassArray.add(color("&7Left click me!"));
			imCompass.setLore(compassArray);
			compass.setItemMeta(imCompass);
			playerInv.setItem(4, compass);
		}

	}

	/**
	 * 
	 * @EventHandler public void onPlayerLeave(PlayerQuitEvent e) {
	 * 
	 *               Player p = e.getPlayer(); Date time = new Date(); endTime =
	 *               timeFormat.format(time); sumTimer(p);
	 * 
	 *               if (!(p.getWorld().getName().equals(plugin.getConfig().
	 *               getString("Spawn World")))){
	 *               Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(
	 *               ), "ci " + e.getPlayer()); }
	 * 
	 *               }
	 * 
	 *               public void sumTimer(Player p) {
	 * 
	 *               try { File counter_data = new File(plugin.getDataFolder() +
	 *               "/Counter_data.yml"); FileConfiguration Counter_data =
	 *               YamlConfiguration.loadConfiguration(counter_data);
	 * 
	 *               Date timeStarted =
	 *               timeFormat.parse(this.tempPlayerTime.get(p.getUniqueId()));
	 *               Date timeEnded = timeFormat.parse(endTime); Long difference
	 *               = (timeEnded.getTime() - timeStarted.getTime()) / 1000; if
	 *               (difference < 0) { difference = (long) +86400; } userTime =
	 *               Long.toString(difference);
	 *               this.tempPlayerTime.remove(p.getUniqueId());
	 * 
	 *               int storedTime = Counter_data.getInt("Players." +
	 *               p.getUniqueId().toString() + ".Time"); int totalTime =
	 *               storedTime + Integer.parseInt(userTime);
	 * 
	 *               Counter_data.set("Players." + p.getUniqueId().toString() +
	 *               ".Time", totalTime);
	 * 
	 *               try { Counter_data.save(counter_data); } catch (IOException
	 *               e) { e.printStackTrace(); }
	 * 
	 *               } catch (Exception e) { System.out.println(
	 *               "Player time did not save as the plugin was reloaded whilst the player was online"
	 *               ); } }
	 * 
	 **/

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
