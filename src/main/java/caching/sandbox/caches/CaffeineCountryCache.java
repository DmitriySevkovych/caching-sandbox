package caching.sandbox.caches;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import caching.sandbox.dao.CountryDAO;
import caching.sandbox.databases.DatabaseAdapter;
import caching.sandbox.models.Country;

public class CaffeineCountryCache {

	private final DatabaseAdapter<?> db;
	private final Cache<String, Country> caffeineCache;
	private int countDbAccesses;

	public CaffeineCountryCache(DatabaseAdapter<?> db)
	{
		this.db = db;
		caffeineCache = Caffeine.newBuilder().maximumSize(3).expireAfterWrite(1, TimeUnit.MINUTES).build();
	}

	public Optional<Country> getCountry(String alpha2Code)
	{
		System.out.println("~~~~~ New request to CaffeineCountryCache for: " + alpha2Code + " ~~~~~");
		System.out.println("Number of Caffeine cache database accesses before request:" + countDbAccesses);

		Optional<Country> country = Optional.ofNullable(caffeineCache.get(alpha2Code, key -> {
			countDbAccesses++;
			return CountryDAO.getCountry(this.db, alpha2Code);
		}));

		System.out.println("Number of Caffeine cache database accesses after request:" + countDbAccesses);
		System.out.println("~~~~~ ~~~~~ ~~~~~");

		return country;
	}
}
