package com.example.cookingapp.adapter;

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

import com.example.cookingapp.R;
import com.example.cookingapp.model.Recipe;

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
		ImageView ivFoodImg, ivMenu;

		// Find item by id
		tvFoodName = convertView.findViewById(R.id.tvFoodName);
		tvIngredient = convertView.findViewById(R.id.tvIngredient);
		tvTag = convertView.findViewById(R.id.tvTag);

		ivFoodImg = convertView.findViewById(R.id.ivFoodImg);
		ivMenu = convertView.findViewById(R.id.ivMenu);

		// On click

		// Pop up menu

		// Dialog

		// Pop up extra

		// Config strictMode
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Download and set image from API
		URL newURL = null;
		try {
			newURL = new URL(listRecipe.get(position).getImage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Bitmap mIcon_val = null;
		try {
			mIcon_val = BitmapFactory.decodeStream(newURL.openConnection().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ivFoodImg.setImageBitmap(mIcon_val);

		// Set data
		tvFoodName.setText(listRecipe.get(position).getName());
		tvTag.setText(listRecipe.get(position).getTag());

		notifyDataSetChanged();
		return convertView;

	}

}
