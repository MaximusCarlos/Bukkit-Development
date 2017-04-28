package xyz.maximuscarlos.thanatosreports.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import xyz.maximuscarlos.thanatosreports.Main;
import xyz.maximuscarlos.thanatosreports.backend.SQLReport;

public class Reports implements CommandExecutor{

	private Main plugin;

	public Reports(Main pl) {
		plugin = pl;
	}
	
	private int id = 0;
	private int count = 0;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}
		
		if(!(sender.hasPermission("thanatosreports.view"))){
			sender.sendMessage(color(plugin.getConfig().getString("No Permission")));
			return false;
		}
		
		String prefix = plugin.getConfig().getString("Prefix");
		
		Sql2o sql2o = new Sql2o(
				"jdbc:mysql://" + plugin.getConfig().getString("Host") + ":"
						+ plugin.getConfig().getString("Port") + "/" + plugin.getConfig().getString("Database"),
				plugin.getConfig().getString("User"), plugin.getConfig().getString("Password"));

		if (args.length == 0) {

			try (Connection connection = sql2o.open()) {
				
				count = 0;
				
				connection.createQuery("SELECT * FROM " + plugin.getConfig().getString("Table")).executeAndFetch(SQLReport.class).forEach(report -> {
					
					count++;
					
					sender.sendMessage(color(plugin.getConfig().getString("List Format")
							.replaceAll("%ID%", Integer.toString(report.getReportID()))
							.replaceAll("%reportingPlayer%", report.getReportingPlayer())
							.replaceAll("%reportedPlayer%", report.getReportedPlayer())
							.replaceAll("%reason%", report.getReason())));
					
				});
				
				if(count == 0){
					sender.sendMessage(color(prefix + plugin.getConfig().getString("No Reports")));
				}
				
			}

			return true;
		}

		if (args[0].equalsIgnoreCase("by") && args.length == 2) {
			try (Connection connection = sql2o.open()) {
				connection.createQuery("SELECT * FROM " + plugin.getConfig().getString("Table") + " WHERE ReportingPlayer = '" + args[1] + "'").executeAndFetch(SQLReport.class).forEach(report -> {
					sender.sendMessage(color(plugin.getConfig().getString("List Format")
							.replaceAll("%ID%", Integer.toString(report.getReportID()))
							.replaceAll("%reportingPlayer%", report.getReportingPlayer())
							.replaceAll("%reportedPlayer%", report.getReportedPlayer())
							.replaceAll("%reason%", report.getReason())));
						});
			}
			
			return true;
		}
		
		
		
		if(args[0].equalsIgnoreCase("deleteall") && args.length == 2){
			
			if(!(sender.hasPermission("thanatosreports.delete"))){
				sender.sendMessage(color(plugin.getConfig().getString("No Permission")));
				return false;
			}
			
			try (Connection con = sql2o.open()) {
				String[] queryInfo = new String[3];
				queryInfo[0] = "DELETE FROM " + plugin.getConfig().getString("Table") + " WHERE ReportingPlayer = '" + args[1] + "'";
				queryInfo[1] = "alter table " + plugin.getConfig().getString("Table") + " drop ReportID";
				queryInfo[2] = "ALTER TABLE " + plugin.getConfig().getString("Table") + " ADD ReportID MEDIUMINT NOT NULL AUTO_INCREMENT Primary Key";
				
				sender.sendMessage(color(prefix + plugin.getConfig().getString("Successful DeleteALL").replaceAll("%reportingPlayer%", args[1])));
			    
				
			    id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() { 	
			    	int i = -1;
			    	public void run() {   
			    		i ++;
			    		con.createQuery(queryInfo[i])
			    		.executeUpdate();
			    		
			    		if(i == 2){
			    			Bukkit.getScheduler().cancelTask(id);
			    		}

			    		
			    	}
			    }, 0, 10);
			}
			
			return true;
		}

		if(args[0].equalsIgnoreCase("delete") && args.length == 2){
			
			if(!(sender.hasPermission("thanatosreports.delete"))){
				sender.sendMessage(color(plugin.getConfig().getString("No Permission")));
				return false;
			}
			
			try{
				Integer.parseInt(args[1]);
			} catch (Exception e){
				sender.sendMessage(color(prefix + plugin.getConfig().getString("Delete ID Error - 1")));
				return false;
			}
				
			try (Connection con = sql2o.open()) {				
				String[] queryInfo = new String[3];
				queryInfo[0] = "DELETE FROM " + plugin.getConfig().getString("Table") + " WHERE ReportID = " + args[1];
				queryInfo[1] = "alter table " + plugin.getConfig().getString("Table") + " drop ReportID";
				queryInfo[2] = "ALTER TABLE " + plugin.getConfig().getString("Table") + " ADD ReportID MEDIUMINT NOT NULL AUTO_INCREMENT Primary Key";
				
				sender.sendMessage(color(prefix + plugin.getConfig().getString("Successful Delete").replaceAll("%ID%", args[1])));
			    
				id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() { 	
					int i = -1;
			    	public void run() {   	
			    		i ++;
			    		
			    		con.createQuery(queryInfo[i])
			    		.executeUpdate();
			    		
			    		if(i == 2){
			    			Bukkit.getScheduler().cancelTask(id);
			    		}
			    	}
			    }, 0, 10);
			}	
			
		}
		
		

		return false;
	}

	public String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
