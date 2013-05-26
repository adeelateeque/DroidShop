package com.droidshop.api;

public class Logger
{
	private Logger(){}
	
	public static void trace(Class<?> entity, String message)
	{
		org.slf4j.LoggerFactory.getLogger(entity).trace(message);
	}

	public static void debug(Class<?> entity, String message)
	{
		org.slf4j.LoggerFactory.getLogger(entity).debug(message);
	}

	public static void info(Class<?> entity, String message)
	{
		org.slf4j.LoggerFactory.getLogger(entity).info(message);
	}

	public static void warn(Class<?> entity, String message)
	{
		org.slf4j.LoggerFactory.getLogger(entity).warn(message);
	}

	public static void error(Class<?> entity, String message)
	{
		org.slf4j.LoggerFactory.getLogger(entity).error(message);
	}
}
