package com.droidshop.api.dao;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.droidshop.api.model.order.Order;

@Component
public class OrderDAO extends AbstractDAO<Order>
{
	public OrderDAO()
	{
		super(Order.class);
	}

	public Order add(Order order) throws NoSuchAlgorithmException
	{
		System.out.println("OrderDAO: add");
		System.out.println("OrderDAO: START - adding order to the database");

		beginTransaction();
		save(order);
		commitAndCloseTransaction();

		System.out.println("OrderDAO: END - adding order to the database");

		return order;
	}

	public Order update(Long orderID, Order order) throws NoSuchAlgorithmException
	{
		System.out.println("OrderDAO: update");
		System.out.println("OrderDAO: START - updating order to the database");

		beginTransaction();
		Order fetchedOrder = fetch(orderID);
		fetchedOrder.setDateCreated(new Date());

		update(fetchedOrder);
		commitAndCloseTransaction();
		System.out.println("OrderDAO: END - updating order to the database");

		return fetchedOrder;
	}

	public Order fetch(Long orderID)
	{
		System.out.println("OrderDAO: fetch");
		System.out.println("OrderDAO: START - fetching order from the database by order ID");

		Order fetchedOrder = find(orderID);
		System.out.println("OrderDAO: END - fetching order from the database by order ID");

		return fetchedOrder;
	}

	public Order fetchByUserName(String userName)
	{
		System.out.println("OrderDAO: fetchByUserName");
		System.out.println("OrderDAO: START - fetching order from the database by ordername");

		beginTransaction();
		Order fetchedOrder = (Order) session.createCriteria(Order.class).add(Restrictions.eq("userName", userName))
				.uniqueResult();
		System.out.println("OrderDAO: END - fetching order from the database by ordername");
		closeTransaction();

		return fetchedOrder;
	}

	public List<Order> fetchAll(boolean includeAll)
	{
		System.out.println("OrderDAO: fetchAll");
		System.out.println("OrderDAO: START - fetching all orders from the database");

		beginTransaction();
		List<Order> fetchedOrders = getAll();

		System.out.println("DEBUG: includeAll [" + includeAll + "]");
		if (includeAll == false)
		{
			/*
			 * System.out.println("OrderDAO: removing non-active orders");
			 * Iterator<Order> iterator = fetchedOrders.iterator();
			 * while (iterator.hasNext())
			 * {
			 * Order currentOrder = (Order) iterator.next();
			 * String orderStatus = currentOrder.getOrderStatus().getStatus();
			 * System.out.println("DEBUG: Order Status Code [" + orderStatus + "]");
			 * if (!orderStatus.equals(OrderStatus))
			 * {
			 * System.out.println("DEBUG: Removing Order [" + currentOrder.getUserName() + "]");
			 * iterator.remove();
			 * }
			 * }
			 */
		}
		else
		{
			System.out.println("OrderDAO: including all orders");
		}
		System.out.println("OrderDAO: END - fetching all orders from the database");

		return fetchedOrders;
	}

	public Order delete(Long orderID)
	{
		System.out.println("OrderDAO: delete");
		System.out.println("OrderDAO: START - setting order status to delete in the database");

		beginTransaction();
		Order fetchedOrder = fetch(orderID);
		update(fetchedOrder);
		commitAndCloseTransaction();
		System.out.println("OrderDAO: END - setting order status to delete in the database");

		return fetchedOrder;
	}
}
