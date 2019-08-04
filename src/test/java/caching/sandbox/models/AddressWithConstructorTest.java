package caching.sandbox.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AddressWithConstructorTest {
	
	@Test
	public void buildAddress() {
		Country deutschland = new Country("DE",  "Deutschland");
		String zipCode = "70374";
		String city = "Stuttgart";
		String street = "Beskidenstr.";
		String houseNumber = "8";
		
		AddressWithConstructor addr = AddressWithConstructor.builder()
			.country(deutschland)
			.zipCode(zipCode)
			.city(city)
			.street(street)
			.houseNumber(houseNumber)
			.build();
		
		addr.setAppartment("123");
		
		assertNotNull(addr);
		
		String addrLombokToString =  "AddressWithConstructor(country=Deutschland (DE), zipCode=70374, city=Stuttgart, street=Beskidenstr., houseNumber=8, appartment=123)";
		assertEquals(addrLombokToString, addr.toString());
	}
	
	public void whenCityNotMatchingZipcode_adjustCity() {
		Country deutschland = new Country("DE",  "Deutschland");
		String zipCode = "70374";
		String city = "Hamburg";
		String street = "Beskidenstr.";
		String houseNumber = "8";
		
		AddressWithConstructor addr = AddressWithConstructor.builder()
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
	
	@Test(expected = IllegalArgumentException.class)
	public void whenBuildAddress_forgetObligatoryField() {
		String zipCode = "70374";
		String city = "Stuttgart";
		String street = "Beskidenstr.";
		String houseNumber = "8";
		
		// NPE should be thrown because Country is missing
		AddressWithConstructor.builder()
			.zipCode(zipCode)
			.city(city)
			.street(street)
			.houseNumber(houseNumber)
			.build();
	}

}
