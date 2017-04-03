package me.maxipad.chatlock;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatLock extends JavaPlugin implements Listener {

	public boolean locked = false;

	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been enabled (v" + pdfFile.getVersion() + ")!\n" + pdfFile.getDescription());

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been enabled (v" + pdfFile.getVersion() + ")!");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("chatlock") && args.length == 0) {
			if (sender.hasPermission("chatlock.lock")) {
				if (locked == false) {
					locked = true;
					if (getConfig().getString("Show IGN that locked").equalsIgnoreCase("True")) {
						Bukkit.broadcastMessage(color(getConfig().getString("Lock Message").replaceAll("%p", sender.getName())));
					} else {
						Bukkit.broadcastMessage(color(getConfig().getString("Lock Message without IGN")));

					}
				} else if (locked == true) {
					locked = false;
					if (getConfig().getString("Show IGN that locked").equalsIgnoreCase("True")) {
						Bukkit.broadcastMessage(color(getConfig().getString("Unlock Message").replaceAll("%p", sender.getName())));
					} else {
						Bukkit.broadcastMessage(color(getConfig().getString("Unlock Message without IGN")));
					}

				}
			} else {
				sender.sendMessage(color("&4You do not have access to that command."));
			}
		}

		if (command.getName().equalsIgnoreCase("chatlockreload") && args.length == 0) {
			if (!(sender.hasPermission("chatlock.lock"))) {
				sender.sendMessage(color("&4You do not have access to that command."));
			} else {
				reloadConfig();
				sender.sendMessage(color("&bChatLock &chas been successfully reloaded! &b(v1.0)"));
			}
		}

		if (command.getName().equalsIgnoreCase("chatlock") && args.length > 1 && args[0].equalsIgnoreCase("ign")) {
			if (!(sender.hasPermission("chatlock.lock"))) {
				sender.sendMessage(color("&4You do not have access to that command."));
			}

			String choice = "";
			if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {
				choice = args[1];
			} else {
				sender.sendMessage(color("&cPlease specify: &bTrue &c/ &bFalse"));
				return false;
			}

			sender.sendMessage(color("&cShow IGN: &b" + choice));
			getConfig().set("Show IGN that locked", choice);
			saveConfig();
		}

		return false;
	}

	@EventHandler
	public void chatLockCheck(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (locked) { // short for if locked == true
			if (!(p.hasPermission("chatlock.bypass.chat"))) {
				e.setCancelled(true);
				p.sendMessage(color(getConfig().getString("Currently Locked").replaceAll("%p", p.getName())));
			}

		}
	}

	public String m;

	@EventHandler
	public void chatLockCommandCheck(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (locked == true) {
			if (!(e.getPlayer().hasPermission("chatlock.bypass.command"))) {
				for (String command : e.getMessage().toLowerCase().split(" ")) {
					if (getConfig().getStringList("Blocked Commands On Chat Lock").contains(command)) {
						e.setCancelled(true);
					}
				}
				if (e.isCancelled()) {
					p.sendMessage(color(getConfig().getString("Currently Locked").replaceAll("%p", p.getName())));
				}
			}
		}
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
