package com.example.cookingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookingapp.adapter.RecipeAdapter;
import com.example.cookingapp.adapter.VPAdapter;
import com.example.cookingapp.db.DAO;
import com.example.cookingapp.db.DBHelper;
import com.example.cookingapp.db.RecipeDAO;
import com.example.cookingapp.dialog.RecipeDialog;
import com.example.cookingapp.fragment.fragment1;
import com.example.cookingapp.fragment.fragment2;
import com.example.cookingapp.fragment.fragment3;
import com.example.cookingapp.model.Recipe;
import com.example.cookingapp.util.RecipeAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	// Variable
	private TextView tvSearchMain;
	private LinearLayout llSearchMain;
	private SearchView svSearchMain;
	private Button btnBreakfast, btnStarter, btnSide, btnVegetarian, btnDessert, btnSeafood;
	private GridLayout glMenuBtn;
	private ListView lvRecipeList;
	private List<Recipe> listRecipe;

	private ArrayList<Recipe> tempList;

	// Initial UI - get view
	private void initUI() {

		// LinearLayout
		llSearchMain = findViewById(R.id.llSearchMain);

		// SearchView
		svSearchMain = findViewById(R.id.svSearchMain);

		// TextView
		tvSearchMain = findViewById(R.id.tvSearchMain);

		// Button
		btnBreakfast = findViewById(R.id.btnBreakfast);
		btnStarter = findViewById(R.id.btnStarter);
		btnSide = findViewById(R.id.btnSide);
		btnVegetarian = findViewById(R.id.btnVegetarian);
		btnDessert = findViewById(R.id.btnDessert);
		btnSeafood = findViewById(R.id.btnSeafood);

		// GridLayout
		glMenuBtn = findViewById(R.id.glMenuBtn);

		// ListView
		lvRecipeList = findViewById(R.id.lvRecipeList);



	}

	// ==================================================================================
	// API ==============================================================================

	// API - Request recipe api
	void recipeCall(String text,RecipeAdapter recipeAdapter,List<Recipe> listRecipe){

		// API - Create URL
		String myUrl = RecipeAPI.createURL(text);

		// API - Request The service to get data
		StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
				response -> {
					try {
						listRecipe.clear();
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

							// ACCESS API - Get recipe name from API
							String strMeal = jsonMealIndex.getString("strMeal");

							// ACCESS API - Get recipe ingredient from API
							String strMealThumb = jsonMealIndex.getString("strMealThumb");

							// ACCESS API - Get recipe instruction from API
							String strInstruction = jsonMealIndex.getString("strInstructions");

							// ACCESS API - Get recipe tag from API
							String strTags = jsonMealIndex.getString("strTags");

							// LIST - Add retrieved API data to list
							listRecipe.add(new Recipe(idMeal, strMeal, strMealThumb, strInstruction, strTags));
							// LOG
							Log.d("recipeId", idMeal.toString());
							Log.d("recipeName", strMeal);
							Log.d("strMealThumb", strMealThumb);
							Log.d("recipeTag", strTags);
							recipeAdapter.notifyDataSetChanged();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				},
				volleyError -> Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show()
		);
		// REQUEST - queue request
		RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

		// REQUEST - Add request to queue
		requestQueue.add(myRequest);
	}

	void recipeCategoryCall(String text,RecipeAdapter recipeAdapter,List<Recipe> listRecipe){

		// API - Create URL
		String myUrl = RecipeAPI.createURLCategory(text);

		// API - Request The service to get data
		StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
				response -> {
					try {
						listRecipe.clear();
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

							// ACCESS API - Get recipe name from API
							String strMeal = jsonMealIndex.getString("strMeal");

							// ACCESS API - Get recipe tag from API
							String strMealThumb = jsonMealIndex.getString("strMealThumb");

							// ACCESS API - Get recipe tag from API
							String strTags = text;

							// LIST - Add retrieved API data to list
							listRecipe.add(new Recipe(idMeal, strMeal,strMealThumb, strTags));
							// LOG
							Log.d("recipeId", idMeal.toString());
							Log.d("recipeName", strMeal);
							Log.d("strMealThumb", strMealThumb);
							Log.d("recipeTag", strTags);
							recipeAdapter.notifyDataSetChanged();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				},
				volleyError -> Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show()
		);
		// REQUEST - queue request
		RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

		// REQUEST - Add request to queue
		requestQueue.add(myRequest);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// INIT_UI ================================================================================
		initUI();


		// DATABASE - Get all record
		listRecipe = new ArrayList<>();
//		listRecipe = recipeDAO.all();

		// LIST - If list empty
		if (listRecipe.size() == 0) Toast.makeText(this, "Empty vessel", Toast.LENGTH_SHORT).show();

		// =======================================================================================
		// SEARCH BAR ============================================================================

		// SEARCH - TEXT VIEW - On click
		tvSearchMain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(svSearchMain.isIconified())
					svSearchMain.setIconified(false);
				else
					svSearchMain.setIconified(true);

			}
		});

		// ADAPTER - Create adapter for List View ========================================
		RecipeAdapter recipeAdapter = new RecipeAdapter(MainActivity.this, listRecipe);

		// SEARCH - SEARCH VIEW
		svSearchMain.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			// Change on submit
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			// Change on type
			@Override
			public boolean onQueryTextChange(String newText) {
				recipeCall(newText,recipeAdapter,listRecipe);
				return false;

			}
		});

		// BUTTON ===============================================================

		// BUTTON breakfast
		btnBreakfast.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recipeCategoryCall("Breakfast",recipeAdapter,listRecipe);
			}
		});

		// BUTTON starter
		btnStarter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recipeCategoryCall("Starter",recipeAdapter,listRecipe);
			}
		});

		// BUTTON side
		btnSide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recipeCategoryCall("Side",recipeAdapter,listRecipe);
			}
		});

		// BUTTON vegetarian
		btnVegetarian.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recipeCategoryCall("Vegetarian",recipeAdapter,listRecipe);
			}
		});

		// BUTTON dessert
		btnDessert.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recipeCategoryCall("Dessert",recipeAdapter,listRecipe);
			}
		});

		// BUTTON seafood
		btnSeafood.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recipeCategoryCall("Seafood",recipeAdapter,listRecipe);
			}
		});

		// ======================================================================
		// LIST VIEW ============================================================

		// LIST VIEW - Set adapter to list view
		lvRecipeList.setAdapter(recipeAdapter);
		recipeAdapter.notifyDataSetChanged();

		// LIST VIEW - On item click - Change activity
		lvRecipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), "List view item clicked", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this,RecipeDetailActivity.class);
				intent.putExtra("recipe", listRecipe.get(position));
				startActivity(intent);
			}
		});

		// LIST VIEW - On item hold - More option
		lvRecipeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), "List view item held", Toast.LENGTH_SHORT).show();
				return true;

			}
		});






	}
}