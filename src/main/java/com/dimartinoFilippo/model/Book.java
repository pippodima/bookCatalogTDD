package com.dimartinoFilippo.model;

import java.util.Objects;

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

	public Book() {
		super();
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
	
	@Override
	public int hashCode() {
		return Objects.hash(author, isbn, publicationYear, title);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Book other = (Book) obj;
		return publicationYear == other.publicationYear && Objects.equals(isbn, other.isbn)
				&& Objects.equals(title, other.title) && Objects.equals(author, other.author);
	}
	
	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", author=" + author + ", publicationYear="
				+ publicationYear + "]";
	}

}
