package me.maxipad.counter;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.maxipad.counter.commands.counterReload;
import me.maxipad.counter.commands.counterStats;
import me.maxipad.counter.events.PlayerDeath;
import me.maxipad.counter.events.PlayerJoin;
import me.maxipad.counter.events.SideBoard;

public class KillCounter extends JavaPlugin implements Listener {

	public double points = 0;

	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been enabled! (v" + pdfFile.getVersion() + ")");
		this.getServer().getPluginManager().registerEvents(this, this);

		registerEvents();
		registerCommands();
		registerConfig();
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been disabled! (v" + pdfFile.getVersion() + ")");
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerDeath(this), this);

		pm.registerEvents(new SideBoard(this), this);
		

	}

	public void registerCommands() {
		getCommand("cstats").setExecutor(new counterStats(this));
		getCommand("counterreload").setExecutor(new counterReload(this));

	}

	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();

	}
}
