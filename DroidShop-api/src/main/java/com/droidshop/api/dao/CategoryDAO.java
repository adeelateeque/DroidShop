package com.droidshop.api.dao;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.droidshop.api.model.Category;

@Repository
public class CategoryDAO extends AbstractDAO<Category>
{

	public CategoryDAO()
	{
		super(Category.class);
	}

	public Category add(Category category) throws NoSuchAlgorithmException
	{
		System.out.println("CategoryDAO: add");
		System.out.println("CategoryDAO: START - adding category to the database");

		category.setCreatedAt(new Date());
		category.setUpdatedAt(new Date());

		save(category);

		System.out.println("CategoryDAO: END - adding category to the database");

		return category;
	}

	public Category update(Long categoryID, Category category) throws NoSuchAlgorithmException
	{
		System.out.println("CategoryDAO: update");
		System.out.println("CategoryDAO: START - updating category to the database");

		Category fetchedCategory = fetch(categoryID);

		fetchedCategory.setUpdatedAt(new Date());

		update(fetchedCategory);
		System.out.println("CategoryDAO: END - updating category to the database");

		return fetchedCategory;
	}

	public Category fetch(Long categoryID)
	{
		System.out.println("CategoryDAO: fetch");
		System.out.println("CategoryDAO: START - fetching category from the database by category ID");

		Category fetchedCategory = find(categoryID);
		System.out.println("CategoryDAO: END - fetching category from the database by category ID");

		return fetchedCategory;
	}

	public Category fetchByCategoryName(String categoryName)
	{
		System.out.println("CategoryDAO: fetchByCategoryName");
		System.out.println("CategoryDAO: START - fetching category from the database by categoryName");

		Category fetchedCategory = (Category) getCurrentSession().createCriteria(Category.class)
				.add(Restrictions.eq("categoryName", categoryName)).uniqueResult();
		System.out.println("CategoryDAO: END - fetching category from the database by categoryName");

		return fetchedCategory;
	}

	public List<Category> fetchAll(boolean includeAll)
	{
		System.out.println("CategoryDAO: fetchAll");
		System.out.println("CategoryDAO: START - fetching all categorys from the database");

		List<Category> fetchedCategorys = getAll();

		System.out.println("DEBUG: includeAll [" + includeAll + "]");
		if (includeAll == false)
		{
			System.out.println("CategoryDAO: removing non-active categories");
			Iterator<Category> iterator = fetchedCategorys.iterator();
			while (iterator.hasNext())
			{
				Category currentCategory = (Category) iterator.next();
				Category.Status categoryStatus = currentCategory.getStatus();
				System.out.println("DEBUG: Category Status Code [" + categoryStatus + "]");
				if (!categoryStatus.equals(Category.Status.ENABLED))
				{
					System.out.println("DEBUG: Removing Category [" + currentCategory.getName() + "]");
					iterator.remove();
				}
			}
		}
		else
		{
			System.out.println("CategoryDAO: including all categorys");
		}
		System.out.println("CategoryDAO: END - fetching all categorys from the database");

		return fetchedCategorys;
	}

	public Category delete(Long categoryID)
	{
		System.out.println("CategoryDAO: delete");
		System.out.println("CategoryDAO: START - setting category status to delete in the database");

		Category fetchedCategory = fetch(categoryID);
		fetchedCategory.setUpdatedAt(new Date());
		update(fetchedCategory);
		System.out.println("CategoryDAO: END - setting category status to delete in the database");

		return fetchedCategory;
	}
}