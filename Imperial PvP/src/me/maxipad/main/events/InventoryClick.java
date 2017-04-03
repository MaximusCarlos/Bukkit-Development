package me.maxipad.main.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClick implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		try {

			if (!(e.getWhoClicked() instanceof Player)) {
				return;
			}

			Inventory inv = e.getInventory();
			Player player = (Player) e.getWhoClicked();

			if (inv.getTitle().equals(ChatColor.AQUA + "Available Ranks")) {
				e.setCancelled(true);
				player.closeInventory();
				return;
			}

			if (inv.getTitle().equals(ChatColor.AQUA + "Choose a mode!")) {

				if (e.getCurrentItem().getType() == Material.PAPER) {
					e.setCancelled(true);
					player.closeInventory();
				}

				if (e.getCurrentItem().getType() == Material.AIR) {
					e.setCancelled(true);
					return;
				}

				if (e.getCurrentItem().getType() == Material.POTION) {
					e.setCancelled(true);
					player.closeInventory();

					Inventory selectionArenaPot = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Choose an Arena! (PotPvP)");
					ItemStack back = nameItem(Material.PAPER, ChatColor.RED + "Back");
					ItemStack arena1 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 1");
					ItemStack arena2 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 2");
					ItemStack arena3 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 3");
					ItemStack arena4 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 4");
					ItemStack arena5 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 5");
					ItemStack arena6 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 6");
					ItemStack arena7 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 7");
					ItemStack arena8 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 8");

					selectionArenaPot.setItem(0, back);
					selectionArenaPot.setItem(1, arena1);
					selectionArenaPot.setItem(2, arena2);
					selectionArenaPot.setItem(3, arena3);
					selectionArenaPot.setItem(4, arena4);
					selectionArenaPot.setItem(5, arena5);
					selectionArenaPot.setItem(6, arena6);
					selectionArenaPot.setItem(7, arena7);
					selectionArenaPot.setItem(8, arena8);

					player.openInventory(selectionArenaPot);
					return;
				}
				if (e.getCurrentItem().getType() == Material.GOLDEN_APPLE) {
					e.setCancelled(true);
					player.closeInventory();

					Inventory selectionArenaPot = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Choose an Arena! (GapplePvP)");
					ItemStack back = nameItem(Material.PAPER, ChatColor.RED + "Back");
					ItemStack arena1 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 1");
					ItemStack arena2 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 2");
					ItemStack arena3 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 3");
					ItemStack arena4 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 4");
					ItemStack arena5 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 5");
					ItemStack arena6 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 6");
					ItemStack arena7 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 7");
					ItemStack arena8 = nameItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Arena 8");


					selectionArenaPot.setItem(0, back);
					selectionArenaPot.setItem(1, arena1);
					selectionArenaPot.setItem(2, arena2);
					selectionArenaPot.setItem(3, arena3);
					selectionArenaPot.setItem(4, arena4);
					selectionArenaPot.setItem(5, arena5);
					selectionArenaPot.setItem(6, arena6);
					selectionArenaPot.setItem(7, arena7);
					selectionArenaPot.setItem(8, arena8);

					player.openInventory(selectionArenaPot);
					return;
				}

				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "FFA Arenas")) {
					e.setCancelled(true);
					player.closeInventory();
				}

				return;
			}

			if (inv.getTitle().equals(ChatColor.AQUA + "Choose an Arena! (PotPvP)")) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 1")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena1-Pot");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Pot");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 2")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena2-Pot");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Pot");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 3")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena3-Pot");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Pot");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 4")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena4-Pot");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Pot");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 5")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena5-Pot");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Pot");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 6")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena6-Pot");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Pot");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 7")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena7-Pot");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Pot");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 8")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena8-Pot");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Pot");
				}

				e.setCancelled(true);
				player.closeInventory();
			}

			if (inv.getTitle().equals(ChatColor.AQUA + "Choose an Arena! (GapplePvP)")) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 1")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena1-Gapple");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Gapple");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 2")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena2-Gapple");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Gapple");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 3")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena3-Gapple");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Gapple");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 4")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena4-Gapple");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Gapple");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 5")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena5-Gapple");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Gapple");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 6")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena6-Gapple");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Gapple");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 7")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena7-Gapple");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Gapple");
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Arena 8")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " ffa Arena8-Gapple");
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName().toString() + " pvpkit Gapple");
				}

				e.setCancelled(true);
				player.closeInventory();

			}

			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Back")) {
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
				player.closeInventory();
				player.openInventory(selectionOne);
			}

			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "FFA Arenas")) {
				e.setCancelled(true);
				player.closeInventory();
			}

		} catch (Exception ee) {
			return;
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
