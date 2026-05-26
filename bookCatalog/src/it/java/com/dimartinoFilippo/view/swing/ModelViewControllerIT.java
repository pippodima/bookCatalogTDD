package com.dimartinoFilippo.view.swing;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.ClassRule;
import org.junit.Test;
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
		window.show(new java.awt.Dimension(900, 500));
	}

	@Override
	protected void onTearDown() {
		mongoClient.close();
	}
	
	@Test
	@GUITest
	public void testAddAuthor() {
		window.textBox("idAuthorTextBox").enterText("a1");
		window.textBox("firstNameTextBox").enterText("Italo");
		window.textBox("lastNameTextBox").enterText("Calvino");
		window.button(JButtonMatcher.withText("Add author")).click();
		assertThat(authorRepository.findById("a1"))
			.isEqualTo(new Author("a1", "Italo", "Calvino"));
	}

	@Test
	@GUITest
	public void testDeleteAuthor() {
		authorRepository.save(new Author("a1", "Italo", "Calvino"));
		GuiActionRunner.execute(() -> authorController.findAllAuthors());
		window.list("authorsList").selectItem(0);
		window.button(JButtonMatcher.withText("Delete author")).click();
		assertThat(authorRepository.findById("a1")).isNull();
	}


}
