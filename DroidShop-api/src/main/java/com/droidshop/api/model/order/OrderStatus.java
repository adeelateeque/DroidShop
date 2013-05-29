package com.droidshop.api.model.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "order_status")
@NamedQueries({ @NamedQuery(name = "OrderStatus.findAll", query = "SELECT o FROM OrderStatus o"),
		@NamedQuery(name = "OrderStatus.findById", query = "SELECT o FROM OrderStatus o WHERE o.id = :id"),
		@NamedQuery(name = "OrderStatus.findByStatus", query = "SELECT o FROM OrderStatus o WHERE o.status = :status") })
public class OrderStatus implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orderStatus")
	private List<Order> customerOrders;

	@Basic(optional = true)
	@Size(min = 0, max = 200, message = "Description has maximum of 200 characters")
	@Column(name = "description")
	@Lob
	private String description;

	@Basic(optional = false)
	@Column(name = "status")
	@Size(min = 3, max = 45, message = "{order.status}")
	private String status;

	public OrderStatus()
	{
		this.customerOrders = new ArrayList<Order>();
	}

	public OrderStatus(Long id)
	{
		this.id = id;
	}

	public OrderStatus(Long id, String status)
	{
		this.id = id;
		this.status = status;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public List<Order> getCustomerOrders()
	{
		return customerOrders;
	}

	public void setCustomerOrders(List<Order> customerOrderList)
	{
		this.customerOrders = customerOrderList;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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
		if (!(object instanceof OrderStatus))
		{
			return false;
		}

		OrderStatus other = (OrderStatus) object;

		if (((this.id == null) && (other.id != null)) || ((this.id != null) && !this.id.equals(other.id)))
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "model.shop.OrderStatus[id=" + id + "]";
	}

}
