package com.droidshop.api;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware
{
	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		context = applicationContext;
	}

	public static Object getBean(String name)
	{
		return context.getBean(name);
	}
}
