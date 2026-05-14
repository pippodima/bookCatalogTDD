package com.dimartinoFilippo.view;

import java.util.List;

import com.dimartinoFilippo.model.Book;

public interface LibraryView {

	void showAllBooks(List<Book> books);

	void newBookAdded(Book bookToAdd);

}
