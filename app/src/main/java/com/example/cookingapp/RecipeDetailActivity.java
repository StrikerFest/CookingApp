package com.example.cookingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookingapp.adapter.IngredientAdapter;
import com.example.cookingapp.adapter.RecipeAdapter;
import com.example.cookingapp.adapter.VPAdapter;
import com.example.cookingapp.fragment.fragment1;
import com.example.cookingapp.fragment.fragment2;
import com.example.cookingapp.fragment.fragment3;
import com.example.cookingapp.model.Ingredient;
import com.example.cookingapp.model.Recipe;
import com.example.cookingapp.util.RecipeAPI;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements Serializable {

	// Variable
	private TextView tvRecipeName,tvDescription;
	private List<Ingredient> listIngredient;
	private Recipe recipe;
	private ImageView ivRecipeImage;
	private ListView lvIngredientList;

	private TabLayout tabLayout;
	private ViewPager viewPager;
	// ==================================================================================
	// API ==============================================================================

	// API - Request ingredient from recipe API
	void ingredientCall(String text, IngredientAdapter ingredientAdapter, List<Ingredient> listIngredient){

		// API - Create URL
		String myUrl = RecipeAPI.createURL(text);

		// API - Request The service to get data
		StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
				response -> {
					try {
						listIngredient.clear();
						// ACCESS API - Get JSON Object
						JSONObject outer = new JSONObject(response);

						// ACCESS API - Get meals property of root Object
						JSONArray jsonMeals = outer.getJSONArray("meals");

						// LOOP - Loop meals property to get index
						for (int i = 0; i < jsonMeals.length(); i++) {

							// ACCESS API - Get JSONObject
							JSONObject jsonMealIndex = jsonMeals.getJSONObject(i);

							// ACCESS API - Get id from API
							Long idMeal = Long.valueOf(jsonMealIndex.getString("idMeal"));

							for(int j = 1; j <= 20; j++ ) {
								// ACCESS API - Get recipe name from API
								String strIngredient = jsonMealIndex.getString("strIngredient" + j);

								// ACCESS API - Get recipe ingredient from API
								String strMeasure = jsonMealIndex.getString("strMeasure" + j);
								if (strIngredient == "" || strIngredient == null)
									break;

								// LIST - Add retrieved API data to list
								listIngredient.add(new Ingredient(idMeal, strIngredient, strMeasure, "https://cdn-icons-png.flaticon.com/128/562/562678.png"));
								// LOG
								Log.d("recipeId", idMeal.toString());
								Log.d("recipeName", strIngredient);
								Log.d("strMealThumb", strMeasure);
							}


							ingredientAdapter.notifyDataSetChanged();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				},
				volleyError -> Toast.makeText(RecipeDetailActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show()
		);
		// REQUEST - queue request
		RequestQueue requestQueue = Volley.newRequestQueue(RecipeDetailActivity.this);

		// REQUEST - Add request to queue
		requestQueue.add(myRequest);
	}

	// API - Request ingredient from recipe API with recipe ID
	void ingredientCallById(Long id	, IngredientAdapter ingredientAdapter, List<Ingredient> listIngredient){

		// API - Create URL
		String myUrl = RecipeAPI.createURLById(id);

		// API - Request The service to get data
		StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
				response -> {
					try {
						listIngredient.clear();
						// ACCESS API - Get JSON Object
						JSONObject outer = new JSONObject(response);

						// ACCESS API - Get meals property of root Object
						JSONArray jsonMeals = outer.getJSONArray("meals");

						// LOOP - Loop meals property to get index
						for (int i = 0; i < jsonMeals.length(); i++) {

							// ACCESS API - Get JSONObject
							JSONObject jsonMealIndex = jsonMeals.getJSONObject(i);

							// ACCESS API - Get id from API
							Long idMeal = Long.valueOf(jsonMealIndex.getString("idMeal"));

							for(int j = 1; j <= 20; j++ ) {
								// ACCESS API - Get recipe name from API
								String strIngredient = jsonMealIndex.getString("strIngredient" + j);

								// ACCESS API - Get recipe ingredient from API
								String strMeasure = jsonMealIndex.getString("strMeasure" + j);
								if (strIngredient == "" || strIngredient == null)
									break;

								// LIST - Add retrieved API data to list
								listIngredient.add(new Ingredient(idMeal, strIngredient, strMeasure, "https://cdn-icons-png.flaticon.com/128/562/562678.png"));
								// LOG
								Log.d("recipeId", idMeal.toString());
								Log.d("recipeName", strIngredient);
								Log.d("strMealThumb", strMeasure);
							}

							ingredientAdapter.notifyDataSetChanged();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				},
				volleyError -> Toast.makeText(RecipeDetailActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show()
		);
		// REQUEST - queue request
		RequestQueue requestQueue = Volley.newRequestQueue(RecipeDetailActivity.this);

		// REQUEST - Add request to queue
		requestQueue.add(myRequest);
	}

	// Initial UI - get view
	private void initUI() {

		// TabLayout
		tabLayout = findViewById(R.id.tabLayout);

		// ViewPager
		viewPager = findViewById(R.id.viewPage);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recipe_detail);

		initUI();

		// TAB LAYOUT
		tabLayout.setupWithViewPager(viewPager);
		VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		vpAdapter.addFragment(new fragment1(),"Intro");
		vpAdapter.addFragment(new fragment2(),"Recipe");
		vpAdapter.addFragment(new fragment3(),"Instruct");

		viewPager.setAdapter(vpAdapter);

	}
}