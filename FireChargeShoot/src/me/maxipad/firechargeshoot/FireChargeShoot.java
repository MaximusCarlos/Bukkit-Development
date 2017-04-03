package me.maxipad.firechargeshoot;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class FireChargeShoot extends JavaPlugin implements Listener {

	public Permission firecharge = new Permission("firecharge.shoot");

	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been enabled (v" + pdfFile.getVersion() + ")!");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been disabled (v" + pdfFile.getVersion() + ")!");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equals("firereload")) {
			if (!(sender.hasPermission("fire.reload"))) {
				sender.sendMessage(ChatColor.RED + "Insufficient Permissions");
				return false;
			}
			reloadConfig();
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Reload Message")));
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		if (getConfig().getString("Permissions").contains("off")) {
			if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) {
				return;
			}

			if (!(e.getItem().getType() == Material.FIREBALL)) {
				return;
			}

			String stringpower = getConfig().getString("Power");
			float floatpower = Float.parseFloat(stringpower);

			Fireball f = e.getPlayer().launchProjectile(Fireball.class);
			f.setIsIncendiary(true);
			f.setYield(floatpower);

			int amount = e.getPlayer().getInventory().getItemInHand().getAmount() - 1;
			ItemStack charge = new ItemStack(Material.FIREBALL, amount);
			e.getPlayer().getInventory().setItemInHand(charge);
		}

		if (getConfig().getString("Permissions").contains("on")) {
			if ((e.getPlayer().hasPermission("firecharge.shoot"))) {
				if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) {
					return;
				}

				if (!(e.getItem().getType() == Material.FIREBALL)) {
					return;
				}

				String stringpower = getConfig().getString("Power");
				float floatpower = Float.parseFloat(stringpower);

				Fireball f = e.getPlayer().launchProjectile(Fireball.class);
				f.setIsIncendiary(true);
				f.setYield(floatpower);

				int amount = e.getPlayer().getInventory().getItemInHand().getAmount() - 1;
				ItemStack charge = new ItemStack(Material.FIREBALL, amount);
				e.getPlayer().getInventory().setItemInHand(charge);
			}
		}
	}
}
