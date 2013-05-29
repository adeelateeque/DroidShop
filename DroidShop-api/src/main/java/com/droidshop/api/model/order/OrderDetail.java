package com.droidshop.api.model.order;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.droidshop.api.model.product.Product;

@Entity
@Table(name = "order_detail")
@NamedQueries({
		@NamedQuery(name = "OrderDetail.findAll", query = "SELECT o FROM OrderDetail o"),
		@NamedQuery(name = "OrderDetail.findByOrderId", query = "SELECT o FROM OrderDetail o WHERE o.orderDetailPK.orderId = :orderId"),
		@NamedQuery(name = "OrderDetail.findByProductId", query = "SELECT o FROM OrderDetail o WHERE o.orderDetailPK.productId = :productId"),
		@NamedQuery(name = "OrderDetail.findByQty", query = "SELECT o FROM OrderDetail o WHERE o.qty = :qty") })
public class OrderDetail implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	protected OrderDetailPK orderDetailPK;

	@JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private Order customerOrder;

	@JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private Product product;

	@Basic(optional = false)
	@Column(name = "qty")
	private int qty;

	public OrderDetail()
	{
		this.orderDetailPK = new OrderDetailPK();
		this.customerOrder = new Order();
		this.product = new Product();
	}

	public OrderDetail(OrderDetailPK orderDetailPK)
	{
		this.orderDetailPK = orderDetailPK;
	}

	public OrderDetail(OrderDetailPK orderDetailPK, int qty)
	{
		this.orderDetailPK = orderDetailPK;
		this.qty = qty;
	}

	public OrderDetail(Long orderId, Long productId)
	{
		this.orderDetailPK = new OrderDetailPK(orderId, productId);
	}

	public OrderDetailPK getOrderItem()
	{
		return orderDetailPK;
	}

	public void setOrderItem(OrderDetailPK orderDetailPK)
	{
		this.orderDetailPK = orderDetailPK;
	}

	public int getQty()
	{
		return qty;
	}

	public void setQty(int qty)
	{
		this.qty = qty;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public Order getCustomerOrder()
	{
		return customerOrder;
	}

	public void setCustomerOrder(Order customerOrder)
	{
		this.customerOrder = customerOrder;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += ((orderDetailPK != null) ? orderDetailPK.hashCode() : 0);

		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof OrderDetail))
		{
			return false;
		}

		OrderDetail other = (OrderDetail) object;

		if (((this.orderDetailPK == null) && (other.orderDetailPK != null))
				|| ((this.orderDetailPK != null) && !this.orderDetailPK.equals(other.orderDetailPK)))
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "model.shop.OrderDetail[orderDetailPK=" + orderDetailPK + "]";
	}
}