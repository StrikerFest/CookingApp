package com.example.cookingapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookingapp.R;
import com.example.cookingapp.RecipeDetailActivity;
import com.example.cookingapp.adapter.IngredientAdapter;
import com.example.cookingapp.model.Ingredient;
import com.example.cookingapp.model.Recipe;
import com.example.cookingapp.util.RecipeAPI;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class fragment2 extends Fragment {

	// Variable
	private TextView tvRecipeName,tvDescription;
	private List<Ingredient> listIngredient;
	private Recipe recipe;
	private ImageView ivRecipeImage;
	private ListView lvIngredientList;

	private TabLayout tabLayout;
	private ViewPager viewPager;

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
								listIngredient.add(new Ingredient(idMeal, strIngredient, strMeasure, "https://www.themealdb.com/images/ingredients/" + strIngredient +"-Small.png"));
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
				volleyError -> Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_SHORT).show()
		);
		// REQUEST - queue request
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

		// REQUEST - Add request to queue
		requestQueue.add(myRequest);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View RootView = inflater.inflate(R.layout.fragment_fragment2, container, false);
		lvIngredientList = (ListView) RootView.findViewById(R.id.lvRecipeIngredientList);
		listIngredient = new ArrayList<>();
		recipe = (Recipe) getActivity().getIntent().getSerializableExtra("recipe");

		// ADAPTER - create new ingredient adapter
		IngredientAdapter ingredientAdapter = new IngredientAdapter(getActivity(), listIngredient);
		// API - call API and add data to listIngredient
		ingredientCallById(recipe.getId(),ingredientAdapter,listIngredient);

		// LISTVIEW - set adapter to list view
		lvIngredientList.setAdapter(ingredientAdapter);
		ingredientAdapter.notifyDataSetChanged();
		return RootView;

	}


}