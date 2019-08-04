package caching.sandbox.databases;

public enum DatabaseCollections {
	
	COUNTRIES("caching_countries");
	
	private final String name;
	
	private DatabaseCollections(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
