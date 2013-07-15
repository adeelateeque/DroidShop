package com.droidshop.api;
import java.util.List;


public interface CrudApi<T>
{
	boolean save(T entity);
	boolean delete(T entity);
	T findById(Long id);
	List<T> getAll();
	List<T> getAll(int count);
}
