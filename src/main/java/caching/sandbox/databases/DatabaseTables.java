package caching.sandbox.databases;

public enum DatabaseTables {
	
	COUNTRIES("countries");
	
	private final String name;
	
	private DatabaseTables(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
