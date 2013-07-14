package com.droidshop.api.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.droidshop.api.admin.AdminRepository;
import com.droidshop.api.customer.CustomerRepository;
import com.droidshop.api.model.user.Admin;
import com.droidshop.api.model.user.Customer;

@Service
@Transactional
public class LoginServiceImpl implements LoginService
{
	private final AdminRepository adminRepository;
	private final CustomerRepository customerRepository;
	
	@Autowired
	public LoginServiceImpl(AdminRepository adminRepository, CustomerRepository customerRepository) {

		Assert.notNull(adminRepository, "AdminRepository must not be null!");
		Assert.notNull(customerRepository, "CustomerRepository must not be null!");

		this.adminRepository = adminRepository;
		this.customerRepository = customerRepository;
	}
	
	@Override
	public Customer loginCustomer(String username, String password)
	{
		return customerRepository.findByUserNameAndPassword(username, password);
	}

	@Override
	public Admin loginAdmin(String username, String password)
	{
		return adminRepository.findByUserNameAndPassword(username, password);
	}
}
