package com.example.cookingapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookingapp.adapter.IngredientAdapter;
import com.example.cookingapp.adapter.VPAdapter;
import com.example.cookingapp.fragment.fragment1;
import com.example.cookingapp.fragment.fragment2;
import com.example.cookingapp.fragment.fragment3;
import com.example.cookingapp.model.Ingredient;
import com.example.cookingapp.model.PersonalRecipe;
import com.example.cookingapp.model.Recipe;
import com.example.cookingapp.util.RecipeAPI;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class PersonalRecipeDetailActivity extends AppCompatActivity implements Serializable {

	// Variable
	private TextView tvRecipeName,tvIngredient,tvInstruction;
	private List<Ingredient> listIngredient;
	private PersonalRecipe recipe;
	private ImageView ivRecipeImage;
	private ListView lvIngredientList;

	// Initial UI - get view
	private void initUI() {
		tvRecipeName = findViewById(R.id.tvRecipeName);
		tvIngredient = findViewById(R.id.tvIngredient);
		tvInstruction = findViewById(R.id.tvInstruction);


	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_recipe_detail);

		initUI();
		recipe = (PersonalRecipe) getIntent().getSerializableExtra("recipe");
		tvRecipeName.setText(recipe.getName());
		tvIngredient.setText(recipe.getIngredient());
		tvInstruction.setText(recipe.getInstruction());



	}
}