package com.droidshop.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class Product extends AbstractEntity {
	private String name;
	private int quantity;
	private MonetaryAmount price;

	public boolean selected;

	private List<Category> categories;

	private String description;

	public List<String> images;

	protected List<Review> reviews;

	private DateTime createdAt;

	private DateTime updatedAt;

	private Status status;

	public Product() {
		this.reviews = new ArrayList<Review>();
		this.categories = new ArrayList<Category>();
	}

	public Product(String name, MonetaryAmount price) {
		this(name, 1, price);
	}

	/**
	 * @param name
	 * @param quantity
	 * @param size
	 * @param cost
	 */
	public Product(String name, int quantity, MonetaryAmount price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.reviews = new ArrayList<Review>();
		this.categories = new ArrayList<Category>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public MonetaryAmount getPrice() {
		return price;
	}

	public void setPrice(MonetaryAmount price) {
		this.price = price;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		INSTOCK, OUTOFSTOCK;
	}

}