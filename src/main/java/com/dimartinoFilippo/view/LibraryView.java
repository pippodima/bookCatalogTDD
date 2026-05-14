package com.dimartinoFilippo.view;

import java.util.List;

import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;

public interface LibraryView {

	void showAllBooks(List<Book> books);

	void newBookAdded(Book bookToAdd);

	void showErrorBookAlreadyExists(String errorMessage, Book existingBook);

	void showErrorAuthorDoesNotExist(String errorMessage, Author notExistigAuthor);

	Object bookRemoved(Book bookToRemove);

}
