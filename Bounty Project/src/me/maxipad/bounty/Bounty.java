package me.maxipad.bounty;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.maxipad.bounty.commands.bountyAdd;
import me.maxipad.bounty.commands.bountyCheck;
import me.maxipad.bounty.commands.bountyReload;
import me.maxipad.bounty.commands.bountyReset;
import me.maxipad.bounty.commands.bountyTop;
import me.maxipad.bounty.events.PlayerDeath;
import me.maxipad.bounty.events.PlayerJoin;
import net.milkbowl.vault.economy.Economy;
import me.maxipad.bounty.backend.InitializeTable;

public class Bounty extends JavaPlugin {

	public Economy econ = null;

	public Permission bountyReload = new Permission("bounty.reload");

	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been enabled (v" + pdfFile.getVersion() + ")!");

		registerCommands();
		registerEvents();
		registerConfig();

		InitializeTable initializeTable = new InitializeTable(this);

		try {
			initializeTable.createTable();
		} catch (Exception e) {
			System.out.println(ChatColor.DARK_RED + "[BountyPlus] [SQL] Unable to create a table. Check your config and reload the plugin.");
		}
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been enabled (v" + pdfFile.getVersion() + ")!");
	}

	public void registerCommands() {
		getCommand("bounty").setExecutor(new bountyCheck(this));
		getCommand("bountyadd").setExecutor(new bountyAdd(this));
		getCommand("bountyreload").setExecutor(new bountyReload(this));
		getCommand("bountyreset").setExecutor(new bountyReset(this));
		getCommand("bountytop").setExecutor(new bountyTop(this));

	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerDeath(this), this);
	}

	public void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
