package caching.sandbox.dao;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import caching.sandbox.models.Employee;

public class EmployeeDAO {

	public static void addEmployee(MongoDatabase db, Employee empl) {
		// Check if employee exists (pseudo check rather, firstName + lastName is not
		// necessarily unique...)
		MongoCollection<Document> employeesCollection = db.getCollection("caching_employees");
		System.out.println("Get collection: " + employeesCollection.getNamespace().getCollectionName());

		Document emplDoc;
		Bson emplFilter = Filters.and(Filters.eq("firstName", empl.getFirstName()),
				Filters.eq("lastName", empl.getLastName()));
		boolean isNew = employeesCollection.countDocuments(emplFilter) == 0;

		if (isNew) {
			// Add new employee to collection
			emplDoc = new Document("firstName", empl.getFirstName()).append("lastName", empl.getLastName())
					.append("age", empl.getAge()).append("salary", empl.getSalary());

			employeesCollection.insertOne(emplDoc);
			System.out.println("Employee " + empl.getLastName() + " added to collection");
		} else {
			System.out.println("Employee " + empl.getLastName() + " already exists in the employees collection");
			emplDoc = employeesCollection.find(emplFilter).first();
		}

		System.out.println(emplDoc.toJson());
		System.out.println("=====");
	}
}
