package com.droidshop.api.model.product;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import org.springframework.data.mongodb.core.mapping.Document;

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

	@JsonProperty("created_at")
	@Temporal(TIMESTAMP)
	private Date createdAt;

	@JsonProperty("updated_at")
	@Temporal(TIMESTAMP)
	private Date updatedAt;

	@JsonProperty
	private ProductStatus status;

	@JsonProperty
	private String statusCode;

	public Product()
	{
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
		this.statusCode = status.getStatusCode();
	}

	public String getStatusCode()
	{
		return statusCode;
	}

	public void setStatusCode(String statusCode)
	{
		this.statusCode = statusCode;
	}

	@Override
	public String toString()
	{
		return "Product{" + "id=" + id + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
	}
}
