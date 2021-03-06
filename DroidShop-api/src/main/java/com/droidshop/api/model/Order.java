package com.droidshop.api.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.ToString;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.droidshop.api.model.user.Customer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.JoinTable;

@Entity
@Table(name = "customer_order")
@ToString(exclude = "products")
@NamedQueries({
		@NamedQuery(name = "Order.findAll", query = "SELECT c FROM Order c"),
		@NamedQuery(name = "Order.findById", query = "SELECT c FROM Order c WHERE c.id = :id"),
		@NamedQuery(name = "Order.findByCustomerUserName", query = "SELECT c FROM Order c WHERE c.customer.userName = :userName")})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Order extends AbstractEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime orderedDate;

	@ManyToMany
	@JoinTable(name = "customer_order_product")
	private Set<Product> products = new HashSet<Product>();

	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Customer customer;

	@Lob
	private String receiptURL;

	/**
	 * Creates a new {@link Order} for the given {@link Product}s and {@link Location}.
	 * 
	 * @param products
	 *            must not be {@literal null}.
	 * @param location
	 */
	public Order(Collection<Product> products)
	{
		this.status = Status.PAYMENT_EXPECTED;
		this.products.addAll(products);
		this.orderedDate = new DateTime();
	}

	public Order()
	{
		this(new Product[0]);
		this.customer = new Customer();
	}

	public Order(Product product)
	{
		this(Arrays.asList(product));
	}

	public Order(Long id)
	{
		this.id = id;
	}

	/**
	 * Creates a new {@link Order} containing the given {@link Product}s.
	 * 
	 * @param products
	 *            must not be {@literal null}.
	 */
	public Order(Product[] items)
	{
		this(Arrays.asList(items));
	}

	/**
	 * Returns the price of the {@link Order} calculated from the contained products.
	 * 
	 * @return will never be {@literal null}.
	 */
	public MonetaryAmount getPrice()
	{
		MonetaryAmount result = MonetaryAmount.ZERO;

		for (Product product : products)
		{
			result = result.add(product.getPrice());
		}

		return result;
	}

	/**
	 * Marks the {@link Order} as payed.
	 */
	public void markPaid()
	{

		if (isPaid())
		{
			throw new IllegalStateException("Already paid order cannot be paid again!");
		}

		this.status = Status.PAID;
	}

	/**
	 * Marks the {@link Order} as in preparation.
	 */
	public void markInPreparation()
	{

		if (this.status != Status.PAID)
		{
			throw new IllegalStateException(String.format("Order must be in state payed to start preparation! "
					+ "Current status: %s", this.status));
		}

		this.status = Status.PREPARING;
	}

	/**
	 * Marks the {@link Order} as prepared.
	 */
	public void markPrepared()
	{

		if (this.status != Status.PREPARING)
		{
			throw new IllegalStateException(String.format("Cannot mark Order prepared that is currently not "
					+ "preparing! Current status: %s.", this.status));
		}

		this.status = Status.READY;
	}

	/**
	 * Returns whether the {@link Order} has been paid already.
	 * 
	 * @return
	 */
	public boolean isPaid()
	{
		return !this.status.equals(Status.PAYMENT_EXPECTED);
	}

	/**
	 * Returns if the {@link Order} is ready to be taken.
	 * 
	 * @return
	 */
	public boolean isReady()
	{
		return this.status.equals(Status.READY);
	}

	public boolean isTaken()
	{
		return this.status.equals(Status.TAKEN);
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Customer getCustomer()
	{
		return customer;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public String getReceiptURL()
	{
		return receiptURL;
	}

	public void setReceiptURL(String receiptURL)
	{
		this.receiptURL = receiptURL;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public DateTime getOrderedDate()
	{
		return orderedDate;
	}

	public void setOrderedDate(DateTime orderedDate)
	{
		this.orderedDate = orderedDate;
	}

	public Set<Product> getItems()
	{
		return products;
	}

	public void setItems(Set<Product> products)
	{
		this.products = products;
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

	/**
	 * Enumeration for all the statuses an {@link Order} can be in.
	 */
	public static enum Status
	{

		/**
		 * Placed, but not payed yet. Still changeable.
		 */
		PAYMENT_EXPECTED,

		/**
		 * {@link Order} was payed. No changes allowed to it anymore.
		 */
		PAID,

		/**
		 * The {@link Order} is currently processed.
		 */
		PREPARING,

		/**
		 * The {@link Order} is ready to be picked up by the customer.
		 */
		READY,

		/**
		 * The {@link Order} was completed.
		 */
		TAKEN;
	}
}
