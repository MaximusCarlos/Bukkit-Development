package me.maxipad.counter.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.maxipad.counter.KillCounter;

public class PlayerJoin implements Listener {

	private KillCounter plugin;

	public PlayerJoin(KillCounter pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		String uuid = p.getUniqueId().toString();
		if (!(plugin.getConfig().contains("Players." + uuid))) {
			plugin.getConfig().set("Players." + uuid + ".Kills", 0);
			plugin.getConfig().set("Players." + uuid + ".Deaths", 0);
			plugin.getConfig().set("Players." + uuid + ".Points", 0);
			plugin.saveConfig();
		}
	}

}
