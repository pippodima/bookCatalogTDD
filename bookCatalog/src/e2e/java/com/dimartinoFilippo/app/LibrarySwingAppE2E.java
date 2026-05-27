package com.dimartinoFilippo.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import javax.swing.JFrame;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.bson.Document;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@RunWith(GUITestRunner.class)
public class LibrarySwingAppE2E extends AssertJSwingJUnitTestCase{
	
	@ClassRule
	public static final MongoDBContainer mongo =
		new MongoDBContainer(DockerImageName.parse("mongo:4.0.5"));

	private static final String AUTHOR_FIXTURE_1_ID        = "a1";
	private static final String AUTHOR_FIXTURE_1_FIRSTNAME = "Italo";
	private static final String AUTHOR_FIXTURE_1_LASTNAME  = "Calvino";
	
	private static final String BOOK_FIXTURE_1_ISBN  = "1234";
	private static final String BOOK_FIXTURE_1_TITLE = "Il Barone Rampante";
	private static final int    BOOK_FIXTURE_1_YEAR  = 1957;
	
	private static final String DB_NAME            = "library";
	private static final String AUTHORS_COLLECTION = "authors";
	private static final String BOOKS_COLLECTION   = "books";
	
	private MongoClient mongoClient;
	private FrameFixture window;
	
	@Override
	protected void onSetUp() throws Exception {
		String host = mongo.getHost();
		Integer port = mongo.getFirstMappedPort();
		
		mongoClient = new MongoClient(new ServerAddress(host, port));
		mongoClient.getDatabase(DB_NAME).drop();
		
		addTestAuthorToDatabase(
				AUTHOR_FIXTURE_1_ID,
				AUTHOR_FIXTURE_1_FIRSTNAME,
				AUTHOR_FIXTURE_1_LASTNAME);
		addTestBookToDatabase(
				BOOK_FIXTURE_1_ISBN,
				BOOK_FIXTURE_1_TITLE,
				AUTHOR_FIXTURE_1_ID,
				BOOK_FIXTURE_1_YEAR);
		
		application("com.dimartinoFilippo.app.LibrarySwingApp")
			.withArgs(
					"--mongo-host=" + host,
					"--mongo-port=" + port.toString()
					).start();
		
		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame frame) {
				return "Library Management".equals(frame.getTitle()) && frame.isShowing();
			}}).using(robot());
	}

	@Override
	protected void onTearDown() throws Exception {
		mongoClient.close();
	}
	
	
	private void addTestAuthorToDatabase(String id, String firstName, String lastName) {
		mongoClient.getDatabase(DB_NAME)
		.getCollection(AUTHORS_COLLECTION)
		.insertOne(new Document()
				.append("id", id)
				.append("firstName", firstName)
				.append("lastName", lastName));
	}

	private void addTestBookToDatabase(String isbn, String title, String authorId, int year) {
		mongoClient.getDatabase(DB_NAME)
		.getCollection(BOOKS_COLLECTION)
		.insertOne(new Document()
				.append("isbn", isbn)
				.append("title", title)
				.append("author", new Document()
						.append("id", authorId)
						.append("firstName", AUTHOR_FIXTURE_1_FIRSTNAME)
						.append("lastName", AUTHOR_FIXTURE_1_LASTNAME))
				.append("publicationYear", year));
	}

	@Test
	@GUITest
	public void testOnStartAllDatabaseElementsAreShown() {
		assertThat(window.list("authorsList").contents())
		.anySatisfy(e -> assertThat(e).contains(
				AUTHOR_FIXTURE_1_ID,
				AUTHOR_FIXTURE_1_FIRSTNAME,
				AUTHOR_FIXTURE_1_LASTNAME));
		assertThat(window.list("booksList").contents())
		.anySatisfy(e -> assertThat(e).contains(
				BOOK_FIXTURE_1_ISBN,
				BOOK_FIXTURE_1_TITLE));
	}
}
