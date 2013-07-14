package com.droidshop.api.login;

import com.droidshop.api.model.user.Admin;
import com.droidshop.api.model.user.Customer;

public interface LoginService
{
	Customer loginCustomer(String username, String password);
	Admin loginAdmin(String username, String password);
}
