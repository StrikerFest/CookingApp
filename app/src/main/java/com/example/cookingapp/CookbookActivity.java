package com.example.cookingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cookingapp.adapter.VPAdapter;
import com.example.cookingapp.fragment.FavoriteDish;
import com.example.cookingapp.fragment.PersonalCookbook;
import com.example.cookingapp.fragment.fragment1;
import com.example.cookingapp.fragment.fragment2;
import com.example.cookingapp.fragment.fragment3;
import com.example.cookingapp.model.Ingredient;
import com.example.cookingapp.model.Recipe;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class CookbookActivity extends AppCompatActivity {
	// Variable
	private TextView tvRecipeName,tvDescription;
	private List<Ingredient> listIngredient;
	private Recipe recipe;
	private ImageView ivRecipeImage;
	private ListView lvIngredientList;

	private TabLayout tabLayout;
	private ViewPager viewPager;

	// Initial UI - get view
	private void initUI() {

		// TabLayout
		tabLayout = findViewById(R.id.tlCookbook);

		// ViewPager
		viewPager = findViewById(R.id.vpCookbook);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cookbook);
		initUI();

		// TAB LAYOUT
		tabLayout.setupWithViewPager(viewPager);
		VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		vpAdapter.addFragment(new PersonalCookbook(),"Cookbook");
		vpAdapter.addFragment(new FavoriteDish(),"❤ Favorite ❤");

		viewPager.setAdapter(vpAdapter);
	}
}