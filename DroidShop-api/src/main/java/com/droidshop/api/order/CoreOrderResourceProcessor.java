package com.droidshop.api.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.droidshop.api.model.order.Order;

/**
 * {@link ResourceProcessor} implementation to add links to the {@link Order} representation that indicate that the
 * Order can be updated or cancelled as long as it has not been paid yet.
 */
@Component
class CoreOrderResourceProcessor implements ResourceProcessor<Resource<Order>> {

	public static final String CANCEL_REL = "cancel";
	public static final String UPDATE_REL = "update";

	private final EntityLinks entityLinks;

	/**
	 * Creates a new {@link CoreOrderResourceProcessor} using the given {@link EntityLinks} instance.
	 * 
	 * @param entityLinks must not be {@literal null}.
	 */
	@Autowired
	public CoreOrderResourceProcessor(EntityLinks entityLinks) {
		Assert.notNull(entityLinks, "EntityLinks must not be null!");
		this.entityLinks = entityLinks;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.hateoas.ResourceProcessor#process(org.springframework.hateoas.ResourceSupport)
	 */
	@Override
	public Resource<Order> process(Resource<Order> resource) {

		Order order = resource.getContent();

		if (!order.isPaid()) {
			resource.add(entityLinks.linkForSingleResource(order).withRel(CANCEL_REL));
			resource.add(entityLinks.linkForSingleResource(order).withRel(UPDATE_REL));
		}

		return resource;
	}
}
