package caching.sandbox;

import java.sql.Connection;

import com.mongodb.client.MongoDatabase;

import caching.sandbox.dao.CountryDAO;
import caching.sandbox.databases.DatabaseAdapter;
import caching.sandbox.databases.MongoDbAdapter;
import caching.sandbox.databases.SqliteAdapter;
import caching.sandbox.models.Country;

public class Main {

	public static void main(String[] args)
	{
		DatabaseAdapter<MongoDatabase> sandboxMongoDb = new MongoDbAdapter("sandbox");
		DatabaseAdapter<Connection> sandboxSqliteDb = new SqliteAdapter("caching-sandbox.db");

		// Add countries to MongoDB
		// DummyData.addSampleCountriesToDatabase(sandboxMongoDb,
		// DummyData.createSampleCountries());

		// Add countries to relational DB
		// DummyData.addSampleCountriesToDatabase(sandboxSqliteDb,
		// DummyData.createSampleCountries());

		Country c1 = CountryDAO.getCountry(sandboxMongoDb, "DE");
		System.out.println("Country queried from MongoDB: " + c1.toString());
		System.out.println("Country queried from MongoDB: " + CountryDAO.getCountry(sandboxMongoDb, "FR").toString());
		
		Country c2 = CountryDAO.getCountry(sandboxSqliteDb, "UA");
		System.out.println("Country queried from SQLite: " + c2.toString());
		System.out.println("Country queried from SQLite: " + CountryDAO.getCountry(sandboxSqliteDb, "IT").toString());
	}
}
