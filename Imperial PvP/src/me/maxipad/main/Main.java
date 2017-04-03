package me.maxipad.main;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.maxipad.main.commands.Arenas;
import me.maxipad.main.commands.FFAspawn;
import me.maxipad.main.commands.FirstTimeSetup;
import me.maxipad.main.commands.GiveCompass;
import me.maxipad.main.commands.Kits;
import me.maxipad.main.commands.Ranks;
import me.maxipad.main.commands.Stats;
import me.maxipad.main.events.InventoryClick;
import me.maxipad.main.events.PlayerDeath;
import me.maxipad.main.events.PlayerDrop;
import me.maxipad.main.events.PlayerJoinLeave;
import me.maxipad.main.events.PlayerRespawnCompass;

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
		getCommand("ffa").setExecutor(new Arenas(this));
		getCommand("pvpkit").setExecutor(new Kits(this));
		getCommand("stats").setExecutor(new Stats(this));
		getCommand("ranks").setExecutor(new Ranks(this));
		getCommand("imperialpvp").setExecutor(new FirstTimeSetup());
		getCommand("ffaspawn").setExecutor(new FFAspawn(this));
		getCommand("lc").setExecutor(new GiveCompass(this));
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new PlayerJoinLeave(this), this);
		pm.registerEvents(new PlayerDeath(this), this);
		pm.registerEvents(new PlayerDrop(this), this);
		pm.registerEvents(new InventoryClick(), this);
		pm.registerEvents(new PlayerRespawnCompass(), this);

	}

	public void registerConfig() {		
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
