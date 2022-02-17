package com.example.cookingapp.adapter;

import static java.lang.String.valueOf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingapp.MainActivity;
import com.example.cookingapp.R;
import com.example.cookingapp.db.DAO;
import com.example.cookingapp.db.DBHelper;
import com.example.cookingapp.db.FavoriteDAO;
import com.example.cookingapp.db.RecipeDAO;
import com.example.cookingapp.model.Recipe;
import com.example.cookingapp.thread.RecipeImageLoadThread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeAdapter extends BaseAdapter {

	// Variable
	private Context context;
	private List<Recipe> listRecipe;
	private ArrayList<Recipe> arraylist;

	private List<Recipe> listTemp;
	private Long idTemp;

	private DBHelper dbHelper;
	private DAO<Recipe> favoriteDAO;

	// Adapter constructor
	public RecipeAdapter(Context context, List<Recipe> listRecipe) {
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

		dbHelper = new DBHelper((MainActivity) context);
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

		// Download and set image from API
//		URL newURL = null;
//		try {
//			newURL = new URL(listRecipe.get(position).getImage());
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		Bitmap mIcon_val = null;
//		try {
//			mIcon_val = BitmapFactory.decodeStream(newURL.openConnection().getInputStream());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		ivFoodImg.setImageBitmap(mIcon_val);
//		ivFoodImg.setImageResource(R.drawable.ic_baseline_fastfood_24);

		// Get all item from fav food list
		listTemp = favoriteDAO.all();
		notifyDataSetChanged();

		// If the item at the current position don't match any item in the fav food list
		for (Recipe r : listTemp){
			if (listRecipe.get(position).getId() != r.getId()){
				ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
				notifyDataSetChanged();
			}
		}

		// If the item at the current position  match any item in the fav food list
		for (Recipe r : listTemp){
			if (listRecipe.get(position).getId() == r.getId()){
				ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
				notifyDataSetChanged();
				break;
			}
		}
		notifyDataSetChanged();


		ivFavorite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

//				idTemp = listRecipe.get(position).getId();
				boolean checkClick = false;
				for (Recipe r : listTemp){
					if (listRecipe.get(position).getId() != r.getId()){
						Log.d("id of r1", String.valueOf(r.getId()));
						Log.d("id of favrep1",valueOf(listRecipe.get(position).getId()));
						checkClick = false;
						notifyDataSetChanged();
					}
					else if(listRecipe.get(position).getId() == r.getId()) {
						Log.d("id of r2", String.valueOf(r.getId()));
						Log.d("id of favrep2",valueOf(listRecipe.get(position).getId()));
						checkClick = true;
						break;
					}
					else{
						Toast.makeText(context.getApplicationContext(), "We got a problem", Toast.LENGTH_SHORT).show();
					}
					notifyDataSetChanged();

				}
				if (checkClick){
					ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
					notifyDataSetChanged();

					Recipe unFavRecipe = listRecipe.get(position);
					Toast.makeText(context.getApplicationContext(), "Not fav: " + unFavRecipe.getName(), Toast.LENGTH_SHORT).show();

					favoriteDAO.delete(unFavRecipe.getId());
				}
				else if (!checkClick) {

					ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
					Recipe favRecipe = listRecipe.get(position);
					notifyDataSetChanged();

					Toast.makeText(context.getApplicationContext(), "Fav: " + favRecipe.getName(), Toast.LENGTH_SHORT).show();

					long id = favoriteDAO.create(favRecipe);
				}
//				for (Recipe r : listTemp){
//					if (listRecipe.get(position).getId() == r.getId()){
//						ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
//						notifyDataSetChanged();
//						break;
//					}
//				}



//				Log.d("listRecipe.get(position)", String.valueOf(listRecipe.get(position)));

//				favRecipe.setId(listRecipe.get(position).getId());
//
//

				notifyDataSetChanged();
			}
		});

		// Thread
		Runnable RecipeImageLoadThread = new RecipeImageLoadThread(listRecipe, ivFoodImg, position, context);
		new Thread(RecipeImageLoadThread).start();

		// Set data
		tvFoodName.setText(listRecipe.get(position).getName());
		tvTag.setText(listRecipe.get(position).getTag());

		notifyDataSetChanged();
		return convertView;

	}

}
