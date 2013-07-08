package com.droidshop.api.product.rest;

import org.joda.time.DateTime;
import org.springframework.data.rest.repository.context.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import com.droidshop.api.model.Product;


@Component
class ProductEventListener extends AbstractRepositoryEventListener<Product>
{
	@Override
	protected void onBeforeCreate(Product entity)
	{
		entity.setCreatedAt(new DateTime());
	}

	@Override
	protected void onBeforeSave(Product entity)
	{
		entity.setUpdatedAt(new DateTime());
	}
}