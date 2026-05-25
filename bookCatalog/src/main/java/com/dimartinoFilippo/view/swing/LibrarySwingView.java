package com.dimartinoFilippo.view.swing;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.dimartinoFilippo.controller.AuthorController;
import com.dimartinoFilippo.controller.BookController;
import com.dimartinoFilippo.model.Author;
import com.dimartinoFilippo.model.Book;
import com.dimartinoFilippo.view.LibraryView;

public class LibrarySwingView extends JFrame implements LibraryView{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private BookController bookController;
	private AuthorController authorController;
	
	private JTextField txtIdAuthor;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JButton btnAddAuthor;
	private JList<Author> listAuthors;
	private DefaultListModel<Author> listAuthorModel;
	private JButton btnDeleteAuthor;
	private JLabel lblErrorAuthor;

	private JTextField txtIsbn;
	private JTextField txtTitle;
	private JTextField txtPublicationYear;
	private JComboBox<Author> cmbAuthor;
	private DefaultComboBoxModel<Author> authorComboBoxModel;
	private JButton btnAddBook;
	private JList<Book> listBooks;
	private DefaultListModel<Book> listBookModel;
	private JButton btnDeleteBook;
	private JLabel lblErrorBook;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibrarySwingView frame = new LibrarySwingView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LibrarySwingView() {
		setTitle("Library Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel(new GridLayout(1, 2, 10, 0));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		
		contentPane.add(buildAuthorPanel());
		contentPane.add(buildBookPanel());
	}
	
	private JPanel buildAuthorPanel() {
		JPanel panel = new JPanel(null);
		panel.setBorder(new TitledBorder("Authors"));
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(10, 30, 80, 20);
		panel.add(lblId);

		txtIdAuthor = new JTextField();
		txtIdAuthor.setName("idAuthorTextBox");
		txtIdAuthor.setBounds(100, 30, 180, 20);
		panel.add(txtIdAuthor);

		JLabel lblFirstName = new JLabel("First name");
		lblFirstName.setBounds(10, 60, 80, 20);
		panel.add(lblFirstName);

		txtFirstName = new JTextField();
		txtFirstName.setName("firstNameTextBox");
		txtFirstName.setBounds(100, 60, 180, 20);
		panel.add(txtFirstName);

		JLabel lblLastName = new JLabel("Last name");
		lblLastName.setBounds(10, 90, 80, 20);
		panel.add(lblLastName);

		txtLastName = new JTextField();
		txtLastName.setName("lastNameTextBox");
		txtLastName.setBounds(100, 90, 180, 20);
		panel.add(txtLastName);

		btnAddAuthor = new JButton("Add author");
		btnAddAuthor.setEnabled(false);
		btnAddAuthor.setBounds(10, 120, 270, 25);
		panel.add(btnAddAuthor);

		listAuthorModel = new DefaultListModel<>();
		listAuthors = new JList<>(listAuthorModel);
		listAuthors.setName("authorsList");
		listAuthors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAuthors.setBounds(10, 155, 270, 160);
		panel.add(listAuthors);

		btnDeleteAuthor = new JButton("Delete author");
		btnDeleteAuthor.setEnabled(false);
		btnDeleteAuthor.setBounds(10, 325, 270, 25);
		panel.add(btnDeleteAuthor);

		lblErrorAuthor = new JLabel(" ");
		lblErrorAuthor.setName("errorAuthorLabel");
		lblErrorAuthor.setBounds(10, 360, 270, 20);
		panel.add(lblErrorAuthor);

		return panel;

	}
	
	private JPanel buildBookPanel() {
		JPanel panel = new JPanel(null);
		panel.setBorder(new TitledBorder("Books"));

		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(10, 30, 80, 20);
		panel.add(lblIsbn);

		txtIsbn = new JTextField();
		txtIsbn.setName("isbnTextBox");
		txtIsbn.setBounds(100, 30, 180, 20);
		panel.add(txtIsbn);

		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(10, 60, 80, 20);
		panel.add(lblTitle);

		txtTitle = new JTextField();
		txtTitle.setName("titleTextBox");
		txtTitle.setBounds(100, 60, 180, 20);
		panel.add(txtTitle);

		JLabel lblYear = new JLabel("Publication year");
		lblYear.setBounds(10, 90, 110, 20);
		panel.add(lblYear);

		txtPublicationYear = new JTextField();
		txtPublicationYear.setName("publicationYearTextBox");
		txtPublicationYear.setBounds(130, 90, 150, 20);
		panel.add(txtPublicationYear);

		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(10, 120, 80, 20);
		panel.add(lblAuthor);

		authorComboBoxModel = new DefaultComboBoxModel<>();
		cmbAuthor = new JComboBox<>(authorComboBoxModel);
		cmbAuthor.setName("authorComboBox");
		cmbAuthor.setEnabled(false);
		cmbAuthor.setBounds(100, 120, 180, 20);
		panel.add(cmbAuthor);

		btnAddBook = new JButton("Add book");
		btnAddBook.setEnabled(false);
		btnAddBook.setBounds(10, 150, 270, 25);
		panel.add(btnAddBook);

		listBookModel = new DefaultListModel<>();
		listBooks = new JList<>(listBookModel);
		listBooks.setName("booksList");
		listBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listBooks.setBounds(10, 185, 270, 130);
		panel.add(listBooks);

		btnDeleteBook = new JButton("Delete book");
		btnDeleteBook.setEnabled(false);
		btnDeleteBook.setBounds(10, 325, 270, 25);
		panel.add(btnDeleteBook);

		lblErrorBook = new JLabel(" ");
		lblErrorBook.setName("errorBookLabel");
		lblErrorBook.setBounds(10, 360, 270, 20);
		panel.add(lblErrorBook);

		return panel;

	}

	@Override
	public void showAllBooks(List<Book> books) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newBookAdded(Book bookToAdd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showErrorBookAlreadyExists(String errorMessage, Book existingBook) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showErrorAuthorDoesNotExist(String errorMessage, Author notExistigAuthor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bookRemoved(Book bookToRemove) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showErrorBookDoesNotExist(String errorMessage, Book notExistingBook) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAllAuthors(List<Author> authors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newAuthorAdded(Author authorToAdd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showErrorAuthorAlreadyExist(String errorMessage, Author existingAuthor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void authorRemoved(Author authorToRemove) {
		// TODO Auto-generated method stub
		
	}

	public void setBookController(BookController bookController) {
		this.bookController = bookController;
	}

	public void setAuthorController(AuthorController authorController) {
		this.authorController = authorController;
	}

}
