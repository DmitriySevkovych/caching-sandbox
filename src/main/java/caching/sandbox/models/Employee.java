package caching.sandbox.models;

import com.mongodb.lang.NonNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {

	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	@NonNull
	private int age;
	private Address address;
	private double salary;
//	private Department department;

}
