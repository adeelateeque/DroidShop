package com.droidshop.api.order;

import static com.droidshop.api.model.MonetaryAmount.EURO;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.droidshop.api.customer.CustomerRepository;
import com.droidshop.api.model.MonetaryAmount;
import com.droidshop.api.model.Order;
import com.droidshop.api.model.Product;
import com.droidshop.api.model.user.Customer;
import com.droidshop.api.product.ProductRepository;

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
	public OrderInitializer(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository)
	{
		Assert.notNull(orderRepository, "OrderRepository must not be null!");
		Assert.notNull(customerRepository, "CustomerRepository must not be null!");
		Assert.notNull(productRepository, "ProductRepository must not be null!");
		

		Product javaChip = new Product("Java Chip", new MonetaryAmount(EURO, 4.20));
		javaChip = productRepository.save(javaChip);
		Product cappuchino = new Product("Cappuchino", new MonetaryAmount(EURO, 3.20));
		cappuchino = productRepository.save(cappuchino);

		Customer customer = customerRepository.findOne((long) 1);
		Order javaChipOrder = new Order(javaChip);
		javaChipOrder.setCustomer(customer);
		Order cappuchinoOrder = new Order(cappuchino);
		cappuchinoOrder.setCustomer(customer);
		Order javaChipAndCappuchinoOrder = new Order(Arrays.asList(javaChip, cappuchino));
		javaChipAndCappuchinoOrder.setCustomer(customer);
		orderRepository.save(Arrays.asList(javaChipOrder, cappuchinoOrder, javaChipAndCappuchinoOrder));
	}
}
