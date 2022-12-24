package io.springworks.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

	@Id
	private int id;
	private String name;
	private String author;
	private String release;
	private String preface;
	private int rentingUserId;

	public Book() {
	}

	public Book(String name, String author, String release, String preface) {
		super();
		this.name = name;
		this.author = author;
		this.release = release;
		this.preface = preface;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getPreface() {
		return preface;
	}

	public void setPreface(String preface) {
		this.preface = preface;
	}

	public int getRentingUserId() {
		return rentingUserId;
	}

	public void setRentingUserId(int rentingUserId) {
		this.rentingUserId = rentingUserId;
	}
}
