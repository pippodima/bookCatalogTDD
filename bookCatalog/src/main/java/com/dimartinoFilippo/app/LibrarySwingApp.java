package com.dimartinoFilippo.app;

import java.awt.EventQueue;
import com.dimartinoFilippo.controller.AuthorController;
import com.dimartinoFilippo.controller.BookController;
import com.dimartinoFilippo.repository.mongo.AuthorMongoRepository;
import com.dimartinoFilippo.repository.mongo.BookMongoRepository;
import com.dimartinoFilippo.view.swing.LibrarySwingView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class LibrarySwingApp {

	public static void main(String[] args) {
		String host = System.getProperty("mongo.host", "localhost");
		int port = Integer.parseInt(
				System.getProperty("mongo.port", "27017"));

		MongoClient client = new MongoClient(new ServerAddress(host, port));
		BookMongoRepository bookRepository = new BookMongoRepository(client);
		AuthorMongoRepository authorRepository = new AuthorMongoRepository(client);

		EventQueue.invokeLater(() -> {
			LibrarySwingView view = new LibrarySwingView();
			BookController bookController =
					new BookController(bookRepository, authorRepository, view);
			AuthorController authorController =
					new AuthorController(view, authorRepository, bookRepository);
			view.setBookController(bookController);
			view.setAuthorController(authorController);
			view.setVisible(true);
			
			authorController.findAllAuthors();
			bookController.findAllBooks();
		});
	}}