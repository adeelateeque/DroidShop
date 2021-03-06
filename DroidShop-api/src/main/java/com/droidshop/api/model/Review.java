package com.droidshop.api.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "review")
public class Review extends AbstractEntity
{
	@Basic(optional = false)
	@Min(value = 0)
	@Max(value = 5)
	protected Double rating;

	@Basic(optional = false)
	@Size(min = 3, max = 5000, message = "{review length too long}")
	protected String review;

	public Review()
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

	public String getReview()
	{
		return review;
	}

	public void setReview(String review)
	{
		this.review = review;
	}

	public Integer getRating()
	{
		return rating.intValue();
	}

	public void setRating(Double rating)
	{
		this.rating = rating;
	}
}
