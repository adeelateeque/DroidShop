package com.droidshop.api.model;

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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@ToString
@EqualsAndHashCode(callSuper=true)
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "category")
public class Category extends AbstractEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@NotNull(groups = POST.class, message = "name: Missing Required Field")
	@JsonProperty
	@Column(length = 30, nullable = false, unique = true)
	@Basic(optional = false)
	private String name;

	@ManyToMany(mappedBy = "categories")
	private List<Product> products;

	@JsonProperty("created_at")
	@Temporal(TIMESTAMP)
	private Date createdAt;

	@JsonProperty("updated_at")
	@Temporal(TIMESTAMP)
	private Date updatedAt;

	@JsonProperty
	@Enumerated(EnumType.STRING)
	private Status status;

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
		ENABLED, DISABLED;
	}
}
