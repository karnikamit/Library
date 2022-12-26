package io.springworks.models;

import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Users")
public class User {

	@Id
	private int id;
	private String name;
	private List<Map<String, String>> contactDetails;

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
	public List<Map<String, String>> getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(List<Map<String, String>> contactDetails) {
		this.contactDetails = contactDetails;
	}

}
