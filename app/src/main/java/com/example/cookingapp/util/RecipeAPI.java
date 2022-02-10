package com.example.cookingapp.util;

public class RecipeAPI {
//	private static String API_KEY = "B08d5MEe9PjMGplKfukQkim42Hk8vO74";

	// Get API with searchKey
	public static String createURL(String searchKey){
		return String.format("https://themealdb.com/api/json/v1/1/search.php?s=" + searchKey);
	}

	// Get API of category - with reduced data
	public static String createURLCategory(String category){
		return String.format("https://www.themealdb.com/api/json/v1/1/filter.php?c=" + category);
	}

	// Get API with id
	public static String createURLById(Long id){
		return String.format("https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + id);
	}
}
