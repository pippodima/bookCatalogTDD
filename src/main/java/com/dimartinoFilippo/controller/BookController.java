package com.dimartinoFilippo.controller;

import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.BookRepository;
import com.dimartinoFilippo.view.LibraryView;

public class BookController {
	
	private BookRepository bookRepository;
	private LibraryView libraryView;

	public BookController(BookRepository bookRepository, LibraryView libraryView) {
		this.bookRepository = bookRepository;
		this.libraryView = libraryView;
	}




	public void findAllBooks() {
		libraryView.showAllBooks(bookRepository.findAll());		
	}




	public void addNewBook(Book testBook) {
		// TODO Auto-generated method stub
		
	}

}
