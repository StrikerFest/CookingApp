package com.example.cookingapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cookingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeDAO implements DAO<Recipe> {
	private DBHelper dbHelper;

	public RecipeDAO(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	@Override
	public List<Recipe> all() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "SELECT * FROM recipe";
		Cursor cursor = db.rawQuery(sql, null);

		//
		List<Recipe> list = new ArrayList<>();
		if (cursor.moveToFirst()) {
			int idIndex = cursor.getColumnIndex("id");
			int nameIndex = cursor.getColumnIndex("name");
			int imageIndex = cursor.getColumnIndex("image");
			int tagIndex = cursor.getColumnIndex("tag");

			do {
				Recipe item = new Recipe();
//
				item.setId(cursor.getLong(idIndex));
				item.setName(cursor.getString(nameIndex));
				item.setImage(cursor.getString(imageIndex));
				item.setTag(cursor.getString(tagIndex));

				list.add(item);
			}
			while (cursor.moveToNext());
		}
		cursor.close();

		return list;
	}

	@Override
	public Recipe get(long id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "SELECT * FROM recipe WHERE id = " + id;
		Cursor cursor = db.rawQuery(sql, null);
		Recipe item = null;
		if (cursor.moveToFirst()) {
			int idIndex = cursor.getColumnIndex("id");
			int nameIndex = cursor.getColumnIndex("name");
			int ingredientIndex = cursor.getColumnIndex("ingredient");
			int tagIndex = cursor.getColumnIndex("tag");

			item = new Recipe();
			item.setId(cursor.getLong(idIndex));
			item.setName(cursor.getString(nameIndex));
			item.setImage(cursor.getString(ingredientIndex));
			item.setTag(cursor.getString(tagIndex));

			item.setId(cursor.getLong(idIndex));
			item.setName(cursor.getString(nameIndex));
			item.setImage(cursor.getString(ingredientIndex));
			item.setTag(cursor.getString(tagIndex));

		}
		cursor.close();

		return item;
	}

	@Override
	public long create(Recipe item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("name", item.getName());
		contentValues.put("image", item.getImage());
		contentValues.put("tag", item.getTag());

		// Get id of new recipe
		long id = db.insert("recipe", null, contentValues);

		return id;
	}

	@Override
	public int update(Recipe item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", item.getName());
		contentValues.put("ingredient", item.getImage());
		contentValues.put("tag", item.getTag());

		//
		int rs = db.update("recipe", contentValues, "id = " + item.getId(), null);
		return rs;
	}

	@Override
	public int delete(long id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rs = db.delete("recipe", "id = " + id, null);
		return 0;
	}
}
