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

import lombok.Getter;

import org.springframework.util.Assert;

import com.droidshop.api.model.order.Order;

/**
 * @author Oliver Gierke
 */
@Getter
public class PaymentException extends RuntimeException {

	private static final long serialVersionUID = -4929826142920520541L;
	private final Order order;

	public PaymentException(Order order, String message) {

		super(message);

		Assert.notNull(order);
		this.order = order;
	}
}
