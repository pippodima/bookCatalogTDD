package com.dimartinoFilippo.repository.mongo;

import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.BOOK;
import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.DB_NAME;
import static org.junit.Assert.*;

import java.net.InetSocketAddress;

import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class AuthorMongoRepositoryTest {
	
	private static final Author TEST_AUTHOR = new Author("a1", "Italo", "Calvino");
	private static final Book TEST_BOOK_1 = new Book("1234", "Il Barone Rampante", TEST_AUTHOR, 1957);
	private static final Book TEST_BOOK_2 = new Book("12345", "Il Visconte Dimezzato", TEST_AUTHOR, 1952);

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
	public void test() {
		fail("Not yet implemented");
	}

}
