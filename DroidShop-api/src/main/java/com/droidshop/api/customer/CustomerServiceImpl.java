package com.droidshop.api.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.droidshop.api.model.user.Customer;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService
{
	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository)
	{
		Assert.notNull(customerRepository, "CustomerRepository must not be null!");

		this.customerRepository = customerRepository;
	}
	
	@Override
	public Customer save(Customer customer)
	{
		return customerRepository.save(customer);
	}

	@Override
	public Page<Customer> findAll(Pageable pageable)
	{
		return customerRepository.findAll(pageable);
	}

	@Override
	public void delete(Customer customer)
	{
		customerRepository.delete(customer);
	}
	
}
