package com.maxipad.antiswear.event.player;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.maxipad.antiswear.Main;

public class PlayerChat implements Listener {

	private Main plugin;

	public PlayerChat(Main pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {

		String block = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("BlockedWordsWarnMessage"));
		String warn = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("DiscouragedWordsWarnMessage"));
		String kick = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("KickableWordsWarnMessage"));

		for (String word : event.getMessage().toLowerCase().split(" ")) {
			if (plugin.getConfig().getStringList("BlockedWords").contains(word)) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(block);
			}
			if (plugin.getConfig().getStringList("DiscouragedWords").contains(word)) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(warn);
			}
			if (plugin.getConfig().getStringList("KickableWords").contains(word)) {
				event.setCancelled(true);
				event.getPlayer().kickPlayer(kick);

			}
		}
	}
}
