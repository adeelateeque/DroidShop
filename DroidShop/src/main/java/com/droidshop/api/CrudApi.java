package com.droidshop.api;
import java.util.List;


public interface CrudApi<T>
{
	long save(T entity);
	boolean update(T entity);
	boolean delete(T entity);
	T findById(Long id);
	List<T> getAll();
	List<T> getAll(int count);
}
