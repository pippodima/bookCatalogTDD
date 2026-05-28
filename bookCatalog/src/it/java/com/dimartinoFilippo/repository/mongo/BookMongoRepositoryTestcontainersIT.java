package com.dimartinoFilippo.repository.mongo;

import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.DB_NAME;
import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.BOOK;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.StreamSupport;

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
	
	@Test
	public void testITFindByIsbn() {
		addTestBookToDatabase(TEST_BOOK_1);
		addTestBookToDatabase(TEST_BOOK_2);
		assertThat(bookRepository.findByIsbn("1234")).isEqualTo(TEST_BOOK_1);
	}
	
	@Test
	public void testITSave() {
		bookRepository.save(TEST_BOOK_1);
		assertThat(readAllBooksFromDatabase()).containsExactly(TEST_BOOK_1);
		
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
				.toList();
	}
	
	@Test
	public void testITDelete () {
		addTestBookToDatabase(TEST_BOOK_1);
		bookRepository.delete(TEST_BOOK_1.getIsbn());
		assertThat(readAllBooksFromDatabase()).isEmpty();
		
	}

	@Test
	public void testITFindBooksByAuthorWhenThereAreNoBooksRelated() {
		addTestBookToDatabase(TEST_BOOK_1);
		assertThat(bookRepository.findByAuthor("b2")).isEmpty();
		
	}
	
	@Test
	public void testITFindBooksByAuthorWhenThereAreBooksRelated() {
		addTestBookToDatabase(TEST_BOOK_1);
		addTestBookToDatabase(TEST_BOOK_2);
		assertThat(bookRepository.findByAuthor("a1")).containsExactly(TEST_BOOK_1, TEST_BOOK_2);
	}



}
