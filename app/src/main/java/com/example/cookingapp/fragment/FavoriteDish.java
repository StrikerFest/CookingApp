package com.example.cookingapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cookingapp.CookbookActivity;
import com.example.cookingapp.R;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View RootView = inflater.inflate(R.layout.fragment_favorite_dish, container, false);

		lvFavoriteRecipe = (ListView) RootView.findViewById(R.id.lvFavoriteRecipe);
		// init db
		dbHelper = new DBHelper(getActivity());
		favoriteDAO = new FavoriteDAO(dbHelper);
		listRecipe = favoriteDAO.all();

		// LIST - If list empty
		if (listRecipe.size() == 0)
			Toast.makeText(getActivity(), "Empty vessel", Toast.LENGTH_SHORT).show();

		FavoriteAdapter favoriteAdapter = new FavoriteAdapter(getActivity(), listRecipe);

		lvFavoriteRecipe.setAdapter(favoriteAdapter);
		return RootView;
	}
}