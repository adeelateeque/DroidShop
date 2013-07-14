package com.droidshop.api;

import java.util.ArrayList;
import java.util.List;

import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.Product;
import com.droidshop.model.MonetaryAmount;
import com.droidshop.model.Reservation;

public class ReservationApi extends BootstrapApi
{

	protected ReservationApi(String username, String password)
	{
		super(username, password);
	}

	protected ReservationApi(String apiKey, UserAgentProvider userAgentProvider)
	{
		super(apiKey, userAgentProvider);
	}

	public List<Reservation> getReservations()
	{
		List<Reservation> reservations = new ArrayList<Reservation>();
		int i = 0;
		while (i <10){
			Reservation r = new Reservation();
			r.setProduct(new Product("iPhone " + i, 5, MonetaryAmount.ZERO));
			reservations.add(r);
			i++;
		}
		return reservations;
	}
}
