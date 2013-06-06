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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.droidshop.api.AbstractEntity;
import com.droidshop.api.model.order.Order;

/**
 * Baseclass for payment implementations.
 * 
 * @author Oliver Gierke
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public abstract class Payment extends AbstractEntity {

	@OneToOne(cascade = CascadeType.MERGE)
	private Order order;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime paymentDate;

	/**
	 * Creates a new {@link Payment} referring to the given {@link Order}.
	 * 
	 * @param order must not be {@literal null}.
	 */
	public Payment(Order order) {

		Assert.notNull(order);
		this.order = order;
		this.paymentDate = new DateTime();
	}

	/**
	 * Returns a receipt for the {@link Payment}.
	 * 
	 * @return
	 */
	public Receipt getReceipt() {
		return new Receipt(order, paymentDate);
	}

	/**
	 * A receipt for an {@link Order} and a payment date.
	 * 
	 * @author Oliver Gierke
	 */
	@Data
	public static class Receipt {

		private final Order order;
		private final DateTime date;
	}
}
