package com.droidshop.model;

public class Review extends AbstractEntity
{
	protected Double rating;

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
