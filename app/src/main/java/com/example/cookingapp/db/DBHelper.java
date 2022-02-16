package com.example.cookingapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "myDB";
	private static final int DB_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create table in Database

		String sql = "CREATE TABLE recipe(" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT NOT NULL," +
				"image TEXT NOT NULL," +
				"tag TEXT NOT NULL" +
				") ";

		db.execSQL(sql);

		// Create ingredient table
		sql = " CREATE TABLE ingredient(" +
				"idIngredient INTEGER PRIMARY KEY AUTOINCREMENT," +
				"idRecipe INTEGER NOT NULL," +
				"ingredientName TEXT NOT NULL," +
				"ingredientAmount TEXT NOT NULL," +
				"FOREIGN KEY(idRecipe)" +
				"REFERENCES recipe(id)" +
				")";

		db.execSQL(sql);



	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Update table
	}
}
