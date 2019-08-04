package caching.sandbox.caches;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import caching.sandbox.dao.CountryDAO;
import caching.sandbox.databases.DatabaseAdapter;
import caching.sandbox.models.Country;

public class GuavaCountryCache {
	private final LoadingCache<String, Country> guavaCache;
	private int countDbAccesses;

	public GuavaCountryCache(DatabaseAdapter<?> db)
	{
		guavaCache = CacheBuilder.newBuilder().maximumSize(5).expireAfterWrite(2, TimeUnit.MINUTES)
				.build(new CountryCacheLoader(db));
	}

	public Optional<Country> getCountry(String alpha2Code)
	{
		try
		{
			System.out.println("===== New request to GuavaCountryCache for: " + alpha2Code + " =====");
			System.out.println("Guava Cache request counter before request:" + countDbAccesses);
			
			Optional<Country> country = Optional.ofNullable(guavaCache.get(alpha2Code));
			
			System.out.println("Guava Cache request counter after request:" + countDbAccesses);
			System.out.println("===== ===== =====");
			
			return country;
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
			return Optional.empty();
		}
	}

	/*
	 * Inner class extends CacheLoader
	 */
	protected class CountryCacheLoader extends CacheLoader<String, Country> {
		private final DatabaseAdapter<?> db;

		public CountryCacheLoader(DatabaseAdapter<?> db)
		{
			this.db = db;
		}

		@Override
		public Country load(String key) throws Exception
		{
			countDbAccesses++;
			return CountryDAO.getCountry(db, key);
		}
	}
}
