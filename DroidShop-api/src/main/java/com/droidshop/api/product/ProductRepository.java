package com.droidshop.api.product;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.droidshop.api.model.Product;
import com.droidshop.api.payment.model.Payment;

/**
 * Repository interface to manage {@link Payment} instances.
 */
@RestResource(path="product")
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>
{
}
