package caching.sandbox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import caching.sandbox.databases.DatabaseAdapter;
import caching.sandbox.databases.DatabaseCollections;
import caching.sandbox.databases.MongoDbAdapter;
import caching.sandbox.models.Country;

public class CountryDAO {

	public static void addCountry(DatabaseAdapter<?> db, Country country)
	{
		// MongoDB
		if (db instanceof MongoDbAdapter)
		{
			MongoCollection<Document> countriesCollection = ((MongoDatabase) db.use())
					.getCollection("caching_countries");

			Document countryDocument = new Document("name", country.getName());
			countryDocument.append("alpha2Code", country.getAlpha2Code());
			if (country.getAlpha3Code().isPresent())
			{
				countryDocument.append("alpha3Code", country.getAlpha3Code().get());
			}
			if (country.getNumericCode().isPresent())
			{
				countryDocument.append("numericCode", country.getNumericCode().get());
			}
			if (country.getDomain().isPresent())
			{
				countryDocument.append("domain", country.getDomain().get());
			}

			countriesCollection.insertOne(countryDocument);
		}
		else // Relational database
		{
			String sql = "INSERT INTO countries (country_name, country_alpha2Code, country_alpha3Code, country_numericCode, country_domain)"
					+ " VALUES (?,?,?,?,?)";

			try (Connection connection = (Connection) db.use();
					PreparedStatement pst = connection.prepareStatement(sql);)
			{
				pst.setString(1, country.getName());
				pst.setString(2, country.getAlpha2Code());
				pst.setString(3, country.getAlpha3Code().isPresent() ? country.getAlpha3Code().get() : null);
				pst.setString(4, country.getNumericCode().isPresent() ? country.getNumericCode().get() : null);
				pst.setString(5, country.getDomain().isPresent() ? country.getDomain().get() : null);
				pst.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static Country getCountry(DatabaseAdapter<?> db, String alpha2Code)
	{
		Country country = null;

		if (db instanceof MongoDbAdapter)
		{
			MongoDatabase mongoDb = (MongoDatabase) db.use();
			MongoCollection<Document> col = mongoDb.getCollection(DatabaseCollections.COUNTRIES.getName());
			Document doc = col.find(Filters.eq("alpha2Code", alpha2Code)).first();
			country = new Country(doc.getString("name"), doc.getString("alpha2Code"));

			country.setAlpha3Code(Optional.ofNullable(doc.getString("alpha3Code")));
			country.setNumericCode(Optional.ofNullable(doc.getString("numericCode")));
			country.setDomain(Optional.ofNullable(doc.getString("domain")));
		}
		else
		{
			String sql = "SELECT country_name, country_alpha2Code, country_alpha3Code, country_numericCode, country_domain FROM countries WHERE country_alpha2Code=?";
			try (Connection connection = (Connection) db.use();
					PreparedStatement pst = connection.prepareStatement(sql);)
			{
				pst.setString(1, alpha2Code);
				ResultSet rs = pst.executeQuery();

				if (rs.next())
				{
					country = new Country(rs.getString("country_name"), rs.getString("country_alpha2Code"));

					country.setAlpha3Code(Optional.ofNullable(rs.getString("country_alpha3Code")));
					country.setNumericCode(Optional.ofNullable(rs.getString("country_numericCode")));
					country.setDomain(Optional.ofNullable(rs.getString("country_domain")));
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return country;
	}
}
