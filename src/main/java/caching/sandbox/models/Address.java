package caching.sandbox.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class Address {
	
	@NonNull private Country country;
	@NonNull private String zipCode;
	@NonNull private String city;
	@NonNull private String street;
	@NonNull private String houseNumber;
	private String appartment;

}
