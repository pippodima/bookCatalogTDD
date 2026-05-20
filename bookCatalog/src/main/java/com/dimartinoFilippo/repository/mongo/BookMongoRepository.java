package com.dimartinoFilippo.repository.mongo;

import java.util.Collections;
import java.util.List;

import org.bson.Document;

import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.BookRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class BookMongoRepository implements BookRepository{

	public static final String DB_NAME = "books";
	public static final String BOOK = "book";

	private MongoCollection<Document> collection;
	
	public BookMongoRepository (MongoClient client) {
		collection = client
				.getDatabase(DB_NAME)
				.getCollection(BOOK);
	}
	
	@Override
	public List<Book> findAll() {
		return Collections.emptyList();
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
