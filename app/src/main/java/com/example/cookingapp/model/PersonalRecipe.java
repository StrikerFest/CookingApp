package com.example.cookingapp.model;

import java.io.Serializable;

public class PersonalRecipe implements Serializable {
	private long id;
	private String name;
	private String ingredient;
	private String instruction;
	private String tag;

	// Full without id
	public PersonalRecipe(String name, String ingredient, String tag) {
		this.name = name;
		this.ingredient = ingredient;
		this.tag = tag;
	}
	// Full with id
	public PersonalRecipe(long id, String name, String ingredient, String instruction, String tag) {
		this.id = id;
		this.name = name;
		this.ingredient = ingredient;
		this.instruction = instruction;
		this.tag = tag;
	}

	public PersonalRecipe( String name, String ingredient, String instruction, String tag) {
		this.name = name;
		this.ingredient = ingredient;
		this.instruction = instruction;
		this.tag = tag;
	}
	// Less without id
	public PersonalRecipe(String name, String tag) {
		this.name = name;
		this.tag = tag;
	}

	// Less with id
	public PersonalRecipe(long id, String name, String ingredient, String tag) {
		this.id = id;
		this.name = name;
		this.ingredient = ingredient;
		this.tag = tag;
	}

	// Empty
	public PersonalRecipe() {
	}

	@Override
	public String toString() {
		return "Recipe{" +
				"id=" + id +
				", name='" + name + '\'' +
				", ingredient=" + ingredient +
				", tag='" + tag + '\'' +
				'}';
	}


	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}


}
