package com.dimartinoFilippo.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.BookRepository;
import com.dimartinoFilippo.view.LibraryView;

public class BookControllerTest {
	
	private static final Book TEST_BOOK = new Book();

	@Mock
	private BookRepository bookRepository;
	
	@Mock
	private LibraryView libraryView;
	
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
	
	

}