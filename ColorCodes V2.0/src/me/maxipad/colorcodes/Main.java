package me.maxipad.colorcodes;

import java.util.logging.Logger;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.maxipad.colorcodes.commands.List;
import me.maxipad.colorcodes.commands.Reload;

public class Main extends JavaPlugin {

	public Permission swearreloadpermission = new Permission("color.reload");

	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been enabled! (v" + pdfFile.getVersion() + ")");

		registerCommands();
		registerConfig();
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been disabled! (v" + pdfFile.getVersion() + ")");
	}

	public void registerCommands() {
		getCommand("colors").setExecutor(new List(this));
		getCommand("colorreload").setExecutor(new Reload(this));
	}

	public void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
