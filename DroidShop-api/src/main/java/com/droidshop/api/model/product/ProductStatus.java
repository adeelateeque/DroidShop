package com.droidshop.api.model.product;

public enum ProductStatus
{
	INSTOCK("INSTOCK"), OUTOFSTOCK("OUTOFSTOCK");

	private String statusCode;

	private ProductStatus(String statusCode)
	{
		this.statusCode = statusCode;
	}

	@Override
	public String toString()
	{
		return this.statusCode;
	}
}