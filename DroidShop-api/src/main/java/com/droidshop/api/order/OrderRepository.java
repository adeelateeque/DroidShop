package com.droidshop.api.order;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.droidshop.api.model.Order;
import com.droidshop.api.model.Order.Status;

/**
 * Repository to manage {@link Order} instances.
 */
@RestResource(path = "order", rel = "order")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>
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
