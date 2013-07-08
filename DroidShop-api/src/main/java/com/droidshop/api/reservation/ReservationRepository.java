package com.droidshop.api.reservation;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.droidshop.api.model.Reservation;

@RestResource(path="reservation", rel="reservation")
public interface ReservationRepository extends PagingAndSortingRepository<Reservation, Long>
{

}
