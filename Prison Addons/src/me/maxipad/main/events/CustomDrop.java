package me.maxipad.main.events;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.maxipad.main.Main;

public class CustomDrop implements Listener {

	Random random = new Random();
	
	private Main plugin;

	public CustomDrop(Main pl) {
		plugin = pl;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {

		if (e.getEntityType() == EntityType.GUARDIAN) {
			double chance = random.nextDouble() * 1000;

			if (chance < Integer.parseInt(plugin.getConfig().getString("GUARDet"))) {
				e.getDrops().clear();
				ItemStack magmacream = new ItemStack(Material.MAGMA_CREAM);
				ItemMeta im = magmacream.getItemMeta();
				im.setDisplayName(ChatColor.DARK_AQUA + "Enchant Token");
				magmacream.setItemMeta(im);

				ArrayList<String> lore = new ArrayList<String>();
				lore.add(color("&7Click to redeem &b&nTokens"));
				im.setLore(lore);
				magmacream.setItemMeta(im);
				
				e.getDrops().add(magmacream);
			}
			
			if(chance >= Integer.parseInt(plugin.getConfig().getString("GUARDet")) && chance < Integer.parseInt(plugin.getConfig().getString("GUARDobby"))){
				ItemStack obsidian = new ItemStack(Material.OBSIDIAN);
				e.getDrops().add(obsidian);			
			}
		}
		

		if (e.getEntityType() == EntityType.IRON_GOLEM) {
			double chance = random.nextDouble() * 1000;

			if (chance < 40) {
				e.getDrops().clear();
				ItemStack magmacream = new ItemStack(Material.MAGMA_CREAM);
				ItemMeta im = magmacream.getItemMeta();
				im.setDisplayName(ChatColor.DARK_AQUA + "Enchant Token");
				magmacream.setItemMeta(im);

				ArrayList<String> lore = new ArrayList<String>();
				lore.add(color("&7Click to redeem &b&nTokens"));
				im.setLore(lore);
				magmacream.setItemMeta(im);
				
				e.getDrops().add(magmacream);
			}
			
			if(chance >= Integer.parseInt(plugin.getConfig().getString("IGet")) && chance < Integer.parseInt(plugin.getConfig().getString("IGobby"))){
				ItemStack obsidian = new ItemStack(Material.OBSIDIAN);
				e.getDrops().add(obsidian);			
			} 
		}

	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
