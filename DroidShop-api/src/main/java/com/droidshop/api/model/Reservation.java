package com.droidshop.api.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.droidshop.api.model.user.Customer;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "reservation")
@EqualsAndHashCode(callSuper = true)
public class Reservation extends AbstractEntity
{
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Customer customer;
	
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Product product;
	
	private int qty;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime reservationDateTime;
	
	private Status status;

	public Customer getCustomer()
	{
		return customer;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public int getQty()
	{
		return qty;
	}

	public void setQty(int qty)
	{
		this.qty = qty;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public DateTime getReservationDateTime()
	{
		return reservationDateTime;
	}

	public void setReservationDateTime(DateTime reservationDateTime)
	{
		this.reservationDateTime = reservationDateTime;
	}

	public enum Status
	{
		PENDING, FULLFILLED
	}

}
