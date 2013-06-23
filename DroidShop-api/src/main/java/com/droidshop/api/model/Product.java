package com.droidshop.api.model;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.POST;

import lombok.EqualsAndHashCode;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "product")
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractEntity
{
	@NotNull(groups = POST.class, message = "name: Missing Required Field")
	@JsonProperty
	@Column(nullable = false)
	@Basic(optional = false)
	private String name;

	private int quantity;

	private MonetaryAmount price;

	@JsonIgnore
	@ManyToMany
	private List<Category> categories;

	@Basic(optional = true)
	@Size(min = 3, max = 5000, message = "{product.description}")
	@Column(name = "description", nullable = true, length = 45)
	@Lob
	private String description;

    @ElementCollection(targetClass=String.class)
	@CollectionTable(name = "product_images")
	private List<String> images;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "product_review", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id"))
	protected List<Review> reviews;

	@JsonProperty("created_at")
	@Temporal(TIMESTAMP)
	private Date createdAt;

	@JsonProperty("updated_at")
	@Temporal(TIMESTAMP)
	private Date updatedAt;

	@JsonProperty
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

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt()
	{
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt)
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
