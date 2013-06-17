package com.droidshop.api.model.order;

import static com.droidshop.api.model.MonetaryAmount.*;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.droidshop.api.model.MonetaryAmount;
import com.droidshop.api.model.Product;

/**
 * Initializer to set up two {@link Order}s.
 */
@Service
class OrderInitializer
{

	/**
	 * Creates two orders and persists them using the given {@link OrderRepository}.
	 * 
	 * @param orderRepository
	 *            must not be {@literal null}.
	 */
	@Autowired
	public OrderInitializer(OrderRepository orderRepository)
	{

		Assert.notNull(orderRepository, "OrderRepository must not be null!");

		Product javaChip = new Product("Java Chip", new MonetaryAmount(EURO, 4.20));
		Product cappuchino = new Product("Cappuchino", new MonetaryAmount(EURO, 3.20));

		Order javaChipOrder = new Order(javaChip);
		Order cappuchinoOrder = new Order(cappuchino);

		orderRepository.save(Arrays.asList(javaChipOrder, cappuchinoOrder));
	}
}
