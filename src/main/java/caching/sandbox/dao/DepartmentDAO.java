package caching.sandbox.dao;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import caching.sandbox.models.Department;

public class DepartmentDAO {

	public static void addDepartment(MongoDatabase db, Department dept) {
		MongoCollection<Document> departmentsCollection = db.getCollection("caching_departments");
		System.out.println("Get collection: " + departmentsCollection.getNamespace().getCollectionName());

		// Check if department already exists
		Bson eqFilter = Filters.eq("name", dept.getName());
		boolean isNew = departmentsCollection.countDocuments(eqFilter) == 0;

		// Add if it doesn't exist
		if (isNew) {
			Document deptDoc = new Document("name", dept.getName()).append("budget", dept.getBudget())
					.append("employees", dept.getEmployees());

			departmentsCollection.insertOne(deptDoc);
			System.out.println("Department " + dept.getName() + " added to collection");
		} else {
			System.out.println("Department " + dept.getName() + " already exists in the departments collection");
		}
	}

}
