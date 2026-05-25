package com.dimartinoFilippo.view.swing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dimartinoFilippo.controller.AuthorController;
import com.dimartinoFilippo.controller.BookController;
import com.dimartinoFilippo.model.Author;

@RunWith(GUITestRunner.class)
public class LibrarySwingViewTest extends AssertJSwingJUnitTestCase{

	private AutoCloseable closeable;
	private LibrarySwingView view;
	private FrameFixture window;
	
	@Mock
	private BookController bookController;
	@Mock
	private AuthorController authorController;
	

	@Override
	protected void onSetUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
		GuiActionRunner.execute(() -> {
			view = new LibrarySwingView();
			view.setBookController(bookController);
			view.setAuthorController(authorController);
			return view;
		});
		window = new FrameFixture(robot(), view);
		window.show(new java.awt.Dimension(900, 500));
		
	}
	
	@Override
	public void onTearDown() throws Exception {
		closeable.close();
	}
	
	@Test
	@GUITest
	public void testControlsInitialStates() {
		window.requireTitle("Library Management");
		// author panel
		window.label(JLabelMatcher.withText("Id"));
		window.textBox("idAuthorTextBox").requireEnabled();
		window.label(JLabelMatcher.withText("First name"));
		window.textBox("firstNameTextBox").requireEnabled();
		window.label(JLabelMatcher.withText("Last name"));
		window.textBox("lastNameTextBox").requireEnabled();
		window.button(JButtonMatcher.withText("Add author")).requireDisabled();
		window.list("authorsList").requireEnabled();
		window.button(JButtonMatcher.withText("Delete author")).requireDisabled();
		window.label("errorAuthorLabel").requireText(" ");
		// book panel
		window.label(JLabelMatcher.withText("ISBN"));
		window.textBox("isbnTextBox").requireEnabled();
		window.label(JLabelMatcher.withText("Title"));
		window.textBox("titleTextBox").requireEnabled();
		window.label(JLabelMatcher.withText("Publication year"));
		window.textBox("publicationYearTextBox").requireEnabled();
		window.comboBox("authorComboBox").requireDisabled();
		window.button(JButtonMatcher.withText("Add book")).requireDisabled();
		window.list("booksList").requireEnabled();
		window.button(JButtonMatcher.withText("Delete book")).requireDisabled();
		window.label("errorBookLabel").requireText(" ");
	}
	
	@Test
	@GUITest
	public void testWhenAllAuthorFieldsAreNonEmptyThenAddAuthorButtonShouldBeEnabled() {
		window.textBox("idAuthorTextBox").enterText("a1");
		window.textBox("firstNameTextBox").enterText("Italo");
		window.textBox("lastNameTextBox").enterText("Calvino");
		window.button(JButtonMatcher.withText("Add author")).requireEnabled();
	}
	
	@Test
	@GUITest
	public void testWhenAnyAuthorFieldIsBlankThenAddAuthorButtonShouldBeDisabled() {
		window.textBox("idAuthorTextBox").enterText(" ");
		window.textBox("firstNameTextBox").enterText("Italo");
		window.textBox("lastNameTextBox").enterText("Calvino");
		window.button(JButtonMatcher.withText("Add author")).requireDisabled();

		window.textBox("idAuthorTextBox").setText("");
		window.textBox("firstNameTextBox").setText("");
		window.textBox("lastNameTextBox").setText("");

		window.textBox("idAuthorTextBox").enterText("a1");
		window.textBox("firstNameTextBox").enterText(" ");
		window.textBox("lastNameTextBox").enterText("Calvino");
		window.button(JButtonMatcher.withText("Add author")).requireDisabled();

		window.textBox("idAuthorTextBox").setText("");
		window.textBox("firstNameTextBox").setText("");
		window.textBox("lastNameTextBox").setText("");

		window.textBox("idAuthorTextBox").enterText("a1");
		window.textBox("firstNameTextBox").enterText("Italo");
		window.textBox("lastNameTextBox").enterText(" ");
		window.button(JButtonMatcher.withText("Add author")).requireDisabled();
	}
	
	@Test
	@GUITest
	public void testDeleteAuthorButtonShouldBeEnabledOnlyWhenAnAuthorIsSelected() {
		GuiActionRunner.execute(() -> 
				view.getListAuthorModel()
				.addElement(new Author("a1", "Italo", "Calvino")));
		window.list("authorsList").selectItem(0);
		window.button(JButtonMatcher.withText("Delete author")).requireEnabled();
		window.list("authorsList").clearSelection();
		window.button(JButtonMatcher.withText("Delete author")).requireDisabled();
	}
	
	
	@Test
	@GUITest
	public void testShowAllAuthorsShouldAddAuthorsToList() {
		Author author1 = new Author("a1", "Italo", "Calvino");
		Author author2 = new Author("a2", "Umberto", "Eco");
		GuiActionRunner.execute(() -> 
		view.showAllAuthors(Arrays.asList(author1, author2)));
		assertThat(window.list("authorsList").contents()).containsExactly(author1.toString(), author2.toString());
	}
	
	@Test
	@GUITest
	public void testShowErrorAuthorAlreadyExist() {
		Author author = new Author("a1", "Italo", "Calvino");
		GuiActionRunner.execute(() -> 
		view.showErrorAuthorAlreadyExist("error message", author));
		window.label("errorAuthorLabel").requireText("error message: " + author.toString());
	}

	@Test
	@GUITest
	public void testNewAuthorAddedShouldAddToListAndResetErrorLabel() {
		Author author = new Author("a1", "Italo", "Calvino");
		GuiActionRunner.execute(() -> 
			view.newAuthorAdded(author));
		assertThat(window.list("authorsList").contents()).containsExactly(author.toString());
		window.label("errorAuthorLabel").requireText(" ");
	}
	
	@Test
	@GUITest
	public void testShowErrorAuthorDoesNotExistShouldShowMessageAndRemoveFromList() {
		Author author1 = new Author("a1", "Italo", "Calvino");
		Author author2 = new Author("a2", "Umberto", "Eco");
		GuiActionRunner.execute(() -> {
			view.getListAuthorModel().addElement(author1);
			view.getListAuthorModel().addElement(author2);});
		
		GuiActionRunner.execute(() -> 
			view.showErrorAuthorDoesNotExist("error message", author1));
		window.label("errorAuthorLabel")
			.requireText("error message: " + author1.toString());
		assertThat(window.list("authorsList").contents()).containsExactly(author2.toString());
	}
	
	
	
}
