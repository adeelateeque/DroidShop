package com.droidshop.api.payment;

import com.droidshop.api.model.order.Order;
import com.droidshop.api.model.payment.CreditCard;
import com.droidshop.api.model.payment.CreditCardNumber;
import com.droidshop.api.model.payment.CreditCardPayment;
import com.droidshop.api.model.payment.Payment;
import com.droidshop.api.model.payment.Payment.Receipt;

/**
 * Interface to collect payment services.
 */
public interface PaymentService {

	/**
	 * Pay the given {@link Order} with the {@link CreditCard} identified by the given {@link CreditCardNumber}.
	 * 
	 * @param order
	 * @param creditCardNumber
	 * @return
	 */
	CreditCardPayment pay(Order order, CreditCardNumber creditCardNumber);

	/**
	 * Returns the {@link Payment} for the given {@link Order}.
	 * 
	 * @param order
	 * @return the {@link Payment} for the given {@link Order} or {@literal null} if the Order hasn't been payed yet.
	 */
	Payment getPaymentFor(Order order);

	/**
	 * Takes the receipt
	 * 
	 * @param order
	 * @return
	 */
	Receipt takeReceiptFor(Order order);
}
