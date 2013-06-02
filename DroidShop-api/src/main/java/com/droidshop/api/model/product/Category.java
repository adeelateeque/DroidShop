package com.droidshop.api.model.product;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
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
@Table(name = "category")
public class Category implements Serializable
{
	private static final long serialVersionUID = 1L;

	@NotNull(groups = { PUT.class, DELETE.class }, message = "id: Missing Required Field")
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(groups = POST.class, message = "name: Missing Required Field")
	@JsonProperty
	@Column(length = 30, nullable = false, unique = true)
	@Basic(optional = false)
	private String name;

	@ManyToMany(mappedBy = "categories")
	@JsonIgnore
	private List<Product> products;

	@JsonProperty("created_at")
	@Temporal(TIMESTAMP)
	private Date createdAt;

	@JsonProperty("updated_at")
	@Temporal(TIMESTAMP)
	private Date updatedAt;

	@JsonProperty
	@Enumerated(EnumType.STRING)
	private CategoryStatus status;

	public Category()
	{
		this.products = new ArrayList<Product>();
	}

	public Category(String name)
	{
		this.name = name;
		this.products = new ArrayList<Product>();
	}

	public Category(Long id)
	{
		this.id = id;
	}

	public Category(Long id, String name)
	{
		this.id = id;
		this.name = name;
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
	
	public List<Product> getProducts()
	{
		return products;
	}

	public void setProducts(List<Product> productList)
	{
		this.products = productList;
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

	public CategoryStatus getStatus()
	{
		return status;
	}

	public void setStatus(CategoryStatus status)
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
		if (!(object instanceof Category))
		{
			return false;
		}

		Category other = (Category) object;

		if (((this.id == null) && (other.id != null)) || ((this.id != null) && !this.id.equals(other.id)))
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "Category{" + "id=" + id + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
	}
}
