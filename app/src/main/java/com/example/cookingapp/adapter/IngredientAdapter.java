package com.example.cookingapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookingapp.R;
import com.example.cookingapp.model.Ingredient;
import com.example.cookingapp.model.Recipe;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IngredientAdapter extends BaseAdapter {

	// Variable
	private Context context;
	private List<Ingredient> listIngredient;
	private ArrayList<Ingredient> arraylist;

	// Adapter constructor
	public IngredientAdapter(Context context, List<Ingredient> listIngredient) {
		this.context = context;
		this.listIngredient = listIngredient;
		this.arraylist = new ArrayList<Ingredient>();
		this.arraylist.addAll(listIngredient);
	}

	@Override
	public int getCount() {
		return listIngredient.size();
	}

	@Override
	public Object getItem(int position) {
		return listIngredient.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void remove(int position){
		listIngredient.remove(listIngredient.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.ingredient_item,parent,false);

		// Set values
		TextView tvIngredientName,tvIngredientAmount,tvIngredientDescription;
		ImageView ivIngredientImage;

		// Find item by id
		tvIngredientName = convertView.findViewById(R.id.tvIngredientName);
		tvIngredientAmount = convertView.findViewById(R.id.tvIngredientAmount);

		ivIngredientImage = convertView.findViewById(R.id.ivIngredientImage);

		// On click

		// Pop up menu

		// Dialog

		// Pop up extra

		// Config strict mode to remove guard
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Download and set image
		URL newURL = null;
		try {
			newURL = new URL(listIngredient.get(position).getImage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Bitmap mIcon_val = null;
		try {
			mIcon_val = BitmapFactory.decodeStream(newURL.openConnection().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ivIngredientImage.setImageBitmap(mIcon_val);

		// Set data
		tvIngredientName.setText(listIngredient.get(position).getName());
		tvIngredientAmount.setText(listIngredient.get(position).getAmount());

		notifyDataSetChanged();
		return convertView;

	}

}
