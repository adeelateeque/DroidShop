package com.droidshop.api.dao;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.droidshop.api.model.user.Customer;
import com.droidshop.api.model.user.UserStatus;
import com.droidshop.api.util.PasswordEncoder;

@Repository
public class CustomerDAO extends AbstractDAO<Customer>
{

	public CustomerDAO()
	{
		super(Customer.class);
	}

	public Customer add(Customer customer) throws NoSuchAlgorithmException
	{
		System.out.println("CustomerDAO: add");
		System.out.println("CustomerDAO: START - adding customer to the database");

		customer.setStatus(UserStatus.ACTIVE);
		customer.setCreatedAt(new Date());
		customer.setUpdatedAt(new Date());

		customer.setPassword(PasswordEncoder.getEncodedPassword(customer.getUserName(), customer.getPassword()));
		save(customer);

		System.out.println("CustomerDAO: END - adding customer to the database");

		return customer;
	}

	public Customer update(Long customerID, Customer customer) throws NoSuchAlgorithmException
	{
		System.out.println("CustomerDAO: update");
		System.out.println("CustomerDAO: START - updating customer to the database");

		Customer fetchedCustomer = fetch(customerID);

		if (customer.getFirstName() != null && !fetchedCustomer.getFirstName().equals(customer.getFirstName()))
		{
			fetchedCustomer.setFirstName(customer.getFirstName());
		}
		if (customer.getMiddleName() != null && !fetchedCustomer.getMiddleName().equals(customer.getMiddleName()))
		{
			fetchedCustomer.setMiddleName(customer.getMiddleName());
		}
		if (customer.getLastName() != null && !fetchedCustomer.getLastName().equals(customer.getLastName()))
		{
			fetchedCustomer.setLastName(customer.getLastName());
		}
		String currentEncodedPassword = PasswordEncoder.getEncodedPassword(customer.getUserName(), customer.getPassword());
		if (customer.getPassword() != null && !fetchedCustomer.getPassword().equals(currentEncodedPassword))
		{
			fetchedCustomer.setPassword(currentEncodedPassword);
		}
		if (customer.getStatus() != null && fetchedCustomer.getStatus() != customer.getStatus())
		{
			fetchedCustomer.setStatus(customer.getStatus());
		}
		if (customer.getDateOfBirth() != null && fetchedCustomer.getDateOfBirth() != customer.getDateOfBirth())
		{
			fetchedCustomer.setDateOfBirth(customer.getDateOfBirth());
		}
		fetchedCustomer.setUpdatedAt(new Date());

		update(fetchedCustomer);
		System.out.println("CustomerDAO: END - updating customer to the database");

		return fetchedCustomer;
	}

	public Customer fetch(Long customerID)
	{
		System.out.println("CustomerDAO: fetch");
		System.out.println("CustomerDAO: START - fetching customer from the database by customer ID");

		Customer fetchedCustomer = find(customerID);
		System.out.println("CustomerDAO: END - fetching customer from the database by customer ID");

		return fetchedCustomer;
	}

	public Customer fetchByUserName(String userName)
	{
		System.out.println("CustomerDAO: fetchByUserName");
		System.out.println("CustomerDAO: START - fetching customer from the database by customername");

		Customer fetchedCustomer = (Customer) getCurrentSession().createCriteria(Customer.class)
				.add(Restrictions.eq("userName", userName)).uniqueResult();
		System.out.println("CustomerDAO: END - fetching customer from the database by customername");

		return fetchedCustomer;
	}

	public List<Customer> fetchAll(boolean includeAll)
	{
		System.out.println("CustomerDAO: fetchAll");
		System.out.println("CustomerDAO: START - fetching all customers from the database");

		List<Customer> fetchedCustomers = getAll();

		System.out.println("DEBUG: includeAll [" + includeAll + "]");
		if (includeAll == false)
		{
			System.out.println("CustomerDAO: removing non-active customers");
			Iterator<Customer> iterator = fetchedCustomers.iterator();
			while (iterator.hasNext())
			{
				Customer currentCustomer = (Customer) iterator.next();
				UserStatus customerStatus = currentCustomer.getStatus();
				System.out.println("DEBUG: Customer Status Code [" + customerStatus + "]");
				if (!customerStatus.equals(UserStatus.ACTIVE))
				{
					System.out.println("DEBUG: Removing Customer [" + currentCustomer.getUserName() + "]");
					iterator.remove();
				}
			}
		}
		else
		{
			System.out.println("CustomerDAO: including all customers");
		}
		System.out.println("CustomerDAO: END - fetching all customers from the database");

		return fetchedCustomers;
	}

	public Customer delete(Long customerID)
	{
		System.out.println("CustomerDAO: delete");
		System.out.println("CustomerDAO: START - setting customer status to delete in the database");

		Customer fetchedCustomer = fetch(customerID);
		fetchedCustomer.setStatus(UserStatus.DELETED);
		fetchedCustomer.setUpdatedAt(new Date());
		update(fetchedCustomer);
		System.out.println("CustomerDAO: END - setting customer status to delete in the database");

		return fetchedCustomer;
	}
}
