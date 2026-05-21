package com.dimartinoFilippo.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;
import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.DB_NAME;
import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.BOOK;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
	public void testFindAllWhenDatabaseIsEmpty() {
		assertThat(bookRepository.findAll()).isEmpty();
	}

	@Test
	public void testFindAllWhenDatabaseIsNotEmpty() {
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
	
	@Test
	public void testFindByIsbnWhenIsbnIsNotPresent() {
		assertThat(bookRepository.findByIsbn("id-not-present")).isNull();
	}
	
	@Test
	public void testFindByIsbnWhenIsbnIsPresent() {
		addTestBookToDatabase(TEST_BOOK_1);
		addTestBookToDatabase(TEST_BOOK_2);
		assertThat(bookRepository.findByIsbn("12345")).isEqualTo(TEST_BOOK_2);
	}
	
	@Test
	public void testSave() {
		bookRepository.save(TEST_BOOK_1);
		assertThat(readAllBooksFromDatabase()).containsExactly(TEST_BOOK_1);
	}
	
	
	@Test
	public void testDelete() {
		addTestBookToDatabase(TEST_BOOK_1);
		bookRepository.delete(TEST_BOOK_1.getIsbn());
		assertThat(readAllBooksFromDatabase()).isEmpty();
	}
	
	
	private List<Book> readAllBooksFromDatabase() {
		return StreamSupport
				.stream(collection.find().spliterator(), false)
				.map(d -> {
					Document authorDoc = (Document) d.get("author");
					Author author = new Author(
							authorDoc.getString("id"),
							authorDoc.getString("firstName"),
							authorDoc.getString("lastName")
							);
					return new Book(
							d.getString("isbn"),
							d.getString("title"),
							author,
							d.getInteger("publicationYear")
							);
				})
				.collect(Collectors.toList());
	}
	
	@Test
	public void testFindBooksByAuthorWhenThereAreNoBooksRelated() {
		addTestBookToDatabase(TEST_BOOK_1);
		assertThat(bookRepository.findByAuthor("b2")).isEmpty();;
		
	}

	
	@Test
	public void testFindBooksByAuthorWhenThereAreBooksRelated() {
		addTestBookToDatabase(TEST_BOOK_1);
		addTestBookToDatabase(TEST_BOOK_2);
		assertThat(bookRepository.findByAuthor("a1")).containsExactly(TEST_BOOK_1, TEST_BOOK_2);
	}
}
