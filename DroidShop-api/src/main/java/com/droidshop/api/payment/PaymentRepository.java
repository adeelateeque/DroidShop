package com.droidshop.api.payment;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.droidshop.api.model.order.Order;
import com.droidshop.api.model.payment.Payment;

/**
 * Repository interface to manage {@link Payment} instances.
 */
@RestResource(exported = false)
interface PaymentRepository extends PagingAndSortingRepository<Payment, Long>
{

	/**
	 * Returns the payment registered for the given {@link Order}.
	 * 
	 * @param order
	 * @return
	 */
	Payment findByOrder(Order order);
}
