package caching.sandbox.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteAdapter implements DatabaseAdapter<Connection>, AutoCloseable {

	private final String connectionString;
	private Connection connection = null;

	public SqliteAdapter(String databaseUrl)
	{
		this.connectionString = "jdbc:sqlite:" + databaseUrl;
	}

	@Override
	public Connection use()
	{
		try {
			this.connection = DriverManager.getConnection(this.connectionString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.connection;
	}

	@Override
	public void close() throws Exception
	{
		this.connection.close();
	}

}
