package com.droidshop.api.payment.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.ToString;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.droidshop.api.model.AbstractEntity;
import com.droidshop.api.model.Order;

/**
 * Baseclass for payment implementations.
 */
@Entity
@Table(name="payment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@ToString(callSuper = true)
public abstract class Payment extends AbstractEntity
{

	@OneToOne(cascade = CascadeType.MERGE)
	private Order order;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime paymentDate;

	public Payment()
	{
	}

	/**
	 * Creates a new {@link Payment} referring to the given {@link Order}.
	 * 
	 * @param order
	 *            must not be {@literal null}.
	 */
	public Payment(Order order)
	{
		Assert.notNull(order);
		this.order = order;
		this.paymentDate = new DateTime();
	}

	/**
	 * Returns a receipt for the {@link Payment}.
	 * 
	 * @return
	 */
	public Receipt getReceipt()
	{
		return new Receipt(order, paymentDate);
	}

	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}

	public DateTime getPaymentDate()
	{
		return paymentDate;
	}

	public void setPaymentDate(DateTime paymentDate)
	{
		this.paymentDate = paymentDate;
	}

	/**
	 * A receipt for an {@link Order} and a payment date.
	 */
	public static class Receipt
	{
		private Order order;
		private DateTime date;

		public Receipt(Order order, DateTime date)
		{
			this.order = order;
			this.date = date;
		}

		public Order getOrder()
		{
			return order;
		}

		public void setOrder(Order order)
		{
			this.order = order;
		}

		public DateTime getDate()
		{
			return date;
		}

		public void setDate(DateTime date)
		{
			this.date = date;
		}
	}
}
