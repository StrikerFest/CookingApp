package com.example.cookingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cookingapp.CookbookActivity;
import com.example.cookingapp.MainActivity;
import com.example.cookingapp.PersonalRecipeDetailActivity;
import com.example.cookingapp.R;
import com.example.cookingapp.RecipeDetailActivity;
import com.example.cookingapp.adapter.PersonalRecipeAdapter;
import com.example.cookingapp.adapter.RecipeAdapter;
import com.example.cookingapp.db.DAO;
import com.example.cookingapp.db.DBHelper;
import com.example.cookingapp.db.PersonalRecipeDAO;
import com.example.cookingapp.db.RecipeDAO;
import com.example.cookingapp.dialog.RecipeDialog;
import com.example.cookingapp.model.PersonalRecipe;
import com.example.cookingapp.model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PersonalCookbook extends Fragment {

	// DBHelper and DAO
	private DBHelper dbHelper;
	private DAO<PersonalRecipe> personalRecipeDAO;

	private AlertDialog alertDialog;
	private AlertDialog.Builder builder;

	private RecipeDialog recipeDialog;
	private FloatingActionButton fabAdd;

	private List<PersonalRecipe> listPersonalRecipe;

	private ListView lvPersonalRecipe;
	private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View RootView = inflater.inflate(R.layout.fragment_personal_cookbook, container, false);

		// Floating Action Button
		fabAdd = (FloatingActionButton) RootView.findViewById(R.id.fabAdd);
		lvPersonalRecipe = (ListView) RootView.findViewById(R.id.lvPersonalRecipe);

		listPersonalRecipe = new ArrayList<>();

		// ======================================================================
		// DATABASE =============================================================
		dbHelper = new DBHelper(getActivity());
		personalRecipeDAO = new PersonalRecipeDAO(dbHelper);

		listPersonalRecipe = personalRecipeDAO.all();

		// ADAPTER - Create adapter for List View ========================================
		PersonalRecipeAdapter personalRecipeAdapter = new PersonalRecipeAdapter(getActivity(), listPersonalRecipe);

		lvPersonalRecipe.setAdapter(personalRecipeAdapter);

		lvPersonalRecipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				listPersonalRecipe = personalRecipeDAO.all();
				personalRecipeAdapter.notifyDataSetChanged();
				Log.d("listRecipe.get(position)", String.valueOf(listPersonalRecipe.get(position)));
				Intent intent = new Intent(getContext(), PersonalRecipeDetailActivity.class);
				intent.putExtra("recipe", listPersonalRecipe.get(position));
				startActivity(intent);
			}
		});

		// =========================================================
		// DIALOG ==================================================

		// DIALOG - Add recipe
		recipeDialog = new RecipeDialog(getActivity()) {
			@Override
			protected void passData(String name, String ingredient,String instruction, String tag) {
				PersonalRecipe item = new PersonalRecipe(name, ingredient,instruction, tag);
				long id = personalRecipeDAO.create(item);
				personalRecipeAdapter.notifyDataSetChanged();

				item.setId(id);

				listPersonalRecipe.add(item);
				personalRecipeAdapter.notifyDataSetChanged();
			}
		};

		// =========================================================
		// FLOAT ACTION BUTTON =====================================

		// FLOAT ACTION BUTTON - Open add dialog
		fabAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recipeDialog.show();
				Toast.makeText(getActivity(), "Add Dialog loading...", Toast.LENGTH_SHORT).show();
				personalRecipeAdapter.notifyDataSetChanged();

			}
		});


		return RootView;

	}
}