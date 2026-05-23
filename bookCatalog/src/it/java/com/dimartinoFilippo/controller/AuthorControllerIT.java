package com.dimartinoFilippo.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.MongoDBContainer;

import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.mongo.AuthorMongoRepository;
import com.dimartinoFilippo.repository.mongo.BookMongoRepository;
import com.dimartinoFilippo.view.LibraryView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;


public class AuthorControllerIT {
	
	private static final Author TEST_AUTHOR_1 = new Author("a1", "Italo", "Calvino");
	private static final Author TEST_AUTHOR_2 = new Author("a2", "Umberto", "Eco");
	private static final Book TEST_BOOK_1 = new Book("1234", "Il Barone Rampante", TEST_AUTHOR_1, 1957);
	private static final Book TEST_BOOK_2 = new Book("12345", "Il Visconte Dimezzato", TEST_AUTHOR_1, 1952);

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.0.5");

	@Mock
	private LibraryView libraryView;

	private AuthorMongoRepository authorRepository;
	private BookMongoRepository bookRepository;
	private AuthorController authorController;
	private AutoCloseable closeable;
	
	@Before
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		MongoClient client = new MongoClient(
				new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		authorRepository = new AuthorMongoRepository(client);
		bookRepository = new BookMongoRepository(client);
		
		for (Book book : bookRepository.findAll()) {
			bookRepository.delete(book.getIsbn());
		}
		for (Author author : authorRepository.findAll()) {
			authorRepository.delete(author.getId());
		}
		authorController = new AuthorController(libraryView, authorRepository, bookRepository);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

}
