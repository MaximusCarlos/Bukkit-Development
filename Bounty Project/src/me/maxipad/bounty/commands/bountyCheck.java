package me.maxipad.bounty.commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.maxipad.bounty.Bounty;

public class bountyCheck implements CommandExecutor {

	private Bounty plugin;

	public bountyCheck(Bounty pl) {
		plugin = pl;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command! :(");
			return false;
		}

		Player player = (Player) sender;
		
		if(args.length == 0){
			sender.sendMessage(color("&8&l&m<----------&8[&6BountyHelp&8]&8&l&m---------->"));
			sender.sendMessage(color("&6/bounty <name> &8- Displays players bounty!"));
			sender.sendMessage(color("&6/bountyadd <name> <value> &8- Add a bounty!"));
			sender.sendMessage(color("&6/bountyreload &8- Reloads Plugin!"));
			sender.sendMessage(color("&6/bountytop &8- Displays player with highest bounty!"));
		}
		if (args.length == 1) {
			OfflinePlayer t = player.getServer().getOfflinePlayer(args[0]);
			String uuid = t.getUniqueId().toString();
			int bounty = plugin.getConfig().getInt("Players." + uuid + ".Bounty");
			String stringBounty = Integer.toString(bounty);

			if (!(plugin.getConfig().contains("Players." + uuid))) {
				sender.sendMessage(color(plugin.getConfig().getString("Invalid Player")).replaceAll("%player%",
						t.getName().replaceAll("%bounty%", stringBounty)));
				return false;
			}
			player.sendMessage(
					color(plugin.getConfig().getString("Bounty Info")).replaceAll("%player%", t.getName()).replaceAll("%bounty%", stringBounty).replaceAll("%a%", "'"));
			return false;
		}

		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
