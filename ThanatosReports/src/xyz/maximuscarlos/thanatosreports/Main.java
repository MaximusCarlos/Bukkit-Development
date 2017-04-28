package xyz.maximuscarlos.thanatosreports;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.maximuscarlos.thanatosreports.backend.InitializeTable;
import xyz.maximuscarlos.thanatosreports.commands.CreateOnlineInventory;
import xyz.maximuscarlos.thanatosreports.commands.ReloadPlugin;
import xyz.maximuscarlos.thanatosreports.commands.Reports;
import xyz.maximuscarlos.thanatosreports.events.InventoryClick;

public class Main extends JavaPlugin{
	
	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been enabled (" + pdfFile.getVersion() + ")");
		registerCommands();
		registerConfig();
		registerEvents();
		
	
		InitializeTable initializeTable = new InitializeTable(this);
		
	try {
			initializeTable.createTable();
		} catch (Exception e) {
			System.out.println("[ThanatosReports] Unable to create a table. Check your config and reload the plugin.");
		}
	
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been disabled (" + pdfFile.getVersion() + ")");
	}

	public void registerCommands() {
		getCommand("report").setExecutor(new CreateOnlineInventory(this));
		getCommand("reports").setExecutor(new Reports(this));
		getCommand("reportsreload").setExecutor(new ReloadPlugin(this));
	}
	
	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new InventoryClick(this), this);

	}

	public void registerConfig() {		
		getConfig().options().copyDefaults(true);
		saveConfig();
	}


	

}
