package com.example.cookingapp.db;

import java.util.List;

public interface DAO <T>{
	public List<T> all();
	public T get(long id);
	public long create(T item);
	public int update(T item);
	public int delete(long id);
}
