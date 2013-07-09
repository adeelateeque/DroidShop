package com.droidshop.api.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.droidshop.api.model.Order;
import com.droidshop.api.model.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customer")
public class Customer extends User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private List<Order> orders;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private List<Reservation> reservations;

	public Customer()
	{
		this.orders = new ArrayList<Order>();
	}

	public Customer(String userName)
	{
		this.userName = userName;
		this.orders = new ArrayList<Order>();
	}

	public Customer(String userName, String firstname, String lastname, String email, String address, String city)
	{
		this.userName = userName;
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.address = address;
		this.city = city;
		this.orders = new ArrayList<Order>();
		this.reservations = new ArrayList<Reservation>();
	}

	public List<Order> getOrders()
	{
		return orders;
	}

	public void setOrders(List<Order> orders)
	{
		this.orders = orders;
	}

	public List<Reservation> getReservations()
	{
		return reservations;
	}

	public void setReservations(List<Reservation> reservations)
	{
		this.reservations = reservations;
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