package caching.sandbox;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mongodb.client.MongoDatabase;

import caching.sandbox.dao.CountryDAO;
import caching.sandbox.dao.EmployeeDAO;
import caching.sandbox.databases.DatabaseAdapter;
import caching.sandbox.databases.DatabaseTables;
import caching.sandbox.databases.MongoDbAdapter;
import caching.sandbox.models.Country;
import caching.sandbox.models.Employee;

public class DummyData {

	public static void createTableCountries(DatabaseAdapter<?> db)
	{
		if (db instanceof MongoDbAdapter)
		{
			return;
		}

		String sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
				.append(DatabaseTables.COUNTRIES.getName())
				.append(" ( ")
				.append("country_alpha2Code STRING PRIMARY KEY, ")
				.append("country_name STRING NOT NULL, ")
				.append("country_alpha3Code STRING, ")
				.append("country_numericCode STRING, ")
				.append("country_domain STRING")
				.append(" );")
				.toString();

		try (Connection c = (Connection) db.use(); Statement st = c.createStatement())
		{
			st.execute(sql);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static List<Country> createSampleCountries()
	{
		List<Country> countries = new ArrayList<>();

		countries.add(new Country("Germany", "DE", Optional.of("DEU"), Optional.of("276"), Optional.of(".de")));
		countries.add(new Country("Ukraine", "UA", Optional.of("UKR"), Optional.of("804"), Optional.of(".ua")));
		countries.add(
				new Country("Russian Federation", "RU", Optional.of("RUS"), Optional.of("643"), Optional.of(".ru")));
		countries.add(new Country("United States of America", "US", Optional.of("USA"), Optional.of("840"),
				Optional.of(".us")));
		countries.add(new Country("Great Britain", "GB", Optional.of("GBR"), Optional.of("826"), Optional.of(".uk")));

		countries.add(new Country("Portugal", "PT"));
		countries.add(new Country("France", "FR"));
		countries.add(new Country("United Arab Emirates", "AE"));
		countries.add(new Country("Netherlands", "NL"));
		countries.add(new Country("Italy", "IT"));

		return countries;
	}

	public static void addSampleCountriesToDatabase(DatabaseAdapter<?> db, List<Country> countries)
	{
		createTableCountries(db);
		countries.forEach(c -> CountryDAO.addCountry(db, c));
	}

	// public static List<Employee> createSampleEmployees() {
	// List<Employee> employees = new ArrayList<>();
	//
	// employees.add(new Employee("Vasyl", "Sirko", 34, 4500));
	// employees.add(new Employee("Petro", "Petrenko", 45, 3245.5));
	// employees.add(new Employee("Taras", "Tarasov", 67, 5200));
	//
	// return employees;
	// }

	public static void addSampleEmployeesToDatabase(MongoDatabase db, List<Employee> employees)
	{
		employees.forEach(p -> EmployeeDAO.addEmployee(db, p));
	}

}
