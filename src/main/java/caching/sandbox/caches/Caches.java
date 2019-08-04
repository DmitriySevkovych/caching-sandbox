package caching.sandbox.caches;

import caching.sandbox.databases.DatabaseAdapter;
import lombok.Getter;

public class Caches {
	@Getter
	private static GuavaCountryCache guavaCountryCache;
	@Getter
	private static CaffeineCountryCache caffeineCountryCache;

	public static void initCaches(DatabaseAdapter<?> db)
	{
		guavaCountryCache = new GuavaCountryCache(db);
		caffeineCountryCache = new CaffeineCountryCache(db);
	}

}
