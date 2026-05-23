package com.dimartinoFilippo.controller;

import static org.mockito.Mockito.verify;
import static java.util.Arrays.asList;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.mongo.AuthorMongoRepository;
import com.dimartinoFilippo.repository.mongo.BookMongoRepository;
import com.dimartinoFilippo.view.LibraryView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class BookControllerIT {
	
	private static final Author TEST_AUTHOR = new Author("a1", "Italo", "Calvino");
	private static final Book TEST_BOOK_1 = new Book("1234", "Il Barone Rampante", TEST_AUTHOR, 1957);
	private static final Book TEST_BOOK_2 = new Book("12345", "Il Visconte Dimezzato", TEST_AUTHOR, 1952);

	@ClassRule
	public static final MongoDBContainer mongo =
	new MongoDBContainer(DockerImageName.parse("mongo:4.0.5"));

	@Mock
	LibraryView libraryView;
	
	private BookMongoRepository bookRepository;
	private AuthorMongoRepository authorRepository;
	private BookController bookController;
	private AutoCloseable closeable;
	
	@Before
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		MongoClient client = new MongoClient(
				new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		bookRepository = new BookMongoRepository(client);
		authorRepository = new AuthorMongoRepository(client);
		
		for (Book book : bookRepository.findAll()) {
			bookRepository.delete(book.getIsbn());
		}
		
		for (Author author : authorRepository.findAll()) {
			authorRepository.delete(author.getId());
		}
		bookController = new BookController(bookRepository, authorRepository, libraryView);
	}
	
	@After
	public void releaseMocks()throws Exception {
		closeable.close();
	}
	
	@Test
	public void testFindAllBooks() {
		bookRepository.save(TEST_BOOK_1);
		bookRepository.save(TEST_BOOK_2);
		bookController.findAllBooks();
		verify(libraryView).showAllBooks(asList(TEST_BOOK_1, TEST_BOOK_2));
	}
	
	@Test
	public void testAddNewBookWhenAuthorExistAndBookDoesNotExist() {
		authorRepository.save(TEST_AUTHOR);
		bookController.addNewBook(TEST_BOOK_1);
		verify(libraryView).newBookAdded(TEST_BOOK_1);
	}
	
	@Test
	public void testAddNewBookWhenBookAlreadyExists() {
		bookRepository.save(TEST_BOOK_1);
		bookController.addNewBook(TEST_BOOK_1);
		verify(libraryView).showErrorBookAlreadyExists("The selected ISBN " + TEST_BOOK_1.getIsbn() + " is already in use", TEST_BOOK_1);
	}

}
