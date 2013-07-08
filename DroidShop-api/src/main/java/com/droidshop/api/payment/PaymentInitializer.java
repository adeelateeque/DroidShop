package com.droidshop.api.payment;

import org.joda.time.Months;
import org.joda.time.Years;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.droidshop.api.creditcard.CreditCardRepository;
import com.droidshop.api.payment.model.CreditCard;
import com.droidshop.api.payment.model.CreditCardNumber;

/**
 * Initializing component to creade a default {@link CreditCard} in the system.
 */
@Service
class PaymentInitializer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public PaymentInitializer(CreditCardRepository repository) {

		if (repository.count() > 0) {
			return;
		}

		CreditCardNumber number = new CreditCardNumber("1234123412341234");
		CreditCard creditCard = new CreditCard(number, "Oliver Gierke", Months.TWELVE, Years.years(2014));

		creditCard = repository.save(creditCard);

		logger.info("Credit card {} created!", creditCard);
	}
}
