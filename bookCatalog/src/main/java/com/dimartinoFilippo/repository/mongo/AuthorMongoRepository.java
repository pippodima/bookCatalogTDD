package com.dimartinoFilippo.repository.mongo;

import static com.dimartinoFilippo.repository.mongo.BookMongoRepository.DB_NAME;

import java.util.List;

import org.bson.Document;

import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.repository.AuthorRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Author newAuthor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Author authorToDelete) {
		// TODO Auto-generated method stub
		
	}

}
