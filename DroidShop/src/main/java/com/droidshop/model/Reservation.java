package com.droidshop.model;

import org.joda.time.DateTime;

public class Reservation extends AbstractEntity {

	private User user;
	private Product product;
	private int qty;
	private DateTime reservationDateTime;
	private Status status;

	public Reservation() {

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public DateTime getReservationDateTime() {
		return reservationDateTime;
	}

	public void setReservationDateTime(DateTime reservationDateTime) {
		this.reservationDateTime = reservationDateTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status
	{
		PENDING, FULLFILLED
	}
}
