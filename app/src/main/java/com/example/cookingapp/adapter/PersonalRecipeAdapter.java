package com.example.cookingapp.adapter;

import static java.lang.String.valueOf;

import android.content.Context;
import android.content.Intent;
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

import com.example.cookingapp.CookbookActivity;
import com.example.cookingapp.MainActivity;
import com.example.cookingapp.PersonalRecipeDetailActivity;
import com.example.cookingapp.R;
import com.example.cookingapp.db.DAO;
import com.example.cookingapp.db.DBHelper;
import com.example.cookingapp.db.FavoriteDAO;
import com.example.cookingapp.db.PersonalRecipeDAO;
import com.example.cookingapp.dialog.RecipeDialog;
import com.example.cookingapp.dialog.RecipeUpdateDialog;
import com.example.cookingapp.model.PersonalRecipe;
import com.example.cookingapp.model.Recipe;
import com.example.cookingapp.thread.RecipeImageLoadThread;

import java.util.ArrayList;
import java.util.List;

public class PersonalRecipeAdapter extends BaseAdapter {

	// Variable
	private Context context;
	private List<PersonalRecipe> personalRecipeList;
	private ArrayList<PersonalRecipe> arraylist;
	private PopupMenu popupMenu;
	private RecipeUpdateDialog recipeDialog;

	private List<PersonalRecipe> listTemp;
	private Long idTemp;

	private DBHelper dbHelper;
	private DAO<PersonalRecipe> personalRecipeDAO;

	private TextView tvFoodName, tvIngredient, tvTag;
	ImageView ivFoodImg, ivMenu, ivFavorite;

	// Adapter constructor
	public PersonalRecipeAdapter(Context context, List<PersonalRecipe> personalRecipeList) {
		this.context = context;
		this.personalRecipeList = personalRecipeList;
		this.arraylist = new ArrayList<PersonalRecipe>();
		this.arraylist.addAll(personalRecipeList);
	}

	@Override
	public int getCount() {
		return personalRecipeList.size();
	}

	@Override
	public Object getItem(int position) {
		return personalRecipeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void remove(int position) {
		personalRecipeList.remove(personalRecipeList.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);

		// Assign new DBHelper and DAO
		dbHelper = new DBHelper((CookbookActivity) context);
		personalRecipeDAO = new PersonalRecipeDAO(dbHelper);

		// Find item by id
		tvFoodName 		= convertView.findViewById(R.id.tvFoodName);
		tvIngredient 	= convertView.findViewById(R.id.tvIngredient);
		tvTag			= convertView.findViewById(R.id.tvTag);
		ivFoodImg 		= convertView.findViewById(R.id.ivFoodImg);
		ivMenu 			= convertView.findViewById(R.id.ivMenu);
		ivFavorite 		= convertView.findViewById(R.id.ivFavorite);

		// Config strictMode
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Assign new update recipe dialog
		recipeDialog = new RecipeUpdateDialog(context) {
			@Override
			protected void passData(String name, String ingredient, String instruction, String tag) {
				personalRecipeList.set(position, new PersonalRecipe(name,ingredient,instruction,tag));
				notifyDataSetChanged();
			}
		};

		// On menu button-looking image click
		ivMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Create popup menu
				popupMenu = new PopupMenu(context.getApplicationContext(), ivMenu);
				popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());

				// pop up extra menu
				popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem menuItem) {
						switch (menuItem.getItemId()){
							case R.id.popup_menu_action1:

								// Get all item from personalRecipe database
								personalRecipeList = personalRecipeDAO.all();
								notifyDataSetChanged();

								Intent intentDetail = new Intent(context, PersonalRecipeDetailActivity.class);
								intentDetail.putExtra("recipe", personalRecipeList.get(position));
								context.startActivity(intentDetail);
								break;
							case R.id.popup_menu_action2:

								// Create dialog
								recipeDialog.show();
								break;
							case R.id.popup_menu_action3:

								// Delete item from the database - then from the current list
								PersonalRecipe item = personalRecipeList.get(position);
								personalRecipeDAO.delete(item.getId());
								remove(position);
								notifyDataSetChanged();
								break;
						}
						return false;
					}
				});

				// Display popupMenu
				popupMenu.show();
			}
		});

		// Set data
		tvFoodName.setText(personalRecipeList.get(position).getName());
		tvTag.setText(personalRecipeList.get(position).getTag());
		ivFoodImg.setImageResource(R.drawable.ic_baseline_fastfood_24);

		notifyDataSetChanged();
		return convertView;

	}

}
