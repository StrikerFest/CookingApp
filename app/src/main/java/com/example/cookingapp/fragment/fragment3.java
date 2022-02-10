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

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookingapp.R;
import com.example.cookingapp.adapter.IngredientAdapter;
import com.example.cookingapp.adapter.InstructionAdapter;
import com.example.cookingapp.model.Ingredient;
import com.example.cookingapp.model.Instruction;
import com.example.cookingapp.model.Recipe;
import com.example.cookingapp.util.RecipeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class fragment3 extends Fragment {

	// Variable
	private TextView tvInstruction;
	private List<Instruction> listInstruction;
	private Instruction instruction;
	private Recipe recipe;
	private ListView lvInstructionList;

	// API - Request ingredient from recipe API with recipe ID
	void callInstruction(Long id , InstructionAdapter instructionAdapter, List<Instruction> listInstruction){

		// API - Create URL
		String myUrl = RecipeAPI.createURLById(id);

		// API - Request The service to get data
		StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
				response -> {
					try {
						listInstruction.clear();
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

							// ACCESS API - Get id from API
							String strInstruction = jsonMealIndex.getString("strInstructions");
								// LIST - Add retrieved API data to list
								listInstruction.add(new Instruction(idMeal, strInstruction));
								// LOG
								Log.d("recipeIdFRAG3", idMeal.toString());
								Log.d("strInstructionFRAG3", strInstruction);

							instructionAdapter.notifyDataSetChanged();
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
		View RootView = inflater.inflate(R.layout.fragment_fragment3, container, false);

		lvInstructionList = (ListView) RootView.findViewById(R.id.lvInstructionDescription);
		listInstruction = new ArrayList<>();
		recipe = (Recipe) getActivity().getIntent().getSerializableExtra("recipe");

		// ADAPTER - create new ingredient adapter
		InstructionAdapter instructionAdapter = new InstructionAdapter(getActivity(), listInstruction);

		// API - call API and add data to listIngredient
		callInstruction(recipe.getId(),instructionAdapter,listInstruction);

		// LISTVIEW - set adapter to list view
		lvInstructionList.setAdapter(instructionAdapter);
		instructionAdapter.notifyDataSetChanged();

		return RootView;
	}
}