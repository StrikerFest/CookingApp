package com.example.cookingapp.model;

import java.io.Serializable;

public class Favorite implements Serializable {
	private long id;
	private String name;
	private String image;
	private String instruction;
	private String tag;
	private boolean favorite;

	// Full without id
	public Favorite(String name, String image, String tag) {
		this.name = name;
		this.image = image;
		this.tag = tag;
	}
	// Full with id
	public Favorite(long id, String name, String image, String instruction, String tag) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.instruction = instruction;
		this.tag = tag;
	}

	// Less without id
	public Favorite(String name, String tag) {
		this.name = name;
		this.tag = tag;
	}

	// Less with id
	public Favorite(long id, String name, String image, String tag) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.tag = tag;
	}

	// Empty
	public Favorite() {
	}

	@Override
	public String toString() {
		return "Recipe{" +
				"id=" + id +
				", name='" + name + '\'' +
				", image=" + image +
				", tag='" + tag + '\'' +
				'}';
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}


}
