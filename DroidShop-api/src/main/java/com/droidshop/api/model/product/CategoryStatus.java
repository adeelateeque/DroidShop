package com.droidshop.api.model.product;

public enum CategoryStatus
{
	ENABLED("E"), DISABLED("D");

	private String statusCode;

	private CategoryStatus(String statusCode)
	{
		this.statusCode = statusCode;
	}

	public String getStatusCode()
	{
		return this.statusCode;
	}
}