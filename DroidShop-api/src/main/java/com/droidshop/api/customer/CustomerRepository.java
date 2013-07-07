package com.droidshop.api.customer;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.droidshop.api.model.user.Customer;

/**
 * Repository interface to manage {@link Customer} instances.
 */
@RestResource(path="customer", rel="customer")
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>
{
}
