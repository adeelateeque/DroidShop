package com.droidshop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.droidshop.model.Order;

public class Customer extends User implements Serializable
{
	private static final long serialVersionUID = 1L;

	private List<Order> customerOrders;

	public Customer()
	{
		this.customerOrders = new ArrayList<Order>();
	}

	public Customer(String userName)
	{
		this.userName = userName;
		this.customerOrders = new ArrayList<Order>();
	}

	public Customer(String userName, String firstname, String lastname, String email, String address, String city)
	{
		this.userName = userName;
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.address = address;
		this.city = city;
		this.customerOrders = new ArrayList<Order>();
	}

	public List<Order> getCustomerOrders()
	{
		return customerOrders;
	}

	public void setCustomerOrders(List<Order> customerOrderList)
	{
		this.customerOrders = customerOrderList;
	}

	@Override
	public String toString()
	{
		return "mode.shop.Customer[userName=" + userName + "]";
	}
}