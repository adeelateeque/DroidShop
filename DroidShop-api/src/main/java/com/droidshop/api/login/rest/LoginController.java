package com.droidshop.api.login.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.droidshop.api.AbstractController;
import com.droidshop.api.login.LoginService;
import com.droidshop.api.model.user.Admin;
import com.droidshop.api.model.user.Customer;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends AbstractController
{
	private final LoginService loginService;
	private final EntityLinks entityLinks;

	@Autowired
	public LoginController(LoginService loginService, EntityLinks entityLinks)
	{
		Assert.notNull(loginService, "LoginService must not be null!");
		Assert.notNull(entityLinks, "EntityLinks must not be null!");

		this.loginService = loginService;
		this.entityLinks = entityLinks;
	}

	@RequestMapping(value = "customer", method = RequestMethod.GET, produces = "application/json")
	HttpEntity<Resource<Customer>> loginCustomer(@RequestParam("username") String username,
			@RequestParam("password") String password)
	{
		Customer customer = loginService.loginCustomer(username, password);
		Resource<Customer> resource = new Resource<Customer>(customer, entityLinks.linkForSingleResource(customer)
				.withSelfRel());
		return new ResponseEntity<Resource<Customer>>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "admin", method = RequestMethod.GET, produces = "application/json")
	HttpEntity<Resource<Admin>> loginAdmin(@RequestParam("username") String username,
			@RequestParam("password") String password)
	{
		Admin admin = loginService.loginAdmin(username, password);
		Resource<Admin> resource = new Resource<Admin>(admin, entityLinks.linkForSingleResource(admin).withSelfRel());
		return new ResponseEntity<Resource<Admin>>(resource, HttpStatus.OK);
	}
}
