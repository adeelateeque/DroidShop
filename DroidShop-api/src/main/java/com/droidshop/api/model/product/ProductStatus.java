package com.droidshop.api.model.product;

public enum ProductStatus
{
	INSTOCK("I"), OUTOFSTOCK("O");

	private String statusCode;

	private ProductStatus(String statusCode)
	{
		this.statusCode = statusCode;
	}

	public String getStatusCode()
	{
		return this.statusCode;
	}
}