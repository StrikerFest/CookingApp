package com.example.cookingapp.fragment;

import android.content.Context;
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

import com.example.cookingapp.MainActivity;
import com.example.cookingapp.R;
import com.example.cookingapp.model.Ingredient;
import com.example.cookingapp.model.Recipe;
import com.squareup.picasso.Picasso;

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
	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View RootView = inflater.inflate(R.layout.fragment_fragment1, container, false);

		tvRecipeName = (TextView) RootView.findViewById(R.id.tvRecipeName);
		ivRecipeImage = (ImageView) RootView.findViewById(R.id.ivRecipeImage);

		recipe = (Recipe) getActivity().getIntent().getSerializableExtra("recipe");
		tvRecipeName.setText(recipe.getName());

        // Set image view value
		Picasso.with(((MainActivity) context))
				.load(recipe.getImage())
				.into(ivRecipeImage);
		return RootView;
	}
}