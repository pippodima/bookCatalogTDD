package com.dimartinoFilippo.repository.mongo;

import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.DB_NAME;
import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.BOOK;
import static org.assertj.core.api.Assertions.assertThat;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class BookMongoRepositoryTestcontainersIT {
	
	private static final Author TEST_AUTHOR = new Author("a1", "Italo", "Calvino");
	private static final Book TEST_BOOK_1 = new Book("1234", "Il Barone Rampante", TEST_AUTHOR, 1957);
	private static final Book TEST_BOOK_2 = new Book("12345", "Il Visconte Dimezzato", TEST_AUTHOR, 1952);

	
	@ClassRule
	public static final MongoDBContainer mongo =
		new MongoDBContainer(DockerImageName.parse("mongo:4.0.5"));

	private MongoClient client;
	private BookMongoRepository bookRepository;
	private MongoCollection<Document> collection;
	
	@Before
	public void setup() {
		client = new MongoClient(
				new ServerAddress(
						mongo.getHost(),
						mongo.getMappedPort(27017)));
		bookRepository = new BookMongoRepository(client);
		MongoDatabase database = client.getDatabase(DB_NAME);
		database.drop();
		collection = database.getCollection(BOOK);
	}
	
	@After
	public void tearDown() {
		client.close();
	}
	
	@Test
	public void testITFindAll() {
		addTestBookToDatabase(TEST_BOOK_1);
		addTestBookToDatabase(TEST_BOOK_2);
		assertThat(bookRepository.findAll()).containsExactly(TEST_BOOK_1, TEST_BOOK_2);
		
	}
	
	private void addTestBookToDatabase(Book book) {
		collection.insertOne(new Document()
				.append("isbn", book.getIsbn())
				.append("title", book.getTitle())
				.append("author", new Document()
						.append("id", book.getAuthor().getId())
						.append("firstName", book.getAuthor().getFirstName())
						.append("lastName", book.getAuthor().getLastName())
						)
				.append("publicationYear", book.getPublicationYear()));
	}

}
