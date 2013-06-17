package com.droidshop.api.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.hateoas.Identifiable;

/**
 * Base class for entity implementations. Uses a {@link Long} id.
 */
@MappedSuperclass
@ToString
@EqualsAndHashCode
public class AbstractEntity implements Identifiable<Long> {

	@NotNull(groups = { PUT.class, DELETE.class }, message = "id: Missing Required Field")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
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
