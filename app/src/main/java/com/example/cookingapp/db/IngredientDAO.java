package com.example.cookingapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cookingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientDAO implements DAO<Ingredient> {
	private DBHelper dbHelper;

	public IngredientDAO(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	@Override
	public List<Ingredient> all() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "SELECT * FROM Ingredient";
		Cursor cursor = db.rawQuery(sql, null);

		//
		List<Ingredient> list = new ArrayList<>();
		if (cursor.moveToFirst()) {
			int idIndex = cursor.getColumnIndex("id");
			int nameIndex = cursor.getColumnIndex("name");
			int amountIndex = cursor.getColumnIndex("amount");
			int imageIndex = cursor.getColumnIndex("image");

			do {
				Ingredient item = new Ingredient();
//
				item.setId(cursor.getLong(idIndex));
				item.setName(cursor.getString(nameIndex));
				item.setAmount(cursor.getString(amountIndex));
				item.setImage(cursor.getString(imageIndex));

				list.add(item);
			}
			while (cursor.moveToNext());
		}
		cursor.close();

		return list;
	}

	@Override
	public Ingredient get(long id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "SELECT * FROM ingredient WHERE id = " + id;
		Cursor cursor = db.rawQuery(sql, null);
		Ingredient item = null;
		if (cursor.moveToFirst()) {
			int idIndex = cursor.getColumnIndex("id");
			int nameIndex = cursor.getColumnIndex("name");
			int amountIndex = cursor.getColumnIndex("amount");
			int imageIndex = cursor.getColumnIndex("image");

			item = new Ingredient();
			item.setId(cursor.getLong(idIndex));
			item.setName(cursor.getString(nameIndex));
			item.setImage(cursor.getString(imageIndex));
			item.setAmount(cursor.getString(amountIndex));

			item.setId(cursor.getLong(idIndex));
			item.setName(cursor.getString(nameIndex));
			item.setImage(cursor.getString(imageIndex));
			item.setAmount(cursor.getString(amountIndex));

		}
		cursor.close();

		return item;
	}

	@Override
	public long create(Ingredient item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("name", item.getName());
		contentValues.put("image", item.getImage());
		contentValues.put("amount", item.getAmount());

		// Get id of new Ingredient
		long id = db.insert("ingredient", null, contentValues);

		return id;
	}

	@Override
	public int update(Ingredient item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", item.getName());
		contentValues.put("ingredient", item.getImage());
		contentValues.put("amount", item.getAmount());

		//
		int rs = db.update("ingredient", contentValues, "id = " + item.getId(), null);
		return rs;
	}

	@Override
	public int delete(long id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rs = db.delete("ingredient", "id = " + id, null);
		return 0;
	}
}
