package com.droidshop.api.model.order;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderDetailPK implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "order_id")
	private Long orderId;
	@Basic(optional = false)
	@Column(name = "product_id")
	private Long productId;

	public OrderDetailPK()
	{
	}

	public OrderDetailPK(Long orderId, Long productId)
	{
		this.orderId = orderId;
		this.productId = productId;
	}

	public Long getOrderId()
	{
		return orderId;
	}

	public void setOrderId(Long orderId)
	{
		this.orderId = orderId;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (Long) orderId;
		hash += (Long) productId;

		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof OrderDetailPK))
		{
			return false;
		}

		OrderDetailPK other = (OrderDetailPK) object;

		if (this.orderId != other.orderId)
		{
			return false;
		}

		if (this.productId != other.productId)
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "model.shop.OrderDetailPK[orderId=" + orderId + ", productId=" + productId + "]";
	}
}
