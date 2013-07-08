package com.droidshop.api.customer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.droidshop.api.AbstractController;
import com.droidshop.api.customer.CustomerService;
import com.droidshop.api.model.user.Customer;

/*@Controller
@RequestMapping(value="/customer", consumes = "application/json", produces = "application/json")
@ExposesResourceFor(Customer.class)*/
public class CustomerController extends AbstractController
{
	private final CustomerService customerService;
	private final EntityLinks entityLinks;

	@Autowired
	public CustomerController(CustomerService customerService, EntityLinks entityLinks)
	{
		Assert.notNull(customerService, "CustomerService must not be null!");
		Assert.notNull(entityLinks, "EntityLinks must not be null!");

		this.customerService = customerService;
		this.entityLinks = entityLinks;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Resource<Customer>> add(@RequestBody Customer customer) throws Exception
	{
		Customer newCustomer = customerService.save(customer);
		Resource<Customer> resource = new Resource<Customer>(newCustomer, entityLinks.linkToSingleResource(newCustomer));

		return new ResponseEntity<Resource<Customer>>(resource, HttpStatus.CREATED);
	}

	/*
	 * @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json",
	 * produces = "application/json")
	 * @ResponseBody
	 * public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) throws
	 * Exception {
	 * return customerManager.update(id, customer);
	 * }
	 * @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	 * @ResponseBody
	 * public Customer fetchCustomer(@PathVariable Long id) {
	 * return customerManager.fetch(id);
	 * }
	 * @RequestMapping(value = "/{id}/orders", method = RequestMethod.GET, produces =
	 * "application/json")
	 * @ResponseBody
	 * public List<Order> fetchCustomerOrders(@PathVariable Long id) {
	 * return customerManager.fetchCustomerOrders(id);
	 * }
	 * @RequestMapping(value = "/username/{userName}", method = RequestMethod.GET, produces =
	 * "application/json")
	 * @ResponseBody
	 * public Customer fetchByUserName(@PathVariable String userName) {
	 * return customerManager.fetchByUserName(userName);
	 * }
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Page<Customer>> fetchAllCustomers(
			@RequestParam(value = "include_all", required = false) boolean includeAll, Pageable pageable)
	{
		return new ResponseEntity<Page<Customer>>(customerService.findAll(pageable), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Void> deleteCustomer(@RequestBody Customer customer) throws Exception
	{
		customerService.delete(customer);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
