package io.springworks.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	private int id;
	private String name;
	private Boolean haveRentedBook = false;

	public User() {
	}

	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getHavRentedBook() {
		return haveRentedBook;
	}

	public void setHavRentedBook(Boolean havRentedBook) {
		this.haveRentedBook = havRentedBook;
	}

}
