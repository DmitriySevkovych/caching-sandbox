package caching.sandbox.models;

import lombok.NonNull;
import lombok.Value;

@Value
public class Country {

	@NonNull private String isoCode;
	@NonNull private String name;
	
	@Override
	public String toString()
	{
		return this.name + " (" + this.isoCode + ")";
	}
}
