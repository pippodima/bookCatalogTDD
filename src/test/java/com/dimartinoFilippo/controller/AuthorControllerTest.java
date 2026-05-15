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
import com.dimartinoFilippo.repository.AuthorRepository;
import com.dimartinoFilippo.view.LibraryView;

public class AuthorControllerTest {
	
	private static final Author TEST_AUTHOR = new Author("a1", "Italo", "Calvino");
	
	@Mock
	private AuthorRepository authorRepository;

	@Mock
	private LibraryView libraryView;
	
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
	
	

}
