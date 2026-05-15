package com.dimartinoFilippo.controller;

import com.dimartinoFilippo.model.Author;
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
		// TODO Auto-generated method stub
		
	}

}
