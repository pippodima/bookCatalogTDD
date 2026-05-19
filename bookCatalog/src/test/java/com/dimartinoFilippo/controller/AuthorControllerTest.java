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

import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.AuthorRepository;
import com.dimartinoFilippo.repository.BookRepository;
import com.dimartinoFilippo.view.LibraryView;

public class AuthorControllerTest {
	
	private static final Author TEST_AUTHOR = new Author("a1", "Italo", "Calvino");
	private static final Book TEST_BOOK = new Book("1234", "Il Barone Rampante", TEST_AUTHOR, 1957);
	private static final Book TEST_BOOK2 = new Book("12345", "Il Visconte Dimezzato", TEST_AUTHOR, 1952);

	
	@Mock
	private AuthorRepository authorRepository;
	
	@Mock
	private LibraryView libraryView;

	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private AuthorController authorController;
	
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
	public void testFindAllAuthors() {
		List<Author> authors = Arrays.asList(TEST_AUTHOR);
		when(authorRepository.findAll()).thenReturn(authors);
		authorController.findAllAuthors();
		verify(libraryView).showAllAuthors(authors);
		
	}
	
	@Test
	public void testAddNewAuthorWhenIdIsAvailable() {
		when(authorRepository.findById("a1")).thenReturn(null);
		
		authorController.addNewAuthor(TEST_AUTHOR);
		verify(authorRepository).save(TEST_AUTHOR);
		verify(libraryView).newAuthorAdded(TEST_AUTHOR);
		
	}

	@Test
	public void testAddNewAuthorWhenIdAlreadyExist() {
		when(authorRepository.findById("a1")).thenReturn(TEST_AUTHOR);
		
		authorController.addNewAuthor(TEST_AUTHOR);
		verify(libraryView).showErrorAuthorAlreadyExist("The selected id a1 is already assigned to another author", TEST_AUTHOR);
		
	}

	@Test
	public void testDeleteAuthorWhenExistAndThereIsNoRelatedBooks() {
		when(authorRepository.findById("a1")).thenReturn(TEST_AUTHOR);
		
		authorController.deleteAuthor(TEST_AUTHOR);
		
		verify(authorRepository).delete(TEST_AUTHOR);
		verify(libraryView).authorRemoved(TEST_AUTHOR);
		
	}
	
	@Test
	public void testDeleteAuthorWhenExistAndThereIsOneRelatedBook() {
		when(authorRepository.findById("a1")).thenReturn(TEST_AUTHOR);
		when(bookRepository.findByAuthor("a1")).thenReturn(Arrays.asList(TEST_BOOK, TEST_BOOK2));
		
		authorController.deleteAuthor(TEST_AUTHOR);
		
		verify(bookRepository).delete(TEST_BOOK);
		verify(libraryView).bookRemoved(TEST_BOOK);
		
		verify(bookRepository).delete(TEST_BOOK2);
		verify(libraryView).bookRemoved(TEST_BOOK2);

		verify(authorRepository).delete(TEST_AUTHOR);
		verify(libraryView).authorRemoved(TEST_AUTHOR);
	}
	
	@Test
	public void testDeleteAuthorWhenItDoesNotExist() {
		when(authorRepository.findById("a1")).thenReturn(null);
		
		authorController.deleteAuthor(TEST_AUTHOR);
		
		verify(libraryView).showErrorAuthorDoesNotExist("The selected id a1 is not associated with any author", TEST_AUTHOR);
		
	}
	

}
