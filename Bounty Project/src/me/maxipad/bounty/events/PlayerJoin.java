package me.maxipad.bounty.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.maxipad.bounty.Bounty;

public class PlayerJoin implements Listener{
	
	private Bounty plugin;
	
	public PlayerJoin(Bounty pl){
		plugin = pl;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		String uuid = p.getUniqueId().toString();
		if (!(plugin.getConfig().contains("Players." + uuid))) {
			plugin.getConfig().set("Players." + uuid + ".Bounty", 0);
			plugin.saveConfig();
		}
	}

}
