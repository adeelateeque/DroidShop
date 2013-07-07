package com.droidshop.api.payment.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Value object to represent a {@link CreditCardNumber}.
 */
@Embeddable
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CreditCardNumber {

	private static final String regex = "[0-9]{16}";
	
	public CreditCardNumber()
	{
	}

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

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}
}
