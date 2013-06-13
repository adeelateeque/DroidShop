/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.droidshop.api.payment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.util.Assert;

import com.droidshop.api.model.order.Order;

/**
 * A {@link Payment} done through a {@link CreditCard}.
 * 
 * @author Oliver Gierke
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreditCardPayment extends Payment {

	@ManyToOne
	private CreditCard creditCard;

	/**
	 * Creates a new {@link CreditCardPayment} for the given {@link CreditCard} and {@link Order}.
	 * 
	 * @param creditCard must not be {@literal null}.
	 * @param order
	 */
	public CreditCardPayment(CreditCard creditCard, Order order) {

		super(order);
		Assert.notNull(creditCard);
		this.creditCard = creditCard;
	}
}
