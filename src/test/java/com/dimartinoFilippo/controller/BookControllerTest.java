package com.dimartinoFilippo.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.AuthorRepository;
import com.dimartinoFilippo.repository.BookRepository;
import com.dimartinoFilippo.view.LibraryView;

public class BookControllerTest {
	
	private static final Author TEST_AUTHOR = new Author("a1", "Italo", "Calvino");
	private static final Book TEST_BOOK = new Book("1234", "Il Barone Rampante", TEST_AUTHOR, 1957);

	@Mock
	private BookRepository bookRepository;
	
	@Mock
	private LibraryView libraryView;
	
	@Mock
	private AuthorRepository authorRepository;
	
	@InjectMocks
	private BookController bookController;
	
	private AutoCloseable closeable;
	
	@Before
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
	}
	
	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}
	
	
	
	@Test
	public void testFindAllBooks() {
		List<Book> books = Arrays.asList(TEST_BOOK);
		when(bookRepository.findAll()).thenReturn(books);
		bookController.findAllBooks();
		verify(libraryView).showAllBooks(books);
	}
	
	@Test
	public void testAddNewBookWhenIsbnIsAvailableAndAuthorExist() {
		when(bookRepository.findByIsbn("1234")).thenReturn(null);
		when(authorRepository.findById("a1")).thenReturn(TEST_AUTHOR);
	
		bookController.addNewBook(TEST_BOOK);
	
		verify(bookRepository).save(TEST_BOOK);
		verify(libraryView).newBookAdded(TEST_BOOK);
	}
	
	@Test
	public void testAddNewBookWhenIsbnAlreadyExist() {
		when(bookRepository.findByIsbn("1234")).thenReturn(TEST_BOOK);
		
		bookController.addNewBook(TEST_BOOK);
		
		verify(libraryView).showErrorBookAlreadyExists("The selected ISBN 1234 is already in use", TEST_BOOK);
		verifyNoInteractions(authorRepository);
	}
	
	@Test
	public void testAddNewBookWhenAuthorDoesNotExist() {
		when(bookRepository.findByIsbn("1234")).thenReturn(null);
		when(authorRepository.findById("a1")).thenReturn(null);
	
		bookController.addNewBook(TEST_BOOK);

		verify(libraryView).showErrorAuthorDoesNotExist("The selected author does not exist", TEST_AUTHOR);
	}
	
	@Test
	public void testDeleteBookWhenBookExist() {
		when(bookRepository.findByIsbn("1234")).thenReturn(TEST_BOOK);
		
		bookController.deleteBook(TEST_BOOK);
		
		verify(bookRepository.delete(TEST_BOOK));
		verify(libraryView.bookRemoved(TEST_BOOK));
	}
	
	
}