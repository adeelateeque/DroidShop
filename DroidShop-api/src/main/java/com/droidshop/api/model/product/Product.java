package com.droidshop.api.model.product;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "product")
public class Product implements Serializable
{
	private static final long serialVersionUID = 1L;

	@NotNull(groups = { PUT.class, DELETE.class }, message = "id: Missing Required Field")
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(groups = POST.class, message = "name: Missing Required Field")
	@JsonProperty
	@Column(length = 30, nullable = false)
	@Basic(optional = false)
	private String name;

	@JsonIgnore
	@ManyToMany
	private List<Category> categories;

	@Basic(optional = false)
	@Size(min = 3, max = 5000, message = "{product.description}")
	@Column(name = "description", nullable = false, length = 45)
	@Lob
	private String description;

	@JsonIgnore
	@Lob
	private byte[] image;

	@Basic(optional = false)
	@DecimalMax(value = "1000000.00", message = "{product.price.max}")
	@Column(name = "price", nullable = false)
	protected double price;

	protected double rating;

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
	private ProductStatus status;

	public Product()
	{
		this.reviews = new ArrayList<Review>();
		this.categories = new ArrayList<Category>();
	}

	public Product(Long id)
	{
		this.id = id;
		this.reviews = new ArrayList<Review>();
		this.categories = new ArrayList<Category>();
	}

	public Product(Long id, String name, double price, String description)
	{
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.reviews = new ArrayList<Review>();
		this.categories = new ArrayList<Category>();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public double getRating()
	{
		return rating;
	}

	public void setRating(double rating)
	{
		this.rating = rating;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public BufferedImage getImage()
	{
		InputStream in = new ByteArrayInputStream(image);
		try
		{
			return ImageIO.read(in);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void setImage(BufferedImage image)
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try
		{
			ImageIO.write(image, "PNG" /* for instance */, out);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.image = out.toByteArray();
	}

	public List<Category> getCategories()
	{
		return categories;
	}

	public void setCategories(List<Category> categories)
	{
		this.categories = categories;
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

	public ProductStatus getStatus()
	{
		return status;
	}

	public void setStatus(ProductStatus status)
	{
		this.status = status;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += ((id != null) ? id.hashCode() : 0);

		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof Product))
		{
			return false;
		}

		Product other = (Product) object;

		if (((this.id == null) && (other.id != null)) || ((this.id != null) && !this.id.equals(other.id)))
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "Product{" + "id=" + id + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
	}
}
