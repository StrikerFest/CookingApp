package com.example.cookingapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cookingapp.R;

public abstract class RecipeDialog extends Dialog {

	// Create variable
	private Button btnSave, btnExit;
	private EditText editName, editIngredient, editTag;
	private Context context;

	// Create constructor with context that can't be null
	public RecipeDialog(@NonNull Context context) {
		super(context);
		this.context = context;

	}

	// Override the current show function with the following
	@Override
	public void show() {
		super.show();
		editName.setText("");
		editIngredient.setText("");
		editTag.setText("");

	}

	// Override onCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_recipe);

		// Find view by id
		editName = findViewById(R.id.editRecipeName);
		editIngredient = findViewById(R.id.editRecipeIngredient);
		editTag = findViewById(R.id.editRecipeTag);
		btnExit = findViewById(R.id.btnExit);
		btnSave = findViewById(R.id.btnSave);

		// On button click save
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Get data from editName
				String name = editName.getText().toString();

				// If it empty
				if (name.isEmpty()) {
					editName.setError("Enter name");
					return;
				}

				// Get data from editIngredient
				String ingredient = editIngredient.getText().toString();

				// If it empty
				if (ingredient.isEmpty()) {
					editIngredient.setError("Enter ingredient");
				}

				// Get data from editTag
				String tag = editTag.getText().toString();

				// pass the data
				passData(name, ingredient,tag);

				// If it empty
				if (tag.isEmpty()) {
					editTag.setError("Enter tag");
				}
			}
		});

		btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Exit
				dismiss();
				Toast.makeText(context, "Exiting...", Toast.LENGTH_SHORT).show();
			}
		});
	}
	protected abstract void passData(String name, String ingredient,String tag);

}
