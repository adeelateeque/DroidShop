package com.droidshop.api.model.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin extends User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
}
