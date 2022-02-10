package com.example.cookingapp.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cookingapp.R;
import com.example.cookingapp.model.Ingredient;
import com.example.cookingapp.model.Recipe;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class fragment1 extends Fragment {

	// Variable
	private TextView tvRecipeName,tvDescription;
	private List<Ingredient> listIngredient;
	private Recipe recipe;
	private ImageView ivRecipeImage;
	private ListView lvIngredientList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View RootView = inflater.inflate(R.layout.fragment_fragment1, container, false);
		tvRecipeName = (TextView) RootView.findViewById(R.id.tvRecipeName);
		ivRecipeImage = (ImageView) RootView.findViewById(R.id.ivRecipeImage);


		recipe = (Recipe) getActivity().getIntent().getSerializableExtra("recipe");
		tvRecipeName.setText(recipe.getName());

		// IMAGE VIEW - set imageView to API's url
		URL newURL = null;
		// Create new URL which take the api's image url
		try {
			newURL = new URL(recipe.getImage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// Download image from URL and get the value
		Bitmap mIcon_val = null;
		try {
			mIcon_val = BitmapFactory.decodeStream(newURL.openConnection().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set image view value
		ivRecipeImage.setImageBitmap(mIcon_val);
		return RootView;
	}
}