package com.droidshop.api.payment;

import org.springframework.context.ApplicationEvent;

import com.droidshop.api.model.order.Order;

/**
 * {@link ApplicationEvent} to be thrown when an {@link Order} has been payed.
 */
public class OrderPaidEvent extends ApplicationEvent
{

	private static final long serialVersionUID = -6150362015056003378L;
	private long orderId;

	/**
	 * Creates a new {@link OrderPaidEvent}
	 * 
	 * @param orderId
	 *            the id of the order that just has been payed
	 * @param source
	 *            must not be {@literal null}.
	 */
	public OrderPaidEvent(long orderId, Object source)
	{
		super(source);
		this.orderId = orderId;
	}

	public long getOrderId()
	{
		return orderId;
	}

	public void setOrderId(long orderId)
	{
		this.orderId = orderId;
	}
}
