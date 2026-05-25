package com.dimartinoFilippo.view.swing;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
		setTitle("Library View");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

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
