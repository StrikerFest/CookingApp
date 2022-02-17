package com.example.cookingapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cookingapp.model.PersonalRecipe;
import com.example.cookingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class PersonalRecipeDAO implements DAO<PersonalRecipe> {
	private DBHelper dbHelper;

	public PersonalRecipeDAO(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	@Override
	public List<PersonalRecipe> all() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "SELECT * FROM personalRecipe";
		Cursor cursor = db.rawQuery(sql, null);

		//
		List<PersonalRecipe> list = new ArrayList<>();
		if (cursor.moveToFirst()) {
			int idIndex = cursor.getColumnIndex("id");
			int nameIndex = cursor.getColumnIndex("name");
			int ingredientIndex = cursor.getColumnIndex("ingredient");
			int instructionIndex = cursor.getColumnIndex("instruction");
			int tagIndex = cursor.getColumnIndex("tag");

			do {
				PersonalRecipe item = new PersonalRecipe();
//
				item.setId(cursor.getLong(idIndex));
				item.setName(cursor.getString(nameIndex));
				item.setIngredient(cursor.getString(ingredientIndex));
				item.setInstruction(cursor.getString(instructionIndex));
				item.setTag(cursor.getString(tagIndex));

				list.add(item);
			}
			while (cursor.moveToNext());
		}
		cursor.close();

		return list;
	}

	@Override
	public PersonalRecipe get(long id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "SELECT * FROM personalRecipe WHERE id = " + id;
		Cursor cursor = db.rawQuery(sql, null);
		PersonalRecipe item = null;
		if (cursor.moveToFirst()) {
			int idIndex = cursor.getColumnIndex("id");
			int nameIndex = cursor.getColumnIndex("name");
			int ingredientIndex = cursor.getColumnIndex("ingredient");
			int instructionIndex = cursor.getColumnIndex("instruction");
			int tagIndex = cursor.getColumnIndex("tag");

			item = new PersonalRecipe();
			item.setId(cursor.getLong(idIndex));
			item.setName(cursor.getString(nameIndex));
			item.setIngredient(cursor.getString(ingredientIndex));
			item.setInstruction(cursor.getString(instructionIndex));
			item.setTag(cursor.getString(tagIndex));

		}
		cursor.close();

		return item;
	}

	@Override
	public long create(PersonalRecipe item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("name", item.getName());
		contentValues.put("ingredient", item.getIngredient());
		contentValues.put("instruction", item.getInstruction());
		contentValues.put("tag", item.getTag());

		// Get id of new recipe
		long id = db.insert("personalRecipe", null, contentValues);

		return id;
	}

	@Override
	public int update(PersonalRecipe item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", item.getName());
		contentValues.put("ingredient", item.getIngredient());
		contentValues.put("instruction", item.getInstruction());
		contentValues.put("tag", item.getTag());

		//
		int rs = db.update("personalRecipe", contentValues, "id = " + item.getId(), null);
		return rs;
	}

	@Override
	public int delete(long id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rs = db.delete("personalRecipe", "id = " + id, null);
		return 0;
	}
}
