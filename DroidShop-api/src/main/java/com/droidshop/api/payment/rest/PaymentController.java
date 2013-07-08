package com.droidshop.api.payment.rest;

import lombok.EqualsAndHashCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.droidshop.api.AbstractController;
import com.droidshop.api.model.MonetaryAmount;
import com.droidshop.api.model.Order;
import com.droidshop.api.payment.PaymentService;
import com.droidshop.api.payment.model.CreditCard;
import com.droidshop.api.payment.model.CreditCardNumber;
import com.droidshop.api.payment.model.CreditCardPayment;
import com.droidshop.api.payment.model.Payment;
import com.droidshop.api.payment.model.Payment.Receipt;

/**
 * Spring MVC controller to handle payments for an {@link Order}.
 */
@Controller
@RequestMapping("/order/{id}")
@ExposesResourceFor(Payment.class)
public class PaymentController extends AbstractController
{
	private final PaymentService paymentService;
	private final EntityLinks entityLinks;

	/**
	 * Creates a new {@link PaymentController} using the given {@link PaymentService}.
	 * 
	 * @param paymentService
	 *            must not be {@literal null}.
	 * @param entityLinks
	 *            must not be {@literal null}.
	 */
	@Autowired
	public PaymentController(PaymentService paymentService, EntityLinks entityLinks)
	{
		Assert.notNull(paymentService, "PaymentService must not be null!");
		Assert.notNull(entityLinks, "EntityLinks must not be null!");

		this.paymentService = paymentService;
		this.entityLinks = entityLinks;
	}

	/**
	 * Accepts a payment for an {@link Order}
	 * 
	 * @param order
	 *            the {@link Order} to process the payment for. Retrieved from the path variable and
	 *            converted into an {@link Order} instance by Spring Data's
	 *            {@link DomainClassConverter}. Will be {@literal null} in case no {@link Order}
	 *            with the given id could be found.
	 * @param number
	 *            the {@link CreditCardNumber} unmarshalled from the request payload.
	 * @return
	 */
	@RequestMapping(value = PaymentLinks.PAYMENT, method = RequestMethod.PUT)
	HttpEntity<PaymentResource> submitPayment(@PathVariable("id") Order order, @RequestBody CreditCardNumber number)
	{
		if (order == null || order.isPaid())
		{
			return new ResponseEntity<PaymentResource>(HttpStatus.NOT_FOUND);
		}

		CreditCardPayment payment = paymentService.pay(order, number);

		PaymentResource resource = new PaymentResource(order.getPrice(), payment.getCreditCard());
		resource.add(entityLinks.linkToSingleResource(order));

		return new ResponseEntity<PaymentResource>(resource, HttpStatus.CREATED);
	}

	/**
	 * Shows the {@link Receipt} for the given order.
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = PaymentLinks.RECEIPT, method = RequestMethod.GET)
	HttpEntity<Resource<Receipt>> showReceipt(@PathVariable("id") Order order)
	{

		if (order == null || !order.isPaid() || order.isTaken())
		{
			return new ResponseEntity<Resource<Receipt>>(HttpStatus.NOT_FOUND);
		}

		Payment payment = paymentService.getPaymentFor(order);

		if (payment == null)
		{
			return new ResponseEntity<Resource<Receipt>>(HttpStatus.NOT_FOUND);
		}

		return createReceiptResponse(payment.getReceipt());
	}

	/**
	 * Takes the {@link Receipt} for the given {@link Order} and thus completes the process.
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = PaymentLinks.RECEIPT, method = RequestMethod.DELETE)
	HttpEntity<Resource<Receipt>> takeReceipt(@PathVariable("id") Order order)
	{
		if (order == null || !order.isPaid())
		{
			return new ResponseEntity<Resource<Receipt>>(HttpStatus.NOT_FOUND);
		}

		return createReceiptResponse(paymentService.takeReceiptFor(order));
	}

	/**
	 * Renders the given {@link Receipt} including links to the associated {@link Order} as well as
	 * a self link in case
	 * the {@link Receipt} is still available.
	 * 
	 * @param receipt
	 * @return
	 */
	private HttpEntity<Resource<Receipt>> createReceiptResponse(Receipt receipt)
	{
		Order order = receipt.getOrder();

		Resource<Receipt> resource = new Resource<Receipt>(receipt);
		resource.add(entityLinks.linkToSingleResource(order));

		if (!order.isTaken())
		{
			resource.add(entityLinks.linkForSingleResource(order).slash("receipt").withSelfRel());
		}

		return new ResponseEntity<Resource<Receipt>>(resource, HttpStatus.OK);
	}

	/**
	 * Resource implementation for payment results.
	 */
	@EqualsAndHashCode(callSuper = true)
	static class PaymentResource extends ResourceSupport
	{
		private MonetaryAmount amount;

		private CreditCard creditCard;

		public PaymentResource(MonetaryAmount amount, CreditCard creditCard)
		{
			super();
			this.amount = amount;
			this.creditCard = creditCard;
		}

		public MonetaryAmount getAmount()
		{
			return amount;
		}

		public void setAmount(MonetaryAmount amount)
		{
			this.amount = amount;
		}

		public CreditCard getCreditCard()
		{
			return creditCard;
		}

		public void setCreditCard(CreditCard creditCard)
		{
			this.creditCard = creditCard;
		}
	}
}
