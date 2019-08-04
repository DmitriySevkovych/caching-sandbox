package caching.sandbox.databases;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbAdapter implements DatabaseAdapter<MongoDatabase> { 
	
	private final MongoDatabase database;

	public MongoDbAdapter(String databaseName) {
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(
						PojoCodecProvider.builder().automatic(true).build()));

		MongoClientSettings settings = MongoClientSettings.builder()
				.codecRegistry(pojoCodecRegistry).build();

		MongoClient mongoClient = MongoClients.create(settings);
		System.out.println(
				"MongoClient instantiated: connection to MongoDB server OK!");

		this.database = mongoClient.getDatabase(databaseName);
		System.out.println("Get database: " + database.getName());
	}

	@Override
	public MongoDatabase use() {
		return this.database;
	}

}
