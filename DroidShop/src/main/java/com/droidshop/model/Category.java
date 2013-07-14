package com.droidshop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Category extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	private List<Product> products;

	private Date createdAt;

	private Date updatedAt;

	private Status status;

	public Category() {
		this.products = new ArrayList<Product>();
	}

	public Category(String name) {
		this.name = name;
		this.products = new ArrayList<Product>();
	}

	public Category(Long id) {
		this.id = id;
	}

	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> productList) {
		this.products = productList;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		ENABLED, DISABLED;
	}
}
