package xyz.maximuscarlos.thanatosreports.events;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import me.ellie.thanatosbot.ThanatosBot;
import xyz.maximuscarlos.thanatosreports.Main;
import xyz.maximuscarlos.thanatosreports.backend.StripColor;

public class InventoryClick implements Listener {

	private Main plugin;

	public InventoryClick(Main pl) {
		plugin = pl;
	}

	private String reportedPlayer = "";
	private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	StripColor stripColor = new StripColor();

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		if(!(event.getInventory().getName().contains("Player") || event.getInventory().getName().contains("Report a Player!"))){
			return;
		}

		Player player = (Player) event.getWhoClicked();

		String prefix = plugin.getConfig().getString("Prefix");

		try {

			Inventory stepOne = Bukkit.createInventory(null, 18,
					ChatColor.RED + "Player: " + event.getCurrentItem().getItemMeta().getDisplayName());

			if (event.getCurrentItem().getType() == Material.SKULL_ITEM && event.getInventory().getName().contentEquals(ChatColor.RED + "Report a Player!")) {
				reportedPlayer = event.getCurrentItem().getItemMeta().getDisplayName();
				event.setCancelled(true);

				if (event.getCurrentItem().getItemMeta().getDisplayName() == event.getWhoClicked().getName()) {
					event.setCancelled(true);
					event.getWhoClicked().closeInventory();
					event.getWhoClicked()
							.sendMessage(color(prefix + plugin.getConfig().getString("Cannot report yourself")));
					return;
				}

				int i = 0;
				for (String key : plugin.getConfig().getConfigurationSection("Options").getKeys(false)) {
					String material = plugin.getConfig().getString("Options." + key + ".material");
					String displayName = plugin.getConfig().getString("Options." + key + ".displayname");
					ItemStack reportOptions = nameItem(Material.getMaterial(material), ChatColor.RED + "Close");

					ItemMeta imPot = reportOptions.getItemMeta();
					imPot.setDisplayName(color(displayName));
					reportOptions.setItemMeta(imPot);

					stepOne.setItem(i, reportOptions);
					player.closeInventory();
					player.openInventory(stepOne);
					i++;
				}

				player.openInventory(stepOne);
			} else if(event.getInventory().getName().contains("Player")){
				
				if (cooldown.containsKey(player.getUniqueId())
						&& cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
					long remainingTime = cooldown.get(player.getUniqueId()) - System.currentTimeMillis();
					player.sendMessage(color(prefix + plugin.getConfig().getString("Cooldown Message")).replaceAll("%cooldown%",
							Long.toString(remainingTime / 1000)));
					event.setCancelled(true);
					player.closeInventory();
					return;
				}

				cooldown.put(player.getUniqueId(),
						System.currentTimeMillis() + (plugin.getConfig().getInt("Cooldown Length") * 1000));

				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						cooldown.remove(player.getUniqueId()); // removes player
																// from hash map
					}
				}, plugin.getConfig().getInt("Cooldown Length") * 20); // set by
																		// ticks

				event.setCancelled(true);
				player.closeInventory();

				Sql2o sql2o = new Sql2o(
						"jdbc:mysql://" + plugin.getConfig().getString("Host") + ":"
								+ plugin.getConfig().getString("Port") + "/" + plugin.getConfig().getString("Database"),
						plugin.getConfig().getString("User"), plugin.getConfig().getString("Password"));

				String insertSql = "insert into " + plugin.getConfig().getString("Table")
						+ "(ReportingPlayer, ReportedPlayer, Reason) "
						+ "values (:ReportingPlayerParam, :ReportedPlayerParam, :ReasonParam)";

				
				String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
				
				try (Connection connection = sql2o.open()) {
					connection.createQuery(insertSql).addParameter("ReportingPlayerParam", player.getName())
							.addParameter("ReportedPlayerParam", reportedPlayer).addParameter("ReasonParam", stripColor.getStripColor(itemName)).executeUpdate();
				}
				
				ThanatosBot.getThanatosBot().extMessageChannel(304564681811558401L, "**" + event.getWhoClicked().getName() + "** has reported **" + reportedPlayer + "** for **" + stripColor.getStripColor(itemName) + "**");

				player.sendMessage(color(prefix + plugin.getConfig().getString("Report Success"))
						.replaceAll("%reportedPlayer%", reportedPlayer).replaceAll("%reason%", stripColor.getStripColor(itemName)));
				player.closeInventory();
				
				
				for (World w : Bukkit.getServer().getWorlds()) {
					for (Player p : w.getPlayers()) {
						if (p.hasPermission("thanatosreports.staffmessages")) {
							p.sendMessage(color(prefix + plugin.getConfig().getString("Player was reported (Staff)")
									.replaceAll("%reportingPlayer%", event.getWhoClicked().getName())
									.replaceAll("%reportedPlayer%", reportedPlayer).replaceAll("%reason%", stripColor.getStripColor(itemName))));
						}
					}
				}
			} else {
				return;
			}

		} catch (Exception e) {
			return;
		}
	}

	private ItemStack nameItem(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);

		item.setItemMeta(meta);
		return item;
	}

	private ItemStack nameItem(Material item, String name) {
		return nameItem(new ItemStack(item), name);
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
