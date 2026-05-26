package com.dimartinoFilippo.view.swing;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
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
		window.show(new java.awt.Dimension(900, 500));
	}

	@Override
	protected void onTearDown() {
		mongoClient.close();
	}
	
	
	@Test
	@GUITest
	public void testAllAuthors() {
		Author author1 = new Author("a1", "Italo", "Calvino");
		Author author2 = new Author("a2", "Umberto", "Eco");
		authorRepository.save(author1);
		authorRepository.save(author2);
		GuiActionRunner.execute(() -> authorController.findAllAuthors());
		assertThat(window.list("authorsList").contents())
			.containsExactly(author1.toString(), author2.toString());
	}

	@Test
	@GUITest
	public void testAddAuthorSuccess() {
		window.textBox("idAuthorTextBox").enterText("a1");
		window.textBox("firstNameTextBox").enterText("Italo");
		window.textBox("lastNameTextBox").enterText("Calvino");
		window.button(JButtonMatcher.withText("Add author")).click();
		assertThat(window.list("authorsList").contents())
			.containsExactly(new Author("a1", "Italo", "Calvino").toString());
	}
	
	@Test
	@GUITest
	public void testAddAuthorError() {
		authorRepository.save(new Author("a1", "Italo", "Calvino"));
		window.textBox("idAuthorTextBox").enterText("a1");
		window.textBox("firstNameTextBox").enterText("Italo");
		window.textBox("lastNameTextBox").enterText("Calvino");
		window.button(JButtonMatcher.withText("Add author")).click();
		assertThat(window.list("authorsList").contents()).isEmpty();
		window.label("errorAuthorLabel")
			.requireText("The selected id a1 is already assigned to another author: "+ new Author("a1", "Italo", "Calvino"));
	}

	@Test
	@GUITest
	public void testDeleteAuthorSuccess() {
		GuiActionRunner.execute(() ->
			authorController.addNewAuthor(new Author("a1", "Italo", "Calvino")));
		window.list("authorsList").selectItem(0);
		window.button(JButtonMatcher.withText("Delete author")).click();
		assertThat(window.list("authorsList").contents()).isEmpty();
	}

	@Test
	@GUITest
	public void testDeleteAuthorError() {
		Author author = new Author("a1", "Italo", "Calvino");
		GuiActionRunner.execute(() ->
			view.getListAuthorModel().addElement(author));
		window.list("authorsList").selectItem(0);
		window.button(JButtonMatcher.withText("Delete author")).click();
		assertThat(window.list("authorsList").contents()).isEmpty();
		window.label("errorAuthorLabel")
			.requireText("The selected id a1 is not associated with any author: " + author.toString());
	}
	
	
	@Test
	@GUITest
	public void testAllBooks() {
		Author author = new Author("a1", "Italo", "Calvino");
		Book book1 = new Book("1234", "Il Barone Rampante", author, 1957);
		Book book2 = new Book("12345", "Il Visconte Dimezzato", author, 1952);
		authorRepository.save(author);
		bookRepository.save(book1);
		bookRepository.save(book2);
		GuiActionRunner.execute(() -> bookController.findAllBooks());
		assertThat(window.list("booksList").contents())
			.containsExactly(book1.toString(), book2.toString());
	}

	@Test
	@GUITest
	public void testAddBookSuccess() {
		Author author = new Author("a1", "Italo", "Calvino");
		authorRepository.save(author);
		GuiActionRunner.execute(() -> authorController.findAllAuthors());
		window.textBox("isbnTextBox").enterText("1234");
		window.textBox("titleTextBox").enterText("Il Barone Rampante");
		window.textBox("publicationYearTextBox").enterText("1957");
		window.comboBox("authorComboBox").selectItem(0);
		window.button(JButtonMatcher.withText("Add book")).click();
		assertThat(window.list("booksList").contents())
			.containsExactly(
					new Book("1234", "Il Barone Rampante", author, 1957).toString());
	}

	@Test
	@GUITest
	public void testAddBookErrorBookAlreadyExists() {
		Author author = new Author("a1", "Italo", "Calvino");
		Book book = new Book("1234", "Il Barone Rampante", author, 1957);
		authorRepository.save(author);
		bookRepository.save(book);
		GuiActionRunner.execute(() -> {
			authorController.findAllAuthors();
			bookController.findAllBooks();
		});
		window.textBox("isbnTextBox").enterText("1234");
		window.textBox("titleTextBox").enterText("Il Barone Rampante");
		window.textBox("publicationYearTextBox").enterText("1957");
		window.comboBox("authorComboBox").selectItem(0);
		window.button(JButtonMatcher.withText("Add book")).click();
		window.label("errorBookLabel")
			.requireText("The selected ISBN 1234 is already in use: " + book);
	}




}
