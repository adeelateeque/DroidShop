package com.droidshop.api.payment;

import com.droidshop.api.model.Order;
import com.droidshop.api.payment.model.CreditCard;
import com.droidshop.api.payment.model.CreditCardNumber;
import com.droidshop.api.payment.model.CreditCardPayment;
import com.droidshop.api.payment.model.Payment;
import com.droidshop.api.payment.model.Payment.Receipt;

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
