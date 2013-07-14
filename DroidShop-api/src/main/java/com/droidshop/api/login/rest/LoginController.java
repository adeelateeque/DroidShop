package com.droidshop.api.login.rest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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
	ResponseEntity<Resources<Resource<Customer>>> loginCustomer(@RequestParam("username") String username,
			@RequestParam("password") String password)
	{
		Customer customer = loginService.loginCustomer(username, password);
		Resource<Customer> resource = new Resource<Customer>(customer);
		resource.add(entityLinks.linkToSingleResource(customer).withSelfRel());
		ArrayList<Resource<Customer>> resourceList = new ArrayList<Resource<Customer>>();
		resourceList.add(resource);
		Resources<Resource<Customer>> resources = new Resources<Resource<Customer>>(resourceList);
		return new ResponseEntity<Resources<Resource<Customer>>>(resources, HttpStatus.OK);
	}

	@RequestMapping(value = "admin", method = RequestMethod.GET, produces = "application/json")
	ResponseEntity<Resources<Resource<Admin>>> loginAdmin(@RequestParam("username") String username,
			@RequestParam("password") String password)
	{
		Admin admin = loginService.loginAdmin(username, password);
		Resource<Admin> resource = new Resource<Admin>(admin);
		resource.add(entityLinks.linkToSingleResource(admin).withSelfRel());
		ArrayList<Resource<Admin>> resourceList = new ArrayList<Resource<Admin>>();
		resourceList.add(resource);
		Resources<Resource<Admin>> resources = new Resources<Resource<Admin>>(resourceList);
		return new ResponseEntity<Resources<Resource<Admin>>>(resources, HttpStatus.OK);
	}
}
