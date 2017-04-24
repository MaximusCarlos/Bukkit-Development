package xyz.maximuscarlos.thanatosreports.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import xyz.maximuscarlos.thanatosreports.Main;

public class ReloadPlugin implements CommandExecutor{
	
	private Main plugin;

	public ReloadPlugin(Main pl) {
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender.hasPermission("thanatosreports.reload"))) {
			plugin.reloadConfig();
			String prefix = plugin.getConfig().getString("Prefix");
			sender.sendMessage(color(prefix + plugin.getConfig().getString("Reload Message")));
		} else {
			sender.sendMessage(color(plugin.getConfig().getString("No Permission")));
		}
		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
