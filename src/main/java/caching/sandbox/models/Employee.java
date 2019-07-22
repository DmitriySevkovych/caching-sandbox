package caching.sandbox.models;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	@NonNull
	private int age;
	private double salary;
//	private Department department;

}
