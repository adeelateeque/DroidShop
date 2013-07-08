package com.droidshop.api.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.droidshop.api.model.user.Customer;

public interface CustomerService
{
	public Customer save(Customer customer);

	public Page<Customer> findAll(Pageable pageable);

	public void delete(Customer customer);
}
