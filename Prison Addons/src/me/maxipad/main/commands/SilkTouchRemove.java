package me.maxipad.main.commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import me.maxipad.main.Main;

public class SilkTouchRemove implements CommandExecutor {

	private Main plugin;

	public SilkTouchRemove(Main pl) {
		plugin = pl;
	}

	private HashMap<UUID, Long> tempPlayerTime = new HashMap<UUID, Long>();

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command.");
			return false;
		}

		String prefix = plugin.getConfig().getString("(ST) Prefix");
		Player player = (Player) sender;
		PlayerInventory inv = player.getInventory();

		if (cmd.getName().equalsIgnoreCase("silktouch") && args.length == 0) {
			sender.sendMessage(color(prefix + plugin.getConfig().getString("(ST) Usage")));
			return false;
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("remove")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to use this command.");
				return false;
			}

			if (!(inv.getItemInHand().getType() == Material.DIAMOND_PICKAXE || inv.getItemInHand().getType() == Material.DIAMOND_HOE
					|| inv.getItemInHand().getType() == Material.DIAMOND_SPADE || inv.getItemInHand().getType() == Material.DIAMOND_AXE
					|| inv.getItemInHand().getType() == Material.IRON_PICKAXE || inv.getItemInHand().getType() == Material.IRON_HOE
					|| inv.getItemInHand().getType() == Material.IRON_SPADE || inv.getItemInHand().getType() == Material.IRON_AXE
					|| inv.getItemInHand().getType() == Material.GOLD_PICKAXE || inv.getItemInHand().getType() == Material.GOLD_HOE
					|| inv.getItemInHand().getType() == Material.GOLD_SPADE || inv.getItemInHand().getType() == Material.GOLD_AXE
					|| inv.getItemInHand().getType() == Material.WOOD_PICKAXE || inv.getItemInHand().getType() == Material.WOOD_HOE
					|| inv.getItemInHand().getType() == Material.WOOD_SPADE || inv.getItemInHand().getType() == Material.WOOD_AXE)) {
				sender.sendMessage(color(prefix + plugin.getConfig().getString("(ST) NotTool")));
				return false;
			}

			if (tempPlayerTime.containsKey(player.getUniqueId()) && tempPlayerTime.get(player.getUniqueId()) > System.currentTimeMillis()) {

				inv.getItemInHand().removeEnchantment(Enchantment.SILK_TOUCH);
				player.sendMessage(color(prefix
						+ plugin.getConfig().getString("(ST) SilkRemoved").replaceAll("%tool%", inv.getItemInHand().getType().toString())));
				tempPlayerTime.remove(player.getUniqueId());
			} else {
				if (!(inv.getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH))) {
					sender.sendMessage(color(prefix + plugin.getConfig().getString("(ST) NoSilkTouch")));
					return false;
				}
			
			int time = Integer.parseInt(plugin.getConfig().getString("(ST) ConfirmTime"));
			tempPlayerTime.put(player.getUniqueId(), System.currentTimeMillis() + (time * 1000));

			sender.sendMessage(" ");
			sender.sendMessage(color(prefix + plugin.getConfig().getString("(ST) Prompt")).replaceAll("%n%", "\n").replaceAll("%time%",
					Integer.toString(time)));
			sender.sendMessage(" ");
			}
		}

		return false;
	}
	
	

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
