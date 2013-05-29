package com.droidshop.api.model.product;

public enum CategoryStatus
{
	ENABLED("ENABLED"), DISABLED("DISABLED");

	private String statusCode;

	private CategoryStatus(String statusCode)
	{
		this.statusCode = statusCode;
	}

	@Override
	public String toString()
	{
		return this.statusCode;
	}
}
