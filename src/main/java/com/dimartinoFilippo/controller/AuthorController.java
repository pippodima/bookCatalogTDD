package com.dimartinoFilippo.controller;

import com.dimartinoFilippo.repository.AuthorRepository;
import com.dimartinoFilippo.view.LibraryView;

public class AuthorController {
	
	private LibraryView libraryView;
	
	private AuthorRepository authorRepository;
	
	public AuthorController(LibraryView libraryView, AuthorRepository authorRepository) {
		this.libraryView = libraryView;
		this.authorRepository = authorRepository;
	}

	public void findAllAuthors() {
		libraryView.showAllAuthors(authorRepository.findAll());
		
	}

}
