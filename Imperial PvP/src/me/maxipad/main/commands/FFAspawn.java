package me.maxipad.main.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.maxipad.main.Main;

public class FFAspawn implements CommandExecutor{

	private Main plugin;

	public FFAspawn(Main pl) {
		plugin = pl;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String prefix = plugin.getConfig().getString("Prefix");
		
		if(!(sender.hasPermission("imperialpvp.admin"))){
			sender.sendMessage(color("&4You do not have permission to use this command."));
			return true;
		}
		
		if(args.length == 0){
			sender.sendMessage(color(prefix + plugin.getConfig().getString("FFASpawnUsage")));
			return true;
		}
		
		if(args.length == 1){
			plugin.getConfig().set("Spawn World", args[0]);
			sender.sendMessage(color(prefix + plugin.getConfig().getString("SetSpawn").replaceAll("%world%", args[0])));
			plugin.saveConfig();
			return true;
		}
		
		return false;
	}
	
	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
