package caching.sandbox.models;

import java.util.List;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
public class Department {
	
	private String name;
	private double budget;
	@NonFinal private List<Employee> employees;

}
