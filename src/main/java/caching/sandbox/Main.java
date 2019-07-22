package caching.sandbox;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import caching.sandbox.dao.DepartmentDAO;
import caching.sandbox.dao.EmployeeDAO;
import caching.sandbox.models.Department;
import caching.sandbox.models.Employee;

public class Main {
	

	public static void main(String[] args) {

		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		MongoClientSettings settings = MongoClientSettings.builder()
		        .codecRegistry(pojoCodecRegistry)
		        .build();
		
		MongoClient mongoClient = MongoClients.create(settings);
		System.out.println("MongoClient instantiated: connection to MongoDB server OK!");
		
		MongoDatabase sandboxDb = mongoClient.getDatabase("sandbox");
		System.out.println("Get database: " + sandboxDb.getName());
		
		// Employees dummy data
		List<Employee> programmers = new ArrayList<>();
		
		programmers.add(new Employee("Vasyl", "Sirko", 34, 4500));
		programmers.add(new Employee("Petro", "Petrenko", 45, 3245.5));
		programmers.add(new Employee("Taras", "Tarasov", 67, 5200));
		
		programmers.forEach( p -> EmployeeDAO.addEmployee(sandboxDb, p));
		
		// Departments dummy data
		Department itDepartment = new Department("IT", 100000, programmers);
		Department hrDepartment = new Department("HR", 50000, new ArrayList<Employee>());
		Department fcDepartment = new Department("F&C", 45000, new ArrayList<Employee>());
		
		DepartmentDAO.addDepartment(sandboxDb,itDepartment);
		DepartmentDAO.addDepartment(sandboxDb,hrDepartment);
		DepartmentDAO.addDepartment(sandboxDb,fcDepartment);
	}
}
