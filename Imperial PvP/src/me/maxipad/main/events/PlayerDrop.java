package me.maxipad.main.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.Plugin;

import me.maxipad.main.Main;

public class PlayerDrop implements Listener {

	private Main plugin;

	public PlayerDrop(Main pl) {
		plugin = pl;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void handleItemDrop(PlayerDropItemEvent event) {
		try {
			if (event.getItemDrop().getItemStack().getTypeId() == 374) {
				event.getItemDrop().remove();
			}

			if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "FFA Arenas")) {
				event.setCancelled(true);
				String prefix = plugin.getConfig().getString("Prefix");
				event.getPlayer().sendMessage(color(prefix + plugin.getConfig().getString("CannotDrop")));
				doInventoryUpdate(event.getPlayer(), plugin);
			}

		} catch (Exception ee) {
			return;
		}
	}

	public static void doInventoryUpdate(final Player player, Plugin plugin) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				player.updateInventory();
			}

		}, 1L);

	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
