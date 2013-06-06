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

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Value object to represent a {@link CreditCardNumber}.
 * 
 * @author Oliver Gierke
 */
@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CreditCardNumber {

	private static final String regex = "[0-9]{16}";

	@Column(unique = true)
	private String number;

	/**
	 * Creates a new {@link CreditCardNumber}.
	 * 
	 * @param number must not be {@literal null} and be a 16 digit numerical only String.
	 */
	public CreditCardNumber(String number) {

		if (!isValid(number)) {
			throw new IllegalArgumentException(String.format("Invalid credit card NUMBER %s!", number));
		}

		this.number = number;
	}

	/**
	 * Returns whether the given {@link String} is a valid {@link CreditCardNumber}.
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isValid(String number) {
		return number == null ? false : number.matches(regex);
	}
}
