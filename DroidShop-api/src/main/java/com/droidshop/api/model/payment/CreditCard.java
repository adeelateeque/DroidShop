package com.droidshop.api.model.payment;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.ToString;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;

import com.droidshop.api.model.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@ToString(callSuper = true)
@Entity
@Table(name = "creditcard")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
public class CreditCard extends AbstractEntity
{
	@JsonUnwrapped
	private CreditCardNumber number;

	@JsonProperty
	private String cardHolderName;

	private Months expiryMonth;
	private Years expiryYear;

	public CreditCard(CreditCardNumber number, String cardHolderName, Months expiryMonth, Years expiryYear)
	{
		super();
		this.number = number;
		this.cardHolderName = cardHolderName;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
	}

	protected CreditCard()
	{

	}

	/**
	 * Returns whether the {@link CreditCard} is currently valid.
	 * 
	 * @return
	 */
	@JsonIgnore
	public boolean isValid()
	{
		return isValid(new LocalDate());
	}

	/**
	 * Returns whether the {@link CreditCard} is valid for the given date.
	 * 
	 * @param date
	 * @return
	 */
	public boolean isValid(LocalDate date)
	{
		return date == null ? false : getExpirationDate().isAfter(date);
	}

	/**
	 * Returns the {@link LocalDate} the {@link CreditCard} expires.
	 * 
	 * @return will never be {@literal null}.
	 */
	public LocalDate getExpirationDate()
	{
		return new LocalDate(expiryYear.getYears(), expiryMonth.getMonths(), 1);
	}

	/**
	 * Protected setter to allow binding the expiration date.
	 * 
	 * @param date
	 */
	protected void setExpirationDate(LocalDate date)
	{

		this.expiryYear = Years.years(date.getYear());
		this.expiryMonth = Months.months(date.getMonthOfYear());
	}

	public CreditCardNumber getNumber()
	{
		return number;
	}

	public void setNumber(CreditCardNumber number)
	{
		this.number = number;
	}

	public String getCardHolderName()
	{
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName)
	{
		this.cardHolderName = cardHolderName;
	}

	public Months getExpiryMonth()
	{
		return expiryMonth;
	}

	public void setExpiryMonth(Months expiryMonth)
	{
		this.expiryMonth = expiryMonth;
	}

	public Years getExpiryYear()
	{
		return expiryYear;
	}

	public void setExpiryYear(Years expiryYear)
	{
		this.expiryYear = expiryYear;
	}
}
