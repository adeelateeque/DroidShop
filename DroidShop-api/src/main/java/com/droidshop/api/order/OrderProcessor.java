package com.droidshop.api.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.droidshop.api.model.Order;
import com.droidshop.api.payment.OrderPaidEvent;

/**
 * Simple {@link ApplicationListener} implementation that listens to {@link OrderPaidEvent}s marking
 * the according {@link Order} as in process, sleeping for 10 seconds and marking the order as
 * processed right after that.
 */
@Service
class OrderProcessor implements ApplicationListener<OrderPaidEvent>
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final OrderRepository repository;

	/**
	 * Creates a new {@link OrderProcessor} using the given {@link OrderRepository}.
	 * 
	 * @param repository
	 *            must not be {@literal null}.
	 */
	@Autowired
	public OrderProcessor(OrderRepository repository)
	{
		Assert.notNull(repository, "OrderRepository must not be null!");
		this.repository = repository;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context
	 * .ApplicationEvent)
	 */
	@Async
	@Override
	public void onApplicationEvent(OrderPaidEvent event)
	{
		Order order = repository.findOne(event.getOrderId());
		order.markInPreparation();
		order = repository.save(order);

		logger.info("Starting to process order {}.", order);

		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}

		order.markPrepared();
		repository.save(order);

		logger.info("Finished processing order {}.", order);
	}
}
