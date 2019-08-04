package caching.sandbox;

import java.sql.Connection;
import java.util.Optional;

import com.mongodb.client.MongoDatabase;

import caching.sandbox.caches.GuavaCountryCache;
import caching.sandbox.databases.DatabaseAdapter;
import caching.sandbox.databases.MongoDbAdapter;
import caching.sandbox.databases.SqliteAdapter;
import caching.sandbox.models.Country;

public class Main {

	public static void main(String[] args)
	{
		DatabaseAdapter<MongoDatabase> sandboxMongoDb = new MongoDbAdapter("sandbox");
		DatabaseAdapter<Connection> sandboxSqliteDb = new SqliteAdapter("caching-sandbox.db");

		/*
		 * Add countries to DB
		 */
		// Add countries to MongoDB
		// DummyData.addSampleCountriesToDatabase(sandboxMongoDb,
		// DummyData.createSampleCountries());

		// Add countries to relational DB
		// DummyData.addSampleCountriesToDatabase(sandboxSqliteDb,
		// DummyData.createSampleCountries());

		/*
		 * Get countries from DB
		 */
		// Country c1 = CountryDAO.getCountry(sandboxMongoDb, "DE");
		// System.out.println("Country queried from MongoDB: " + c1.toString());
		// System.out.println("Country queried from MongoDB: " +
		// CountryDAO.getCountry(sandboxMongoDb, "FR").toString());
		//
		// Country c2 = CountryDAO.getCountry(sandboxSqliteDb, "UA");
		// System.out.println("Country queried from SQLite: " + c2.toString());
		// System.out.println("Country queried from SQLite: " +
		// CountryDAO.getCountry(sandboxSqliteDb, "IT").toString());

		/*
		 * Get countries via Guava cache
		 */
		GuavaCountryCache guavaCountryCache = new GuavaCountryCache(sandboxSqliteDb);
		Optional<Country> germany = guavaCountryCache.getCountry("DE");
		System.out.println(germany.get().toString());

		for (int i = 0; i < 7; i++)
		{
			guavaCountryCache.getCountry("US");
			guavaCountryCache.getCountry("GB");
		}
		System.out.println(guavaCountryCache.getCountry("RU").get().toString());
	}
}
