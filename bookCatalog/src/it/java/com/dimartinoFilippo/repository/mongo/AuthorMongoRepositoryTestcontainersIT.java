package com.dimartinoFilippo.repository.mongo;

import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.DB_NAME;
import static com.dimartinoFilippo.repository.mongo.AuthorMongoRepository.AUTHOR;
import static org.assertj.core.api.Assertions.assertThat;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import com.dimartinoFilippo.model.Author;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AuthorMongoRepositoryTestcontainersIT {
	
	private static final Author TEST_AUTHOR_1 = new Author("a1", "Italo", "Calvino");
	private static final Author TEST_AUTHOR_2 = new Author("a2", "Umberto", "Eco");

	@ClassRule
	public static final MongoDBContainer mongo =
		new MongoDBContainer(DockerImageName.parse("mongo:4.0.5"));

	private MongoClient client;
	private AuthorMongoRepository authorRepository;
	private MongoCollection<Document> collection;

	@Before
	public void setup() {
		client = new MongoClient(
				new ServerAddress(
						mongo.getHost(),
						mongo.getMappedPort(27017)));
		authorRepository = new AuthorMongoRepository(client);
		MongoDatabase database = client.getDatabase(DB_NAME);
		database.drop();
		collection = database.getCollection(AUTHOR);
	}

	@After
	public void tearDown() {
		client.close();
	}
	
	@Test
	public void testITFindAll() {
		addTestAuthorToDatabase(TEST_AUTHOR_1);
		addTestAuthorToDatabase(TEST_AUTHOR_2);
		assertThat(authorRepository.findAll()).containsExactly(TEST_AUTHOR_1, TEST_AUTHOR_2);
	}

	@Test
	public void testITFindById() {
		addTestAuthorToDatabase(TEST_AUTHOR_1);
		addTestAuthorToDatabase(TEST_AUTHOR_2);
		assertThat(authorRepository.findById("a2")).isEqualTo(TEST_AUTHOR_2);
	}
	
	
	private void addTestAuthorToDatabase(Author author) {
		collection.insertOne(new Document()
				.append("id", author.getId())
				.append("firstName", author.getFirstName())
				.append("lastName", author.getLastName()));
	}



}
