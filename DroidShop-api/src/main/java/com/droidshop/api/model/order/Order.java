package com.droidshop.api.model.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.droidshop.api.model.user.Customer;
import com.droidshop.api.model.user.User;

@Entity
@Table(name = "customer_order")
@NamedQueries({
		@NamedQuery(name = "Order.findAll", query = "SELECT c FROM Order c"),
		@NamedQuery(name = "Order.findById", query = "SELECT c FROM Order c WHERE c.id = :id"),
		@NamedQuery(name = "Order.findByStatus", query = "SELECT c FROM Order c, OrderStatus s WHERE c.orderStatus = s and s.status = :status"),
		@NamedQuery(name = "Order.findByStatusId", query = "SELECT c FROM Order c, OrderStatus s WHERE c.orderStatus = s and s.id = :id"),
		@NamedQuery(name = "Order.findByCustomerUserName", query = "SELECT c FROM Order c WHERE c.customer.userName = :userName"),
		@NamedQuery(name = "Order.findByAmount", query = "SELECT c FROM Order c WHERE c.amount = :amount"),
		@NamedQuery(name = "Order.findByDateCreated", query = "SELECT c FROM Order c WHERE c.dateCreated = :dateCreated") })
public class Order implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@JoinColumn(name = "customer_id", referencedColumnName = "userName")
	@ManyToOne(optional = false)
	private Customer customer;

	@Basic(optional = false)
	@NotNull
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customerOrder")
	private List<OrderDetail> orderDetails;

	@JoinColumn(name = "status_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private OrderStatus orderStatus;

	@Basic(optional = false)
	@DecimalMin(value = "0.1", message = "{c.order.amount}")
	@Column(name = "amount")
	private double amount;
	
	@Lob
	private String receiptURL;

	public Order()
	{
		this.customer = new Customer();
		this.dateCreated = new Date();
		this.orderDetails = new ArrayList<OrderDetail>();
		this.orderStatus = new OrderStatus();
	}

	public Order(Long id)
	{
		this.id = id;
	}

	public Order(Long id, double amount, Date dateCreated)
	{
		this.id = id;
		this.amount = amount;
		this.dateCreated = dateCreated;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public Date getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public OrderStatus getOrderStatus()
	{
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus)
	{
		this.orderStatus = orderStatus;
	}

	public Customer getCustomer()
	{
		return customer;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public void setCustomer(User user)
	{
		this.customer = (Customer) user;
	}

	public List<OrderDetail> getOrderDetails()
	{
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetailList)
	{
		this.orderDetails = orderDetailList;
	}

	public String getReceiptURL()
	{
		return receiptURL;
	}

	public void setReceiptURL(String receiptURL)
	{
		this.receiptURL = receiptURL;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += ((id != null) ? id.hashCode() : 0);

		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof Order))
		{
			return false;
		}

		Order other = (Order) object;

		if (((this.id == null) && (other.id != null)) || ((this.id != null) && !this.id.equals(other.id)))
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "model.shop.Order[id=" + id + "]";
	}
}
