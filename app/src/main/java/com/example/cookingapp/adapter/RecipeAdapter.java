package com.example.cookingapp.adapter;

import static java.lang.String.valueOf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingapp.MainActivity;
import com.example.cookingapp.PersonalRecipeDetailActivity;
import com.example.cookingapp.R;
import com.example.cookingapp.RecipeDetailActivity;
import com.example.cookingapp.db.DAO;
import com.example.cookingapp.db.DBHelper;
import com.example.cookingapp.db.FavoriteDAO;
import com.example.cookingapp.db.RecipeDAO;
import com.example.cookingapp.model.PersonalRecipe;
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

	private PopupMenu popupMenu;

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
		TextView tvFoodName, tvTag;
		ImageView ivFoodImg, ivMenu, ivFavorite;

		// Assign new databaseHelper and DAO
		dbHelper 	= new DBHelper((MainActivity) context);
		favoriteDAO = new FavoriteDAO(dbHelper);

		// Find item by id
		tvFoodName 	= convertView.findViewById(R.id.tvFoodName);
		tvTag 		= convertView.findViewById(R.id.tvTag);
		ivFoodImg 	= convertView.findViewById(R.id.ivFoodImg);
		ivMenu 		= convertView.findViewById(R.id.ivMenu);
		ivFavorite 	= convertView.findViewById(R.id.ivFavorite);

		// Config strictMode
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

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

		// On fav icon click
		ivFavorite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// checkClick variable to check weather an item is in fav list database or not
				boolean checkClick = false;
				for (Recipe r : listTemp){
					// There was no match
					if (listRecipe.get(position).getId() != r.getId()){
						notifyDataSetChanged();
					}
					// There was a match - break
					else if(listRecipe.get(position).getId() == r.getId()) {
						checkClick = true;
						break;
					}
					notifyDataSetChanged();

				}

				// Update - There was a match - Delete the item out of the database
				if (checkClick){
					// Change icon to empty favorite icon
					ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
					notifyDataSetChanged();

					Recipe unFavRecipe = listRecipe.get(position);
					Toast.makeText(context.getApplicationContext(), "Remove favorite: " + unFavRecipe.getName(), Toast.LENGTH_SHORT).show();

					favoriteDAO.delete(unFavRecipe.getId());
				}
				// Update - There wasn't a match - Add the item into the database
				else if (!checkClick) {
					// Change icon to selected favorite icon
					ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
					Recipe favRecipe = listRecipe.get(position);
					notifyDataSetChanged();

					Toast.makeText(context.getApplicationContext(), "Fav: " + favRecipe.getName(), Toast.LENGTH_SHORT).show();

					long id = favoriteDAO.create(favRecipe);
				}
				notifyDataSetChanged();
			}
		});

		// Utilizing thread to help loading image faster
		Runnable RecipeImageLoadThread = new RecipeImageLoadThread(listRecipe, ivFoodImg, position, context);
		new Thread(RecipeImageLoadThread).start();

		// On image view menu click
		ivMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Create popup menu
				popupMenu = new PopupMenu(context.getApplicationContext(), ivMenu);
				popupMenu.getMenuInflater().inflate(R.menu.popup_menu_main,popupMenu.getMenu());

				// pop up extra menu
				popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem menuItem) {
						switch (menuItem.getItemId()){
							case R.id.popup_menu_action1:

								Log.d("listRecipe.get(position)", String.valueOf(listRecipe.get(position)));
								notifyDataSetChanged();

								Toast.makeText(context, "List view item clicked", Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(context, RecipeDetailActivity.class);
								intent.putExtra("recipe", listRecipe.get(position));
								context.startActivity(intent);
								break;
						}
						return false;
					}
				});
				popupMenu.show();
			}
		});

		// Set data
		tvFoodName.setText(listRecipe.get(position).getName());
		tvTag.setText(listRecipe.get(position).getTag());

		notifyDataSetChanged();
		return convertView;

	}

}
