package xyz.maximuscarlos.thanatosreports.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import xyz.maximuscarlos.thanatosreports.Main;

public class CreateOnlineInventory implements CommandExecutor {

	private Main plugin;

	public CreateOnlineInventory(Main pl) {
		plugin = pl;
	}
	
	Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.RED + "Report a Player!");

	@SuppressWarnings("unchecked")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}

		if (!(sender.hasPermission("thanatosreports.report"))) {
			sender.sendMessage(color(plugin.getConfig().getString("No Permission")));
			return false;
		}
		
		Player player = (Player) sender;
		String[] Players = new String[Bukkit.getServer().getOnlinePlayers().size()];
		
		List<Player> list = new LinkedList<Player>();
		list.clear();
		list = (List<Player>) Bukkit.getServer().getOnlinePlayers();

		int i = 0;
		for (Player player1 : list) {

			if(!(player1.hasPermission("thanatosreports.exempt"))){
			Players[i] = player1.getName();
			i++;		
			} else {
				//do nothing
			}
			
		}
		
		inventory.clear();

		for (int count = 0; count < i; count++) {
			ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta im = (SkullMeta) itemStack.getItemMeta();
			im.setOwner(Players[count]);
			im.setDisplayName(Players[count]);
			itemStack.setItemMeta(im);
			inventory.setItem(count, itemStack);
		}

		
		player.openInventory(inventory);

		return false;

	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
