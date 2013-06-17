package com.droidshop.api.model.order;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.droidshop.api.model.order.Order.Status;

/**
 * Repository to manage {@link Order} instances.
 */
@RestResource(path = "orders", rel = "orders")
public interface OrderRepository extends CrudRepository<Order, Long>
{

	/**
	 * Returns all {@link Order}s with the given {@link Status}.
	 * 
	 * @param status
	 *            must not be {@literal null}.
	 * @return
	 */
	List<Order> findByStatus(@Param("status") Status status);
}
