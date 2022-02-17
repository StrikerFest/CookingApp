package com.example.cookingapp.adapter;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingapp.CookbookActivity;
import com.example.cookingapp.MainActivity;
import com.example.cookingapp.R;
import com.example.cookingapp.db.DAO;
import com.example.cookingapp.db.DBHelper;
import com.example.cookingapp.db.FavoriteDAO;
import com.example.cookingapp.model.Recipe;
import com.example.cookingapp.thread.RecipeImageLoadThread;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends BaseAdapter {

	// Variable
	private Context context;
	private List<Recipe> listRecipe;
	private ArrayList<Recipe> arraylist;

	private List<Recipe> listTemp;
	private Long idTemp;

	private DBHelper dbHelper;
	private DAO<Recipe> favoriteDAO;

	// Adapter constructor
	public FavoriteAdapter(Context context, List<Recipe> listRecipe) {
		this.context = context;
		this.listRecipe = listRecipe;
		this.arraylist = new ArrayList<Recipe>();
		this.arraylist.addAll(listRecipe);
	}

	@Override
	public int getCount() {
		return listRecipe.size();
	}

	@Override
	public Object getItem(int position) {
		return listRecipe.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void remove(int position) {
		listRecipe.remove(listRecipe.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);

		// Set values
		TextView tvFoodName, tvFoodDifficulty, tvFoodRating, tvIngredient, tvTag;
		ImageView ivFoodImg, ivMenu, ivFavorite;

		dbHelper = new DBHelper( context);
		favoriteDAO = new FavoriteDAO(dbHelper);

		// Find item by id
		tvFoodName = convertView.findViewById(R.id.tvFoodName);
		tvIngredient = convertView.findViewById(R.id.tvIngredient);
		tvTag = convertView.findViewById(R.id.tvTag);

		ivFoodImg = convertView.findViewById(R.id.ivFoodImg);

		ivMenu = convertView.findViewById(R.id.ivMenu);
		ivFavorite = convertView.findViewById(R.id.ivFavorite);

		// On click

		// Pop up menu

		// Dialog

		// Pop up extra

		// Config strictMode
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

//		ivFavorite.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(context.getApplicationContext(), "NANNA", Toast.LENGTH_SHORT).show();
//				ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
////				idTemp = listRecipe.get(position).getId();
//				favoriteDAO.create(listRecipe.get(position));
//			}
//		});

//		// Thread
//		Runnable RecipeImageLoadThread = new RecipeImageLoadThread(listRecipe, ivFoodImg, position, context);
//		new Thread(RecipeImageLoadThread).start();
		Picasso.with(((CookbookActivity) context))
				.load(listRecipe.get(position).getImage())
				.into(ivFoodImg);

		// Set data
		tvFoodName.setText(listRecipe.get(position).getName());
		tvTag.setText(listRecipe.get(position).getTag());

		notifyDataSetChanged();
		return convertView;

	}

}
