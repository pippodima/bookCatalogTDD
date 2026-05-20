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
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class BookMongoRepositoryTest {
	
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

}
