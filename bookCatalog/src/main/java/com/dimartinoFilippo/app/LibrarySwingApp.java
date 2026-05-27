package com.dimartinoFilippo.app;

import java.awt.EventQueue;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import com.dimartinoFilippo.controller.AuthorController;
import com.dimartinoFilippo.controller.BookController;
import com.dimartinoFilippo.repository.mongo.AuthorMongoRepository;
import com.dimartinoFilippo.repository.mongo.BookMongoRepository;
import com.dimartinoFilippo.view.swing.LibrarySwingView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@Command(mixinStandardHelpOptions = true)
public class LibrarySwingApp implements Callable<Void> {

	@Option(names = {"--mongo-host"}, description = "MongoDB host address")
	private String mongoHost = "localhost";

	@Option(names = {"--mongo-port"}, description = "MongoDB host port")
	private int mongoPort = 27017;

	public static void main(String[] args) {
		new CommandLine(new LibrarySwingApp()).execute(args);
	}

	@Override
	public Void call() throws Exception {
		EventQueue.invokeLater(() -> {
			try {
				MongoClient client = new MongoClient(new ServerAddress(mongoHost, mongoPort));
				AuthorMongoRepository authorRepository = new AuthorMongoRepository(client);
				BookMongoRepository bookRepository = new BookMongoRepository(client);
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return null;
	}
}