package com.dimartinoFilippo.model;

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

	
}
