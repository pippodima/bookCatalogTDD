package com.dimartinoFilippo.controller;

import java.util.List;

import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.AuthorRepository;
import com.dimartinoFilippo.repository.BookRepository;
import com.dimartinoFilippo.view.LibraryView;

public class AuthorController {
	
	private LibraryView libraryView;
	
	private AuthorRepository authorRepository;
	
	private BookRepository bookRepository;
	
	public AuthorController(LibraryView libraryView, AuthorRepository authorRepository, BookRepository bookRepository) {
		this.libraryView = libraryView;
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
	}

	public void findAllAuthors() {
		libraryView.showAllAuthors(authorRepository.findAll());
		
	}

	public void addNewAuthor(Author authorToAdd) {
		Author existingAuthor = authorRepository.findById(authorToAdd.getId());
		if (existingAuthor != null) {
			libraryView.showErrorAuthorAlreadyExist("The selected id " + authorToAdd.getId() + " is already assigned to another author", existingAuthor);
			return;
		}
		authorRepository.save(authorToAdd);
		libraryView.newAuthorAdded(authorToAdd);
		
	}

	public void deleteAuthor(Author authorToDelete) {
		Author existingAuthor = authorRepository.findById(authorToDelete.getId());
		if (existingAuthor == null) {
			libraryView.showErrorAuthorDoesNotExist("The selected id " + authorToDelete.getId() + " is not associated with any author", authorToDelete);
			return;
		}
		List<Book> authorsBooks = bookRepository.findByAuthor(authorToDelete.getId());
		for (Book book : authorsBooks) {
			bookRepository.delete(book);
			libraryView.bookRemoved(book);
		}
		authorRepository.delete(authorToDelete);
		libraryView.authorRemoved(authorToDelete);
		
	}

}
