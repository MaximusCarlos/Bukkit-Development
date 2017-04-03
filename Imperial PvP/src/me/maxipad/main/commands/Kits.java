package me.maxipad.main.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.maxipad.main.Main;

public class Kits implements CommandExecutor {

	private Main plugin;

	public Kits(Main pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;
		String prefix = plugin.getConfig().getString("Prefix");

		if (args.length == 0) {
			return true;
		}

		if (args[0].equalsIgnoreCase("pot") && args.length == 1) {
			if (!(player.hasPermission("imperialpvp.kits.pot"))) {
				player.sendMessage(color("&4You do not have access to that command."));
				return false;
			}

			PlayerInventory playerInv = player.getInventory();

			ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
			ItemStack diamondHelmet = new ItemStack(Material.DIAMOND_HELMET);
			ItemStack diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
			ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);
			ItemStack potionSpeed = new ItemStack(Material.POTION, 1, (short) 8226);
			ItemStack potionStrength = new ItemStack(Material.POTION, 1, (short) 8233);
			ItemStack potionRegeneration = new ItemStack(Material.POTION, 1, (short) 8257);
			ItemStack potionHealth = new ItemStack(Material.POTION, 1, (short) 16421);
			ItemStack foodBeef = new ItemStack(Material.COOKED_BEEF, 16);

			diamondSword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
			diamondHelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			diamondHelmet.addEnchantment(Enchantment.DURABILITY, 3);
			diamondChestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			diamondChestplate.addEnchantment(Enchantment.DURABILITY, 3);
			diamondLeggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			diamondLeggings.addEnchantment(Enchantment.DURABILITY, 3);
			diamondBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			diamondBoots.addEnchantment(Enchantment.DURABILITY, 3);

			playerInv.clear();

			playerInv.setHelmet(diamondHelmet);
			playerInv.setChestplate(diamondChestplate);
			playerInv.setLeggings(diamondLeggings);
			playerInv.setBoots(diamondBoots);

			playerInv.setItem(0, diamondSword);
			playerInv.setItem(1, potionSpeed);
			playerInv.setItem(9, potionSpeed);
			playerInv.setItem(10, potionSpeed);
			playerInv.setItem(2, potionStrength);
			playerInv.setItem(18, potionStrength);
			playerInv.setItem(19, potionStrength);
			playerInv.setItem(3, potionRegeneration);
			playerInv.setItem(27, potionRegeneration);
			playerInv.setItem(28, potionRegeneration);
			playerInv.setItem(8, foodBeef);
			playerInv.setItem(11, potionHealth);
			playerInv.setItem(12, potionHealth);
			playerInv.setItem(13, potionHealth);
			playerInv.setItem(14, potionHealth);
			playerInv.setItem(15, potionHealth);
			playerInv.setItem(16, potionHealth);
			playerInv.setItem(17, potionHealth);
			playerInv.setItem(20, potionHealth);
			playerInv.setItem(21, potionHealth);
			playerInv.setItem(22, potionHealth);
			playerInv.setItem(23, potionHealth);
			playerInv.setItem(24, potionHealth);
			playerInv.setItem(25, potionHealth);
			playerInv.setItem(26, potionHealth);
			playerInv.setItem(29, potionHealth);
			playerInv.setItem(30, potionHealth);
			playerInv.setItem(31, potionHealth);
			playerInv.setItem(32, potionHealth);
			playerInv.setItem(33, potionHealth);
			playerInv.setItem(34, potionHealth);
			playerInv.setItem(35, potionHealth);
			playerInv.setItem(4, potionHealth);
			playerInv.setItem(5, potionHealth);
			playerInv.setItem(6, potionHealth);
			playerInv.setItem(7, potionHealth);


			player.sendMessage(color(prefix + plugin.getConfig().getString("Set Kit").replaceAll("%kit%", args[0])));

		}

		if (args.length == 1 && args[0].equalsIgnoreCase("gapple")) {
			if (!(player.hasPermission("imperialpvp.kits.gapple"))) {
				player.sendMessage(color("&4You do not have access to that command."));
				return false;
			}

			PlayerInventory playerInv = player.getInventory();

			ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
			ItemStack diamondHelmet = new ItemStack(Material.DIAMOND_HELMET);
			ItemStack diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
			ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);
			ItemStack potionSpeed = new ItemStack(Material.POTION, 1, (short) 8226);
			ItemStack potionStrength = new ItemStack(Material.POTION, 1, (short) 8233);
			ItemStack foodBeef = new ItemStack(Material.COOKED_BEEF, 16);
			ItemStack foodGapple = new ItemStack(Material.GOLDEN_APPLE, 64, (short) 1);
			ItemStack ep = new ItemStack(Material.ENDER_PEARL, 1);
			
			diamondSword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
			diamondHelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			diamondHelmet.addEnchantment(Enchantment.DURABILITY, 3);
			diamondChestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			diamondChestplate.addEnchantment(Enchantment.DURABILITY, 3);
			diamondLeggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			diamondLeggings.addEnchantment(Enchantment.DURABILITY, 3);
			diamondBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			diamondBoots.addEnchantment(Enchantment.DURABILITY, 3);

			playerInv.clear();

			playerInv.setHelmet(diamondHelmet);
			playerInv.setChestplate(diamondChestplate);
			playerInv.setLeggings(diamondLeggings);
			playerInv.setBoots(diamondBoots);

			playerInv.setItem(0, diamondSword);
			playerInv.setItem(1, foodGapple);
			playerInv.setItem(2, diamondHelmet);
			playerInv.setItem(3, diamondChestplate);
			playerInv.setItem(4, diamondLeggings);
			playerInv.setItem(5, diamondBoots);
			playerInv.setItem(6, potionSpeed);
			playerInv.setItem(33, potionSpeed);
			playerInv.setItem(24, potionSpeed);
			playerInv.setItem(15, potionSpeed);
			playerInv.setItem(7, potionStrength);
			playerInv.setItem(34, potionStrength);
			playerInv.setItem(25, potionStrength);
			playerInv.setItem(16, potionStrength);
			playerInv.setItem(8, foodBeef);
			playerInv.setItem(35, ep);

			player.sendMessage(color(prefix + plugin.getConfig().getString("Set Kit").replaceAll("%kit%", args[0])));

		}
		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
