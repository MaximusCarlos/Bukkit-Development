package xyz.maximuscarlos.thanatosreports.backend;

import java.sql.SQLException;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import xyz.maximuscarlos.thanatosreports.Main;

public class InitializeTable {

	private Main plugin;

	public InitializeTable(Main pl) {
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
							"CREATE TABLE IF NOT EXISTS " + plugin.getConfig().getString("Table") + " (ReportID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, ReportingPlayer varchar(40) NOT NULL,"
									+ " ReportedPlayer varchar(40) NOT NULL, Reason varchar(40) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;")
					.executeUpdate().close();;
		}

	}

}
