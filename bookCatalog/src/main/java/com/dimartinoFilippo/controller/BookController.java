package com.dimartinoFilippo.controller;

import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.AuthorRepository;
import com.dimartinoFilippo.repository.BookRepository;
import com.dimartinoFilippo.view.LibraryView;

public class BookController {
	
	private BookRepository bookRepository;
	private AuthorRepository authorRepository;
	private LibraryView libraryView;

	public BookController(BookRepository bookRepository, AuthorRepository authorRepository, LibraryView libraryView) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.libraryView = libraryView;
	}




	public void findAllBooks() {
		libraryView.showAllBooks(bookRepository.findAll());		
	}




	public void addNewBook(Book bookToAdd) {
		Book existingBook = bookRepository.findByIsbn(bookToAdd.getIsbn());
		if (existingBook != null) {
			libraryView.showErrorBookAlreadyExists("The selected ISBN " + bookToAdd.getIsbn() + " is already in use", existingBook);
			return;
		}
		Author existingAuthor = authorRepository.findById(bookToAdd.getAuthor().getId());
		if (existingAuthor == null) {
			libraryView.showErrorAuthorDoesNotExist("The selected author does not exist", bookToAdd.getAuthor());
			return;
		}
		bookRepository.save(bookToAdd);
		libraryView.newBookAdded(bookToAdd);
		
	}




	public void deleteBook(Book bookToDelete) {
		Book existingBook = bookRepository.findByIsbn(bookToDelete.getIsbn());
		if (existingBook == null) {
			libraryView.showErrorBookDoesNotExist("The selected book does not exist", bookToDelete);
			return;
		}
		bookRepository.delete(bookToDelete);
		libraryView.bookRemoved(bookToDelete);
		
	}

}
