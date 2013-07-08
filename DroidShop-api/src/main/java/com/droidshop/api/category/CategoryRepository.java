package com.droidshop.api.category;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.droidshop.api.model.Category;

/**
 * Repository interface to manage {@link Category} instances.
 */
@RestResource(path="category", rel="category")
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>
{

}
