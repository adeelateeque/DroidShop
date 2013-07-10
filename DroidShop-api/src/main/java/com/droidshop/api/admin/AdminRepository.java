package com.droidshop.api.admin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

import com.droidshop.api.model.user.Admin;

@RestResource(path="admin", rel="admin")
public interface AdminRepository extends CrudRepository<Admin, Long>
{
	@RestResource(exported = false)
	Admin findByUserNameAndPassword(String username, String password);
}
