package com.example.cookingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cookingapp.CookbookActivity;
import com.example.cookingapp.R;
import com.example.cookingapp.RecipeDetailActivity;
import com.example.cookingapp.adapter.FavoriteAdapter;
import com.example.cookingapp.db.DAO;
import com.example.cookingapp.db.DBHelper;
import com.example.cookingapp.db.FavoriteDAO;
import com.example.cookingapp.model.Recipe;

import java.util.List;

public class FavoriteDish extends Fragment {
	private List<Recipe> listRecipe;
	// DBHelper và TodoDAO
	private DBHelper dbHelper;
	private DAO<Recipe> favoriteDAO;

	private ListView lvFavoriteRecipe;
	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View RootView = inflater.inflate(R.layout.fragment_favorite_dish, container, false);

		// Set view to variable
		lvFavoriteRecipe = (ListView) RootView.findViewById(R.id.lvFavoriteRecipe);

		// init db
		dbHelper = new DBHelper(getActivity());
		favoriteDAO = new FavoriteDAO(dbHelper);
		listRecipe = favoriteDAO.all();

		// LIST - If list empty
		if (listRecipe.size() == 0)
			Toast.makeText(getActivity(), "Let's get some recipe in here", Toast.LENGTH_SHORT).show();

		FavoriteAdapter favoriteAdapter = new FavoriteAdapter(getActivity(), listRecipe);

		lvFavoriteRecipe.setAdapter(favoriteAdapter);

		// LIST VIEW - On item click - Change activity
		lvFavoriteRecipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				listRecipe = favoriteDAO.all();
				Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
				intent.putExtra("recipe", listRecipe.get(position));
				startActivity(intent);
			}
		});
		return RootView;
	}
}