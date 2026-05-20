package com.dimartinoFilippo.repository.mongo;

import java.util.List;

import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.BookRepository;

public class BookMongoRepository implements BookRepository{

	@Override
	public List<Book> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book findByIsbn(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Book bookToAdd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Book bookToDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Book> findByAuthor(String authorId) {
		// TODO Auto-generated method stub
		return null;
	}

}
