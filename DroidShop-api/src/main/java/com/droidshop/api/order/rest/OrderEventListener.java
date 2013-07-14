package com.droidshop.api.order.rest;

import org.springframework.data.rest.repository.context.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import com.droidshop.api.model.Order;
import com.droidshop.api.order.OrderAlreadyPaidException;

/**
 * Event listener to reject {@code DELETE} requests to Spring Data REST.
 */
@Component
class OrderEventListener extends AbstractRepositoryEventListener<Order>
{

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.data.rest.repository.context.AbstractRepositoryEventListener#onBeforeDelete
	 * (java.lang.Object)
	 */
	@Override
	protected void onBeforeDelete(Order order)
	{
		if (order.isPaid())
		{
			throw new OrderAlreadyPaidException();
		}
	}
}
