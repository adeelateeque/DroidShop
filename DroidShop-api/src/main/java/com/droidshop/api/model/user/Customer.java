package com.droidshop.api.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.droidshop.api.model.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customer")
public class Customer extends User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
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
	public int hashCode()
	{
		int hash = 0;
		hash += ((userName != null) ? userName.hashCode() : 0);

		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof Customer))
		{
			return false;
		}

		Customer other = (Customer) object;

		if (((this.userName == null) && (other.userName != null))
				|| ((this.userName != null) && !this.userName.equals(other.userName)))
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "mode.shop.Customer[userName=" + userName + "]";
	}
}