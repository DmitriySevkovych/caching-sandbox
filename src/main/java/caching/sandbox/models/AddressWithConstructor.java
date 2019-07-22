package caching.sandbox.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class AddressWithConstructor {

	@NonNull private Country country;
	@NonNull private String zipCode;
	@NonNull private String city;
	@NonNull private String street;
	@NonNull private String houseNumber;
	@Setter private String appartment;
	
	@Builder
	public AddressWithConstructor(Country country, String zipCode, String city, String street, String houseNumber) {
		if (country == null) {
			throw new IllegalArgumentException("Country is null");
		}	

		if (zipCode.equals("70374")) {
			this.city = "Stuttgart";
		} else {
			this.city = city;
		}

		this.country = country;
		this.zipCode = zipCode;
		this.street = street;
		this.houseNumber = houseNumber;	
	}
	
}
