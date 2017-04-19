package me.maxipad.bounty.backend;

import java.sql.SQLException;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import me.maxipad.bounty.Bounty;

public class InitializeTable {
	
	private Bounty plugin;

	public InitializeTable(Bounty pl) {
		plugin = pl;
	}

	public void createTable() throws SQLException {
		Sql2o sql2o = new Sql2o(
				"jdbc:mysql://" + plugin.getConfig().getString("Host") + ":" + plugin.getConfig().getString("Port")
						+ "/" + plugin.getConfig().getString("Database"),
				plugin.getConfig().getString("User"), plugin.getConfig().getString("Password"));

		try (Connection connection = sql2o.open()) {
			connection
					.createQuery(
							"CREATE TABLE IF NOT EXISTS " + plugin.getConfig().getString("Table") + " (PlayerName varchar(40), PlayerUUID varchar(40), CurrentBounty int) ENGINE=InnoDB DEFAULT CHARSET=utf8")
					.executeUpdate().close();;
		}

	}
}
