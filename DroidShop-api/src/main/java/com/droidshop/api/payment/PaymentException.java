package com.droidshop.api.payment;

import org.springframework.util.Assert;

import com.droidshop.api.model.Order;

public class PaymentException extends RuntimeException
{

	private static final long serialVersionUID = -4929826142920520541L;
	private Order order;

	public PaymentException(Order order, String message)
	{

		super(message);

		Assert.notNull(order);
		this.order = order;
	}

	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}
}
