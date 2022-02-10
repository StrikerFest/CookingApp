package com.example.cookingapp.model;

public class Instruction {

	private Long id;
	private String Instruction;

	public Instruction(Long id, String instruction) {
		this.id = id;
		Instruction = instruction;
	}

	public Instruction(String instruction) {
		Instruction = instruction;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstruction() {
		return Instruction;
	}

	public void setInstruction(String instruction) {
		Instruction = instruction;
	}
}
