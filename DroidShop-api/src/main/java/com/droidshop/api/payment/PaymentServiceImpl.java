package com.droidshop.api.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.droidshop.api.model.order.Order;
import com.droidshop.api.model.order.OrderRepository;
import com.droidshop.api.model.order.Order.Status;
import com.droidshop.api.model.payment.CreditCard;
import com.droidshop.api.model.payment.CreditCardNumber;
import com.droidshop.api.model.payment.CreditCardPayment;
import com.droidshop.api.model.payment.Payment;
import com.droidshop.api.model.payment.Payment.Receipt;

/**
 * Implementation of {@link PaymentService} delegating persistence operations to {@link PaymentRepository} and
 * {@link CreditCardRepository}.
 */
@Service
@Transactional
class PaymentServiceImpl implements PaymentService {

	private final CreditCardRepository creditCardRepository;
	private final PaymentRepository paymentRepository;
	private final OrderRepository orderRepository;

	/**
	 * Creates a new {@link PaymentServiceImpl} from the given {@link PaymentRepository} and {@link CreditCardRepository}.
	 * 
	 * @param paymentRepository must not be {@literal null}.
	 * @param creditCardRepository must not be {@literal null}.
	 * @param orderRepository must not be {@literal null}.
	 */
	@Autowired
	public PaymentServiceImpl(PaymentRepository paymentRepository, CreditCardRepository creditCardRepository,
			OrderRepository orderRepository) {

		Assert.notNull(paymentRepository, "PaymentRepository must not be null!");
		Assert.notNull(creditCardRepository, "CreditCardRepository must not be null!");
		Assert.notNull(orderRepository, "OrderRepository must not be null!");

		this.creditCardRepository = creditCardRepository;
		this.paymentRepository = paymentRepository;
		this.orderRepository = orderRepository;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.droidshop.api.payment.PaymentService#pay(com.droidshop.api.model.order.Order, com.droidshop.api.payment.Payment)
	 */
	@Override
	public CreditCardPayment pay(Order order, CreditCardNumber creditCardNumber) {

		if (order.isPaid()) {
			throw new PaymentException(order, "Order already paid!");
		}

		CreditCard creditCard = creditCardRepository.findByNumber(creditCardNumber);

		if (creditCard == null) {
			throw new PaymentException(order, String.format("No credit card found for number: %s",
					creditCardNumber.getNumber()));
		}

		if (!creditCard.isValid()) {
			throw new PaymentException(order, String.format("Invalid credit card with number %s, expired %s!",
					creditCardNumber.getNumber(), creditCard.getExpirationDate()));
		}

		order.markPaid();
		return paymentRepository.save(new CreditCardPayment(creditCard, order));
	}

	/* 
	 * (non-Javadoc)
	 * @see com.droidshop.api.payment.PaymentService#getPaymentFor(com.droidshop.api.model.order.Order)
	 */
	@Override
	@Transactional(readOnly = true)
	public Payment getPaymentFor(Order order) {
		return paymentRepository.findByOrder(order);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.droidshop.api.payment.PaymentService#takeReceiptFor(com.droidshop.api.model.order.Order)
	 */
	@Override
	public Receipt takeReceiptFor(Order order) {

		order.setStatus(Status.TAKEN);
		orderRepository.save(order);

		Payment payment = getPaymentFor(order);
		return payment == null ? null : payment.getReceipt();
	}
}
