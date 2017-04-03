package me.maxipad.bounty.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.maxipad.bounty.Bounty;

public class bountyTop implements CommandExecutor{

	
	
	private Bounty plugin;

	public bountyTop(Bounty pl) {
		plugin = pl;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		String[] names = new String[Bukkit.getOfflinePlayers().length];
		int[] bounty = new int[Bukkit.getOfflinePlayers().length];
		OfflinePlayer[] OfflinePlayers = Bukkit.getServer().getOfflinePlayers();
		int count = 0;;
		
		String topn = "";
		int topb = 0;;
		       
		
		
		for(int i = 0; i < OfflinePlayers.length; i++){
			names[i] = OfflinePlayers[i].getName();
		    bounty[i] = plugin.getConfig().getInt("Players." + OfflinePlayers[i].getUniqueId() + ".Bounty");
		    count++;	    
		}
		
		for(int i = 0; i < count; i++){
			if(bounty[i] > topb){
				topb = bounty[i];
				topn = names[i];
			}	
		}
		String topbs = Integer.toString(topb);
		sender.sendMessage(color(plugin.getConfig().getString("Top Bounty").replaceAll("%player%", topn).replaceAll("%bounty%", topbs)));
		return false;
	}
	
	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	//	ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("Players");
		
	//	for(String key : configSection.getKeys(false)){
	//		sender.sendMessage(Bukkit.getOfflinePlayer(UUID.fromString(key)).getName());
	//	}
		
		
	//	return false;
	//}

}
