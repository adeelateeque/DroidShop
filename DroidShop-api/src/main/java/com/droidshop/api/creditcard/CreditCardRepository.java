package com.droidshop.api.creditcard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.droidshop.api.payment.model.CreditCard;
import com.droidshop.api.payment.model.CreditCardNumber;

/**
 * Repository to access {@link CreditCard} instances.
 */
@RestResource(exported = false)
public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {

	/**
	 * Returns the {@link CreditCard} associated with the given {@link CreditCardNumber}.
	 * 
	 * @param number must not be {@literal null}.
	 * @return
	 */
	CreditCard findByNumber(CreditCardNumber number);
}
