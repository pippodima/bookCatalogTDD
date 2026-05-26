package com.dimartinoFilippo.view.swing;

import java.net.InetSocketAddress;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.dimartinoFilippo.controller.AuthorController;
import com.dimartinoFilippo.controller.BookController;
import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.mongo.AuthorMongoRepository;
import com.dimartinoFilippo.repository.mongo.BookMongoRepository;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

@RunWith(GUITestRunner.class)
public class LibrarySwingViewIT extends AssertJSwingJUnitTestCase{

	private static MongoServer server;
	private static InetSocketAddress serverAddress;

	@BeforeClass
	public static void setupServer() {
		server = new MongoServer(new MemoryBackend());
		serverAddress = server.bind();
	}

	@AfterClass
	public static void shutdownServer() {
		server.shutdown();
	}

	private MongoClient mongoClient;
	private BookMongoRepository bookRepository;
	private AuthorMongoRepository authorRepository;
	private LibrarySwingView view;
	private BookController bookController;
	private AuthorController authorController;
	private FrameFixture window;

	@Override
	protected void onSetUp() {
		mongoClient = new MongoClient(new ServerAddress(serverAddress));
		bookRepository = new BookMongoRepository(mongoClient);
		authorRepository = new AuthorMongoRepository(mongoClient);
		
		for (Book book : bookRepository.findAll()) {
			bookRepository.delete(book.getIsbn());
		}
		for (Author author : authorRepository.findAll()) {
			authorRepository.delete(author.getId());
		}
		GuiActionRunner.execute(() -> {
			view = new LibrarySwingView();
			bookController =
					new BookController(bookRepository, authorRepository, view);
			authorController =
					new AuthorController(view, authorRepository, bookRepository);
			view.setBookController(bookController);
			view.setAuthorController(authorController);
			return view;
		});
		window = new FrameFixture(robot(), view);
		window.show();
	}

	@Override
	protected void onTearDown() {
		mongoClient.close();
	}



}
