package caching.sandbox.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AddressTest {
	
	@Test
	public void buildAddress() {
		Country deutschland = new Country("DE",  "Deutschland");
		String zipCode = "70374";
		String city = "Stuttgart";
		String street = "Beskidenstr.";
		String houseNumber = "8";
		
		Address addr = Address.builder()
			.country(deutschland)
			.zipCode(zipCode)
			.city(city)
			.street(street)
			.houseNumber(houseNumber)
			.build();
		
		assertNotNull(addr);
		
		String addrLombokToString =  "Address(country=Deutschland (DE), zipCode=70374, city=Stuttgart, street=Beskidenstr., houseNumber=8, appartment=null)";
		assertEquals(addrLombokToString, addr.toString());
	}

	@Test(expected = NullPointerException.class)
	public void whenBuildAddress_forgetObligatoryField() {
		String zipCode = "70374";
		String city = "Stuttgart";
		String street = "Beskidenstr.";
		String houseNumber = "8";
		String appartment = "123";
		
		// NPE should be thrown because Country is missing
		Address.builder()
			.zipCode(zipCode)
			.city(city)
			.street(street)
			.houseNumber(houseNumber)
			.appartment(appartment)
			.build();
	}
}
