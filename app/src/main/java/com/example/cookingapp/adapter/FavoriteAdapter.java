package com.example.cookingapp.adapter;

import static java.lang.String.valueOf;

import android.content.Context;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

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

	private AlertDialog alertDialog;
	private AlertDialog.Builder builder;

	private TextView tvFoodName, tvFoodDifficulty, tvFoodRating, tvIngredient, tvTag;
	private ImageView ivFoodImg, ivMenu, ivFavorite;

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

		// Initialize databaseHelper and DAO
		dbHelper = new DBHelper(context);
		favoriteDAO = new FavoriteDAO(dbHelper);

		// Assign view to variable
		tvFoodName = convertView.findViewById(R.id.tvFoodName);
		tvIngredient = convertView.findViewById(R.id.tvIngredient);
		tvTag = convertView.findViewById(R.id.tvTag);
		ivFoodImg = convertView.findViewById(R.id.ivFoodImg);
		ivMenu = convertView.findViewById(R.id.ivMenu);
		ivFavorite = convertView.findViewById(R.id.ivFavorite);


		// Config strictMode
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Get all item from fav food list
		listTemp = favoriteDAO.all();
		notifyDataSetChanged();

		// If the item at the current position don't match any item in the fav food list
		for (Recipe r : listTemp) {
			if (listRecipe.get(position).getId() != r.getId()) {
				// Set favorite to empty
				ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
				notifyDataSetChanged();
			}
		}

		// If the item at the current position  match any item in the fav food list
		for (Recipe r : listTemp) {
			if (listRecipe.get(position).getId() == r.getId()) {
				// Set favorite to selected
				ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
				notifyDataSetChanged();
				// If there is a match in the fav list, break immediately
				break;
			}
		}
		notifyDataSetChanged();

		// On favorite icon click
		ivFavorite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Check variable
				boolean checkClick = false;
				for (Recipe r : listTemp) {
					// If there wasn't any match in the fav list
					if (listRecipe.get(position).getId() != r.getId()) {
						notifyDataSetChanged();
					}
					// If there is a match in the fav list
					else if (listRecipe.get(position).getId() == r.getId()) {
						checkClick = true;
						break;
					}
					notifyDataSetChanged();

				}
				// If there wasn't a match in the fav list
				if (checkClick) {
					// Create new alert dialog to warn if user want to remove favorite item
					builder = new AlertDialog.Builder(context).
							setTitle("Remove current recipe?").
							setMessage("Do you want to remove this recipe off the favorite list").
							setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {
									// If yes, set fav icon back to empty
									ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);

									Recipe unFavRecipe = listRecipe.get(position);
									Toast.makeText(context.getApplicationContext(), "Remove favorite: " + unFavRecipe.getName(), Toast.LENGTH_SHORT).show();

									// Delete recipe of the favorite list
									favoriteDAO.delete(unFavRecipe.getId());
									notifyDataSetChanged();
								}

							}).setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							// Cancel
							Toast.makeText(context, "Cancel deletion", Toast.LENGTH_SHORT).show();
						}
					});

					// Build and show the dialog
					alertDialog = builder.create();
					alertDialog.show();


				} else if (!checkClick) {

					// If there is no match - Create a recipe in tne fav database - Set icon to selected
					ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
					Recipe favRecipe = listRecipe.get(position);

					Toast.makeText(context.getApplicationContext(), "Fav: " + favRecipe.getName(), Toast.LENGTH_SHORT).show();

					long id = favoriteDAO.create(favRecipe);
				}

				notifyDataSetChanged();
			}
		});

		// Refresh list
		listRecipe.clear();
		listRecipe = favoriteDAO.all();

		// Load image
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
