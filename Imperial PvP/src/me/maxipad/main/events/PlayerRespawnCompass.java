package me.maxipad.main.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerRespawnCompass implements Listener {

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {

		PlayerInventory playerInv = e.getPlayer().getInventory();
		ItemStack compass = nameItem(Material.COMPASS, ChatColor.AQUA + "FFA Arenas");

		ArrayList<String> compassArray = new ArrayList<String>();
		ItemMeta imCompass = compass.getItemMeta();
		compassArray.add(color("&7Left click me!"));
		imCompass.setLore(compassArray);
		compass.setItemMeta(imCompass);

		playerInv.setItem(4, compass);

	}

	@EventHandler
	public void onCompassClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		if (player.getItemInHand().getType() == Material.COMPASS) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Inventory selectionOne = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Choose a mode!");
				ItemStack pot = new ItemStack(Material.POTION, 1, (short) 8229);
				ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
				ItemStack paper = nameItem(Material.PAPER, ChatColor.RED + "Close");
				
				ItemMeta imPot = pot.getItemMeta();
				imPot.setDisplayName(color("&4&lPot PvP"));
				pot.setItemMeta(imPot);
				
				ItemMeta imGap = gapple.getItemMeta();
				imGap.setDisplayName(color("&6&lGapple PvP"));
				gapple.setItemMeta(imGap);
				
				selectionOne.setItem(0, pot);
				selectionOne.setItem(1, gapple);
				selectionOne.setItem(8, paper);
				player.openInventory(selectionOne);
			}

		}
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
