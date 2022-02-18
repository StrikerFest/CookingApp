package com.example.cookingapp.thread;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cookingapp.MainActivity;
import com.example.cookingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecipeImageLoadThread implements Runnable {
	private MainActivity mContext;
	private List<Recipe> listRecipe;
	private ImageView ivFoodImg;
	private Context context;
	private int position;

	public RecipeImageLoadThread(List<Recipe> listRecipe, ImageView ivFoodImg, int position, Context context) {
		this.listRecipe = listRecipe;
		this.ivFoodImg = ivFoodImg;
		this.position = position;
		this.context = context;
	}

	@Override
	public void run() {

		try {
			((MainActivity) context).runOnUiThread(new Runnable() {

				@Override
				public void run() {
						Picasso.with(((MainActivity) context))
								.load(listRecipe.get(position).getImage())
								.into(ivFoodImg);
				}
			});
		} catch (Exception e) {

		}
	}
}
