package com.droidshop.api.category.rest;

import org.joda.time.DateTime;
import org.springframework.data.rest.repository.context.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import com.droidshop.api.model.Category;

@Component
public class CategoryEventListener extends AbstractRepositoryEventListener<Category>
{
	@Override
	protected void onBeforeCreate(Category entity)
	{
		entity.setCreatedAt(new DateTime());
	}

	@Override
	protected void onBeforeSave(Category entity)
	{
		entity.setUpdatedAt(new DateTime());
	}
}
