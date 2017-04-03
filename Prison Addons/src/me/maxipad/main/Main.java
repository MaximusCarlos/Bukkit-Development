package me.maxipad.main;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.maxipad.main.commands.ClearInventory;
import me.maxipad.main.commands.SilkTouchRemove;
import me.maxipad.main.commands.ToggleNV;
import me.maxipad.main.commands.setRates;
import me.maxipad.main.events.CustomDrop;

public class Main extends JavaPlugin {

	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been enabled (" + pdfFile.getVersion() + ")");

		registerCommands();
		registerEvents();
		registerConfig();
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been disabled (" + pdfFile.getVersion() + ")");
	}

	public void registerCommands() {
		getCommand("ci").setExecutor(new ClearInventory(this));
		getCommand("silktouch").setExecutor(new SilkTouchRemove(this));
		getCommand("setRate").setExecutor(new setRates(this));
		getCommand("nv").setExecutor(new ToggleNV());
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new CustomDrop(this), this);
		pm.registerEvents(new ClearInventory(this), this);
	}

	public void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
