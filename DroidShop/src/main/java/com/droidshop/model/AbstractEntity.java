package com.droidshop.model;

public class AbstractEntity{

	protected Long id;

	protected AbstractEntity() {
		this.id = null;

	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
