package com.dimartinoFilippo.model;

import java.util.Objects;

public class Author {
	
	private String id;
	private String firstName;
	private String lastName;
	
	public Author(String id, String firstName, String lastName) {
		super();
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Author other = (Author) obj;
		return Objects.equals(id, other.id) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
