package com.dimartinoFilippo.repository.mongo;

import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.DB_NAME;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.repository.AuthorRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class AuthorMongoRepository implements AuthorRepository{
	
	public static final String AUTHOR = "authors";

	private MongoCollection<Document> collection;
	
	public AuthorMongoRepository (MongoClient client) {
		collection = client
				.getDatabase(DB_NAME)
				.getCollection(AUTHOR);
	}


	@Override
	public Author findById(String id) {
		Document d = collection.find(Filters.eq("id", id)).first();
		if (d != null) {
			return fromDocumentToAuthor(d);
		}
		return null;
	}

	@Override
	public List<Author> findAll() {
		return StreamSupport.
				stream(collection.find().spliterator(), false)
				.map(this::fromDocumentToAuthor)
				.collect(Collectors.toList());
	}

	@Override
	public void save(Author newAuthor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String idOfauthorToDelete) {
		// TODO Auto-generated method stub
		
	}
	
	private Author fromDocumentToAuthor(Document d) {
		return new Author(
			d.getString("id"),
			d.getString("firstName"),
			d.getString("lastName")
			);
	}


}
