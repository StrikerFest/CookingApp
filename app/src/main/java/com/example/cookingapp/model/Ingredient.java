package com.example.cookingapp.model;

import java.io.Serializable;

public class Ingredient implements Serializable {

	// Variable
	private long id;
	private String name;
	private String description;
	private String amount;
	private String image;

	// Less with id
	public Ingredient(long id, String name, String amount, String image) {
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.image = image;
	}

	// Full with id
	public Ingredient(long id, String name, String description, String amount, String image) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.amount = amount;
		this.image = image;
	}

	// Empty
	public Ingredient() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
