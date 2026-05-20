package com.dimartinoFilippo.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.DB_NAME;
import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.BOOK;

import java.net.InetSocketAddress;

import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.ServerAddress;
import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class BookMongoRepositoryTest {
	
	private static final Author TEST_AUTHOR = new Author("a1", "Italo", "Calvino");

	
	private static MongoServer server;
	private static InetSocketAddress serverAddress;
	
	private MongoClient client;
	private BookMongoRepository bookRepository;
	private MongoCollection<Document> collection;
	
	
	@BeforeClass
	public static void setupServer() {
		server = new MongoServer(new MemoryBackend());
		serverAddress = server.bind();
	}
	
	@AfterClass
	public static void shutDownServer() {
		server.shutdown();
	}
	
	@Before
	public void setup() {
        client = new MongoClient(new ServerAddress(serverAddress));
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
	public void testFindAllWhenDatabaseIsEmpty() {
		assertThat(bookRepository.findAll()).isEmpty();
	}

	@Test
	public void testFindAllWhenDatabaseIsNotEmpty() {
		addTestBookToDatabase("1234", "Il Barone Rampante", TEST_AUTHOR, 1957);
		addTestBookToDatabase("12345", "Il Visconte Dimezzato", TEST_AUTHOR, 1952);
		
		assertThat(bookRepository.findAll()).containsExactly(
				new Book("1234", "Il Barone Rampante", TEST_AUTHOR, 1957),
				new Book("12345", "Il Visconte Dimezzato", TEST_AUTHOR, 1952)
				);
	}

	private void addTestBookToDatabase(String isbn, String title, Author author, int publicationYear) {
		collection.insertOne(new Document()
				.append("isbn", isbn)
				.append("title", title)
				.append("author", new Document()
						.append("id", author.getId())
						.append("firstName", author.getFirstName())
						.append("lastName", author.getLastName())
						)
				.append("publicationYear", publicationYear));
	}
}
