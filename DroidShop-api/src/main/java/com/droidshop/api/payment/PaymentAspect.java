package com.droidshop.api.payment;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.droidshop.api.model.Order;

/**
 * Aspect to publish an {@link OrderPaidEvent} on successful execution of
 * {@link PaymentService#pay(Order, CreditCardNumber)} <em>after</em> the transaction has completed. Manually defines
 * the order of the aspect to be <em>before</em> the transaction aspect.
 */
@Aspect
@Service
class PaymentAspect implements ApplicationEventPublisherAware, Ordered {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ApplicationEventPublisher publisher;

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationEventPublisherAware#setApplicationEventPublisher(org.springframework.context.ApplicationEventPublisher)
	 */
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE - 10;
	}

	/**
	 * Publishes an {@link OrderPaidEvent} for the given {@link Order}.
	 * 
	 * @param order
	 */
	@AfterReturning(value = "execution(* PaymentService.pay(com.droidshop.api.model.Order, ..)) && args(order, ..)")
	public void triggerPaymentEvent(Order order) {

		if (order == null) {
			return;
		}

		logger.info("Publishing order payed event for order {}!", order.getId());
		this.publisher.publishEvent(new OrderPaidEvent(order.getId(), this));
	}
}
