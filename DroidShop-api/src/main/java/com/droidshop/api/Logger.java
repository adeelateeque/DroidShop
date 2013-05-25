package com.droidshop.api;

public class Logger
{
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Application.class);
	
	private Logger(){}
	
	public static void trace(String message)
	{
		log.trace(message);
	}

	public static void debug(String message)
	{
		log.debug(message);
	}

	public static void info(String message)
	{
		log.info(message);
	}

	public static void warn(String message)
	{
		log.warn(message);
	}

	public static void error(String message)
	{
		log.error(message);
	}
}
