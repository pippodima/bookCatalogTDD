package com.dimartinoFilippo.repository;

import java.util.List;

import com.dimartinoFilippo.model.Book;

public interface BookRepository {

	List<Book> findAll();

	Book findByIsbn(String isbn);

	void save(Book bookToAdd);

	void delete(String isbnOfbookToDelete);

	List<Book> findByAuthor(String authorId);

}