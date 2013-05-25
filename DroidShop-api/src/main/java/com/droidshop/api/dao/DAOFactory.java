package com.droidshop.api.dao;

public abstract class DAOFactory {
	
	private UserDAO userDAO = new UserDAO();
	
	public UserDAO getUserDAO()
	{
		if(userDAO==null)
		{
			userDAO = new UserDAO(); 
		}
		
		return userDAO;
	}
}
