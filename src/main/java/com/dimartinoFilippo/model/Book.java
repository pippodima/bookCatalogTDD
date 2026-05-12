package com.dimartinoFilippo.model;

public class Book {
	
	private String isbn;
	private String title;
	private Author author;
	private int publicationYear;
	
	public Book(String isbn, String title, Author author, int publicationYear) {
		super();
		this.setIsbn(isbn);
		this.setTitle(title);
		this.setAuthor(author);
		this.setPublicationYear(publicationYear);
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}
	
	

}
