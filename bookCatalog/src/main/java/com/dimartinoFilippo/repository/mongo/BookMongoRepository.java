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
import com.mongodb.client.model.Filters;

public class BookMongoRepository implements BookRepository{

	public static final String DB_NAME = "library";
	public static final String BOOK = "books";

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
		Document d = collection.find(Filters.eq("isbn", isbn)).first();
		if (d != null) {
			return fromDocumentToBook(d);
		}
		return null;
	}

	@Override
	public void save(Book bookToAdd) {
		collection.insertOne(fromBookToDocument(bookToAdd));
	}

	@Override
	public void delete(String isbnOfbookToDelete) {
		collection.deleteOne(Filters.eq("isbn", isbnOfbookToDelete));
		
	}

	@Override
	public List<Book> findByAuthor(String authorId) {
		return StreamSupport
				.stream(collection.find(Filters.eq("author.id", authorId)).spliterator(), false)
				.map(this::fromDocumentToBook)
				.collect(Collectors.toList());
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
	}
	
	private Document fromBookToDocument(Book book) {
		return new Document()
				.append("isbn", book.getIsbn())
				.append("title", book.getTitle())
				.append("author", new Document()
						.append("id", book.getAuthor().getId())
						.append("firstName", book.getAuthor().getFirstName())
						.append("lastName", book.getAuthor().getLastName())
						)
				.append("publicationYear", book.getPublicationYear());
	}
	
}
