package com.example.cookingapp.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cookingapp.MainActivity;
import com.example.cookingapp.R;
import com.example.cookingapp.adapter.RecipeAdapter;
import com.example.cookingapp.db.DAO;
import com.example.cookingapp.db.DBHelper;
import com.example.cookingapp.db.RecipeDAO;
import com.example.cookingapp.dialog.RecipeDialog;
import com.example.cookingapp.model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PersonalCookbook extends Fragment {

	// DBHelper and DAO
	private DBHelper dbHelper;
	private DAO<Recipe> recipeDAO;

	private AlertDialog alertDialog;
	private AlertDialog.Builder builder;

	private RecipeDialog recipeDialog;
	private FloatingActionButton fabAdd;

	private List<Recipe> listRecipe;

	private ListView lvRecipeList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View RootView = inflater.inflate(R.layout.fragment_personal_cookbook, container, false);

		// Floating Action Button
		fabAdd = (FloatingActionButton) RootView.findViewById(R.id.fabAdd);

		listRecipe = new ArrayList<>();

		// ======================================================================
		// DATABASE =============================================================
		dbHelper = new DBHelper(getActivity());
		recipeDAO = new RecipeDAO(dbHelper);

		// ADAPTER - Create adapter for List View ========================================
		RecipeAdapter recipeAdapter = new RecipeAdapter(getActivity(), listRecipe);

		// =========================================================
		// DIALOG ==================================================

		// DIALOG - Add recipe
//		recipeDialog = new RecipeDialog(getActivity()) {
//			@Override
//			protected void passData(String name, String ingredient, String tag) {
//				Recipe item = new Recipe(name, ingredient, tag);
//				long id = recipeDAO.create(item);
//				item.setId(id);
//
//				listRecipe.add(item);
//				recipeAdapter.notifyDataSetChanged();
//			}
//		};

		// =========================================================
		// FLOAT ACTION BUTTON =====================================

		// FLOAT ACTION BUTTON - Open add dialog
//		fabAdd.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				recipeDialog.show();
//				Toast.makeText(getActivity(), "Add Dialog loading...", Toast.LENGTH_SHORT).show();
//			}
//		});


		return RootView;

	}
}