package com.maxipad.antiswear;

import java.util.logging.Logger;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.maxipad.antiswear.commands.reload;
import com.maxipad.antiswear.commands.swearlist;
import com.maxipad.antiswear.event.player.PlayerChat;

public class Main extends JavaPlugin {

	public Permission ReloadPermission = new Permission("swear.reload");

	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been enabled! V(" + pdfFile.getVersion() + ")");

		registerEvents();
		registerConfig();
		registerCommands();
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been disabled! V(" + pdfFile.getVersion() + ")");
	}
	
	public void registerCommands() {
		getCommand("swearlist").setExecutor(new swearlist(this));
		getCommand("slreload").setExecutor(new reload(this));

	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new PlayerChat(this), this);

	}

	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
