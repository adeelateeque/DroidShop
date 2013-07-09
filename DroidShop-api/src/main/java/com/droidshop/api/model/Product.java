package com.droidshop.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.POST;

import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Entity
@Table(name = "product")
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractEntity
{
	@NotNull(groups = POST.class, message = "name: Missing Required Field")
	@Column(nullable = false)
	@Basic(optional = false)
	private String name;

	private int quantity;

	private MonetaryAmount price;

	@ManyToMany
	private List<Category> categories;

	@Basic(optional = true)
	@Size(min = 3, max = 5000, message = "{product.description}")
	@Column(name = "description", nullable = true, length = 45)
	@Lob
	private String description;

	@ElementCollection(targetClass = String.class)
	@CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
	private List<String> images;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "product_review", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id"))
	protected List<Review> reviews;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime createdAt;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime updatedAt;

	@Enumerated(EnumType.STRING)
	private Status status;

	protected Product()
	{
		this.reviews = new ArrayList<Review>();
		this.categories = new ArrayList<Category>();
	}

	public Product(String name, MonetaryAmount price)
	{
		this(name, 1, price);
	}

	/**
	 * @param name
	 * @param quantity
	 * @param size
	 * @param cost
	 */
	public Product(String name, int quantity, MonetaryAmount price)
	{
		this();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.reviews = new ArrayList<Review>();
		this.categories = new ArrayList<Category>();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public MonetaryAmount getPrice()
	{
		return price;
	}

	public void setPrice(MonetaryAmount price)
	{
		this.price = price;
	}

	public List<Category> getCategories()
	{
		return categories;
	}

	public void setCategories(List<Category> categories)
	{
		this.categories = categories;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<String> getImages()
	{
		return images;
	}

	public void setImages(List<String> images)
	{
		this.images = images;
	}

	public List<Review> getReviews()
	{
		return reviews;
	}

	public void setReviews(List<Review> reviews)
	{
		this.reviews = reviews;
	}

	public DateTime getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(DateTime createdAt)
	{
		this.createdAt = createdAt;
	}

	public DateTime getUpdatedAt()
	{
		return updatedAt;
	}

	public void setUpdatedAt(DateTime updatedAt)
	{
		this.updatedAt = updatedAt;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public enum Status
	{
		INSTOCK, OUTOFSTOCK;
	}
}
