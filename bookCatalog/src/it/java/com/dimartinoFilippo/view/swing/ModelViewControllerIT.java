package com.dimartinoFilippo.view.swing;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import com.dimartinoFilippo.controller.AuthorController;
import com.dimartinoFilippo.controller.BookController;
import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.repository.mongo.AuthorMongoRepository;
import com.dimartinoFilippo.repository.mongo.BookMongoRepository;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@RunWith(GUITestRunner.class)
public class ModelViewControllerIT extends AssertJSwingJUnitTestCase{

	@ClassRule
	public static final MongoDBContainer mongo =
		new MongoDBContainer(DockerImageName.parse("mongo:4.0.5"));

	private MongoClient mongoClient;
	private BookMongoRepository bookRepository;
	private AuthorMongoRepository authorRepository;
	private BookController bookController;
	private AuthorController authorController;
	private FrameFixture window;

	@Override
	protected void onSetUp() {
		mongoClient = new MongoClient(
				new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		bookRepository = new BookMongoRepository(mongoClient);
		authorRepository = new AuthorMongoRepository(mongoClient);
		
		for (Book book : bookRepository.findAll()) {
			bookRepository.delete(book.getIsbn());
		}
		for (Author author : authorRepository.findAll()) {
			authorRepository.delete(author.getId());
		}
		LibrarySwingView view = GuiActionRunner.execute(() -> {
			LibrarySwingView v = new LibrarySwingView();
			bookController =
					new BookController(bookRepository, authorRepository, v);
			authorController =
					new AuthorController(v, authorRepository, bookRepository);
			v.setBookController(bookController);
			v.setAuthorController(authorController);
			return v;
		});
		window = new FrameFixture(robot(), view);
		window.show();
	}

	@Override
	protected void onTearDown() {
		mongoClient.close();
	}

}
