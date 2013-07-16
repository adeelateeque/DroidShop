package com.droidshop.model;

import java.util.ArrayList;

import com.droidshop.api.Link;

public class AbstractEntity
{

	protected Long id;

	protected ArrayList<Link> links;

	protected AbstractEntity()
	{
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

	public ArrayList<Link> getLinks()
	{
		return links;
	}

	public void setLinks(ArrayList<Link> links)
	{
		this.links = links;
	}

	public Link findLink(String rel)
	{
		for (Link link : links)
		{
			if (link.getRel().equals(rel))
			{
				return link;
			}
		}
		return null;
	}

	public String getSelfHref()
	{
		Link self = findLink("self");
		return (self != null) ? self.getHref() : null;
	}
}
