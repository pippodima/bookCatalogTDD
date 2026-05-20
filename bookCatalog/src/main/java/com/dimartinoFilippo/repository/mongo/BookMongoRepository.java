package com.dimartinoFilippo.repository.mongo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.dimartinoFilippo.model.Author;
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
		return StreamSupport.
				stream(collection.find().spliterator(), false)
				.map(this::fromDocumentToBook)
				.collect(Collectors.toList());
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

	private Book fromDocumentToBook(Document d) {
	    Document authorDoc = (Document) d.get("author");
	    Author author = new Author(
	        authorDoc.getString("id"),
	        authorDoc.getString("firstName"),
	        authorDoc.getString("lastName")
	    );
	    return new Book(
	        d.getString("isbn"),
	        d.getString("title"),
	        author,
	        d.getInteger("publicationYear")
	    );
	}}
