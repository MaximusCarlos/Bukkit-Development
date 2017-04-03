package me.maxipad.epearlcooldown;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EnderpearlCooldown extends JavaPlugin implements Listener{

	private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	
	@Override
	public void onEnable(){
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(player.getItemInHand().getType() == Material.ENDER_PEARL){
				
				if(cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()) > System.currentTimeMillis()){
				event.setCancelled(true);
				player.updateInventory();
				long remainingTime = cooldown.get(player.getUniqueId()) - System.currentTimeMillis();
				player.sendMessage(ChatColor.RED + "You cannot enderpearl for another " + ChatColor.AQUA + remainingTime/1000 + ChatColor.RED +  " seconds!");
				} 
				
				else {
					cooldown.put(player.getUniqueId(), System.currentTimeMillis() + (15 * 1000));
				}
			}
		}
	}
}
